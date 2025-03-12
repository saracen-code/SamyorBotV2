package com.java.saskartan;

import java.io.*;
import java.nio.file.*;

public class PhonologyBot {
    static String C_PROGRAM = Paths.get("src/main/java/com/java/saskartan/sounds").toAbsolutePath().toString(); // Path to compiled C program

    public static void main(String[] args) throws IOException {
        String inputWord = "lector"; // Example input from user
        String result = applyPhonology(inputWord);
        System.out.println("Converted: " + result);
    }

    public static String applyPhonology(String word) {
        String result = ""; // To store the result from standard output

        try {
            // Step 2: Execute the C program and provide simulated input for prompts
            ProcessBuilder pb = new ProcessBuilder(C_PROGRAM);
            pb.redirectErrorStream(true); // Capture errors as well
            Process process = pb.start();

            // Simulate user input to the C program's prompt (lexicon and rule file)
            OutputStream outputStream = process.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(word);  // Respond with the lexicon file

            // Step 3: Capture the standard output of the C program
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            // Step 4: Collect only the relevant lines from the output stream
            while ((line = reader.readLine()) != null) {
                // Capture only the final result of the C program (usually the last word processed)
                if (line.trim().length() > 0) {
                    output.setLength(0);  // Clear previous output if necessary
                    output.append(line.trim());  // Save only the final result
                }
            }

            // Step 5: Capture any potential errors
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println("C Program Error: " + line);
            }

            // Step 6: Wait for the C program to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("C program exited with error code: " + exitCode);
            }

            // Step 7: Return the final result
            result = output.toString().trim(); // Clean the result if necessary

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result = "Error processing phonology.";
        }

        return result;
    }
}
