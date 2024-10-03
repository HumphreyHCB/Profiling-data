import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PerfComparator {

    private static final int MAX_TOP_METHODS = 5;
    private static final String DIRECTORY_PATH = "/home/hburchell/Repos/graal-dev/graal-instrumentation/vm/AOTDumps/PerfDumps/";
    private static final String CSV_OUTPUT_PATH = "PerfSlowdownComparison.csv";  // Update this path
    private static final String[] BENCHMARKS = {
        "CD", "DeltaBlue", "Havlak", "Json", "Richards", "Bounce", "List", "Mandelbrot", 
        "NBody", "Permute", "Queens", "Sieve", "Storage", "Towers"
    };

    public static void main(String[] args) {
        List<String[]> allTopMethods = new ArrayList<>();

        for (String benchmark : BENCHMARKS) {
            String[] variants = {"_Slowdown", "_NoSlowdown"};
            for (String variant : variants) {
                //String filenameAsync = DIRECTORY_PATH + benchmark + variant + ".txt";
                String filenamePerf = DIRECTORY_PATH + benchmark + variant + ".data";  // Example file for perf dump

                // Process Async dump
                // List<String[]> topAsyncMethods = asyncHottestMethods(filenameAsync, benchmark, variant);
                // allTopMethods.addAll(topAsyncMethods);

                // Process perf dump
                List<String[]> topPerfMethods = perfHottestMethods(filenamePerf, benchmark, variant);
                allTopMethods.addAll(topPerfMethods);
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

    public static List<String[]> perfHottestMethods(String filename, String benchmark, String variant) {
        List<String[]> topMethods = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command("sudo", "perf", "report", "--stdio", "-i", filename);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();

            boolean foundHeader = false;
            while (line != null) {
                // Check for the header line in the perf output
                if (line.contains("# Overhead  Command")) {
                    reader.readLine();  // Skip next two lines
                    reader.readLine();
                    foundHeader = true;
                }

                if (foundHeader && topMethods.size() < MAX_TOP_METHODS) {
                    String[] split = reader.readLine().split("\\s+", 7);
                    if (split.length >= 7) {
                        String methodName = split[6];  // Symbol (method name)
                        String methodWeight = split[1];  // Overhead
                        topMethods.add(new String[]{benchmark, variant, methodName, methodWeight});
                    }
                }

                if (topMethods.size() >= MAX_TOP_METHODS) {
                    break;
                }

                line = reader.readLine();
            }

            if (topMethods.size() < MAX_TOP_METHODS) {
                System.out.println("Warning: Less than " + MAX_TOP_METHODS + " methods were found in " + filename);
            }

        } catch (IOException e) {
            System.err.println("An error occurred while processing the perf file: " + filename);
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
