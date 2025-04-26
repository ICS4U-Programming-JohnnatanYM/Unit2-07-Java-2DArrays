import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * This program reads a list of students and assignments,
 * generates random marks with a mean of 75 over 10 assignments,
 * and writes the results to a file and prints them to the terminal.
 *
 * @author Johnnatan Yasin Medina
 * @version 1.0
 * @since 2025-04-25
 */
public final class TwoDArrays {
    /**
     * This is to satisfy the style checker.
     * @exception IllegalStateException Utility class.
     * @see IllegalStateException
     */
    private TwoDArrays() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * Main Method.
     *
     * @param args Unused.
     */
    public static void main(final String[] args) {
        ArrayList<String> studentArray = readFile("students.txt");
        ArrayList<String> assignmentsArray = readFile("assignments.txt");

        if (studentArray.isEmpty() || assignmentsArray.isEmpty()) {
            System.out.println("One or both files are empty or missing.");
            return;
        }

        // Generate marks
        String[][] results = generateMarks(studentArray, assignmentsArray);

        // Output to terminal
        for (String[] row : results) {
            System.out.print(row[0] + ": ");
            for (int i = 1; i < row.length; i++) {
                System.out.print("\"" + row[i] + "\" ");
            }
            System.out.println();
        }

         // Output to marks.csv
        try (FileWriter writer = new FileWriter("marks.csv")) {
            // Write header
            writer.write(String.format("%-15s", "Student"));
            for (String assignment : assignmentsArray) {
                writer.write(String.format("%-15s", assignment));
            }
            writer.write("\n");

            // Write student rows
            for (String[] row : results) {
                for (String entry : row) {
                    writer.write(String.format("%-15s", entry));
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to marks.csv.");
        }
    }

    /**
     * Reads a file line by line into an ArrayList.
     *
     * @param filename
     * @return List of strings from the file
     */
    public static ArrayList<String> readFile(final String filename) {
        ArrayList<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    list.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found.");
        }
        return list;
    }

    /**
     * Generates random marks using Gaussian
     * with a mean of 75 and standard deviation of 10.
     *
     * @param studentArray
     * @param assignmentsArray
     * @return A 2D array of student names and their marks
     */
    public static String[][] generateMarks(
        final ArrayList<String> studentArray,
        final ArrayList<String> assignmentsArray) {

        Random random = new Random();
        int numStudents = studentArray.size();
        int numAssignments = assignmentsArray.size();

        String[][] results = new String[numStudents][numAssignments + 1];

        for (int i = 0; i < numStudents; i++) {
            results[i][0] = studentArray.get(i);

            for (int j = 0; j < numAssignments; j++) {
                double mark = random.nextGaussian() * 10 + 75;
                int finalMark = Math.max(0, Math.min(
                    100, (int) Math.round(mark)));
                results[i][j + 1] = String.valueOf(finalMark);
            }
        }

        return results;
    }
}
