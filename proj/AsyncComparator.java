import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class AsyncComparator {

    private static final int MAX_TOP_METHODS = 5;
    private static final String DIRECTORY_PATH = "/home/hburchell/Repos/graal-dev/graal-instrumentation/compiler/LIRCostInformation/";
    private static final String CSV_OUTPUT_PATH = "output.csv";  // Update this path
    private static final String[] BENCHMARKS = {
        "CD", "DeltaBlue", "Havlak", "Json", "Richards", "Bounce", "List", "Mandelbrot", 
        "NBody", "Permute", "Queens", "Sieve", "Storage", "Towers"
    };

    public static void main(String[] args) {
        List<String[]> allTopMethods = new ArrayList<>();

        for (String benchmark : BENCHMARKS) {
            String[] variants = {"_Slowdown", "_NoSlowdown"};
            for (String variant : variants) {
                String filename = DIRECTORY_PATH + benchmark + variant + ".txt";
                List<String[]> topMethods = asyncHottestMethods(filename, benchmark, variant);
                allTopMethods.addAll(topMethods);
            }
        }

        writeResultsToCSV(allTopMethods);
    }

    public static List<String[]> asyncHottestMethods(String filename, String benchmark, String variant) {
        List<String[]> topMethods = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            boolean foundHeader = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Check for the header line that precedes the top methods
                if (line.equals("----------  -------  -------  ---")) {
                    foundHeader = true;
                    continue;
                }

                if (foundHeader && topMethods.size() < MAX_TOP_METHODS) {
                    String[] split = line.split("\\s+");
                    String methodName;
                    String methodWeight;

                    // Adjust the split indices based on the format
                    if (split.length >= 5 && split[0].isEmpty()) {
                        methodName = split[4];
                        methodWeight = split[2];
                    } else if (split.length >= 4) {
                        methodName = split[3];
                        methodWeight = split[1];
                    } else {
                        continue;  // Skip malformed lines
                    }

                    topMethods.add(new String[]{benchmark, variant, methodName, methodWeight});
                }

                if (topMethods.size() >= MAX_TOP_METHODS) {
                    break;
                }
            }

            if (topMethods.size() < MAX_TOP_METHODS) {
                System.out.println("Warning: Less than " + MAX_TOP_METHODS + " methods were found in " + filename);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An error occurred while processing the file " + filename);
            e.printStackTrace();
        }

        return topMethods;
    }

    public static void writeResultsToCSV(List<String[]> results) {
        try (FileWriter writer = new FileWriter(CSV_OUTPUT_PATH)) {
            writer.append("Benchmark,Variant,Method,Cost\n");
            for (String[] result : results) {
                writer.append(String.join(",", result));
                writer.append("\n");
            }
            System.out.println("Results successfully written to " + CSV_OUTPUT_PATH);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }
}
