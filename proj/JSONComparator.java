import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONComparator {

    private static final String DIRECTORY_PATH = "/home/hburchell/Repos/graal-dev/graal-instrumentation/compiler/LIRCostInformation/";

    public static void main(String[] args) {
        try {
            // Get all JSON files from the specified directory
            File[] jsonFiles = findJSONFiles(DIRECTORY_PATH);

            // Map to store class names and their associated costs across all files
            Map<String, Integer[][]> comparisonResults = compareJSONFiles(jsonFiles, jsonFiles.length);

            // Display the results and highlight differences
            displayComparisonResults(comparisonResults);

            // Display the list of differing classes and their ranges
            displayDifferences(comparisonResults, jsonFiles.length);

        } catch (IOException e) {
            System.err.println("Error processing JSON files: " + e.getMessage());
        }
    }

    // Method to find all JSON files in the specified directory
    private static File[] findJSONFiles(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.listFiles((dir, name) -> name.endsWith(".json"));
    }

    // Method to read a JSON file and return a map of class names and their normalCost and vCost
    private static Map<String, int[]> readJSONFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder jsonContent = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }
        reader.close();

        JSONArray jsonArray = new JSONArray(jsonContent.toString());
        Map<String, int[]> jsonMap = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String className = jsonObject.getString("Class");
            int normalCost = jsonObject.getInt("normalCost");
            int vCost = jsonObject.getInt("vCost");
            jsonMap.put(className, new int[]{normalCost, vCost});
        }

        return jsonMap;
    }

    // Method to compare JSON files and return a map of class names and their associated ranges (min/max/count)
    private static Map<String, Integer[][]> compareJSONFiles(File[] files, int totalFiles) throws IOException {
        Map<String, Integer[][]> resultMap = new HashMap<>();

        for (File file : files) {
            Map<String, int[]> jsonMap = readJSONFile(file.getAbsolutePath());

            for (Map.Entry<String, int[]> entry : jsonMap.entrySet()) {
                String className = entry.getKey();
                int normalCost = entry.getValue()[0];
                int vCost = entry.getValue()[1];

                // Initialize or update the range and count for both normalCost and vCost
                Integer[][] ranges = resultMap.getOrDefault(className, new Integer[][]{{normalCost, normalCost, 0, 0}, {vCost, vCost, 0, 0}});

                // Update normalCost range
                ranges[0][0] = Math.min(ranges[0][0], normalCost);  // Min normalCost
                ranges[0][1] = Math.max(ranges[0][1], normalCost);  // Max normalCost
                ranges[0][2]++;  // Count normalCost occurrences
                ranges[0][3] = totalFiles;  // Track total files expected

                // Update vCost range
                ranges[1][0] = Math.min(ranges[1][0], vCost);  // Min vCost
                ranges[1][1] = Math.max(ranges[1][1], vCost);  // Max vCost
                ranges[1][2]++;  // Count vCost occurrences
                ranges[1][3] = totalFiles;  // Track total files expected

                resultMap.put(className, ranges);
            }
        }

        return resultMap;
    }

    // Method to display comparison results (optional display, can be removed if not needed)
    private static void displayComparisonResults(Map<String, Integer[][]> comparisonResults) {
        System.out.println("Class Name \t NormalCost Count \t NormalCost Range \t vCost Count \t vCost Range");
        for (Map.Entry<String, Integer[][]> entry : comparisonResults.entrySet()) {
            String className = entry.getKey();
            Integer[][] ranges = entry.getValue();

            System.out.printf("%s \t %d \t [%d, %d] \t %d \t [%d, %d]%n",
                    className,
                    ranges[0][2], ranges[0][0], ranges[0][1],
                    ranges[1][2], ranges[1][0], ranges[1][1]);
        }
    }

    // Method to display the list of differing classes and their ranges
    private static void displayDifferences(Map<String, Integer[][]> comparisonResults, int totalFiles) {
        System.out.println("\nClasses with Differences or Missing Entries:");
        System.out.println("Class Name \t NormalCost Range \t vCost Range");

        for (Map.Entry<String, Integer[][]> entry : comparisonResults.entrySet()) {
            String className = entry.getKey();
            Integer[][] ranges = entry.getValue();

            // Check if there is a difference in either normalCost or vCost
            boolean normalCostDiffers = !ranges[0][0].equals(ranges[0][1]);
            boolean vCostDiffers = !ranges[1][0].equals(ranges[1][1]);

            // Check if the class is missing in any file (appears in fewer files than totalFiles)
            boolean classIsMissing = ranges[0][2] < totalFiles || ranges[1][2] < totalFiles;

            if (normalCostDiffers || vCostDiffers || classIsMissing) {
                int normalCostRange = ranges[0][1] - ranges[0][0];
                int vCostRange = ranges[1][1] - ranges[1][0];

                String missingInfo = classIsMissing ? " (MISSING IN SOME FILES)" : "";

                System.out.printf("%s \t [%d, %d] (Range: %d) \t [%d, %d] (Range: %d)%s%n",
                        className,
                        ranges[0][0], ranges[0][1], normalCostRange,
                        ranges[1][0], ranges[1][1], vCostRange,
                        missingInfo);
            }
        }
    }
}
