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
            Map<String, Integer[]> comparisonResults = compareJSONFiles(jsonFiles);

            // Display the results
            displayComparisonResults(comparisonResults);

        } catch (IOException e) {
            System.err.println("Error processing JSON files: " + e.getMessage());
        }
    }

    // Method to find all JSON files in the specified directory
    private static File[] findJSONFiles(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.listFiles((dir, name) -> name.endsWith(".json"));
    }

    // Method to read a JSON file and return a map of class names and their associated numbers
    private static Map<String, Integer> readJSONFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder jsonContent = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }
        reader.close();

        JSONObject jsonObject = new JSONObject(jsonContent.toString());
        Map<String, Integer> jsonMap = new HashMap<>();

        for (String key : jsonObject.keySet()) {
            jsonMap.put(key, jsonObject.getInt(key));
        }

        return jsonMap;
    }

    // Method to compare JSON files and return a map of class names and their associated ranges
    private static Map<String, Integer[]> compareJSONFiles(File[] files) throws IOException {
        Map<String, Integer[]> resultMap = new HashMap<>();

        for (File file : files) {
            Map<String, Integer> jsonMap = readJSONFile(file.getAbsolutePath());

            for (Map.Entry<String, Integer> entry : jsonMap.entrySet()) {
                String className = entry.getKey();
                int value = entry.getValue();

                Integer[] range = resultMap.getOrDefault(className, new Integer[]{value, value, 0});
                range[0] = Math.min(range[0], value);  // Update min
                range[1] = Math.max(range[1], value);  // Update max
                range[2]++;  // Increment count

                resultMap.put(className, range);
            }
        }

        return resultMap;
    }

    // Method to display comparison results
    private static void displayComparisonResults(Map<String, Integer[]> comparisonResults) {
        System.out.println("Class Name \t Count \t Range");
        for (Map.Entry<String, Integer[]> entry : comparisonResults.entrySet()) {
            String className = entry.getKey();
            Integer[] range = entry.getValue();
            System.out.printf("%s \t %d \t [%d, %d]%n", className, range[2], range[0], range[1]);
        }
    }
}
