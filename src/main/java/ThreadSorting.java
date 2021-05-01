import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class ThreadSorting {

    /**
     * Main Method to Execute
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("input.txt");
        try {
            printSortedBirths(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Print method to Read the file and print a sorted birth.
     * @param file
     * @throws IOException
     */
    public static void printSortedBirths(File file) throws IOException {
        // First CountDownLatch to control the order of execution.
        CountDownLatch first = new CountDownLatch(1);
        // Previous CountDownLatch to verify if CountDownLatch is finished.
        CountDownLatch prev = first;
        final int nThreads = 2; // Number of Thread to execute in parallel.
        for (int threadId = 0; threadId < nThreads; threadId++) {
            LinkedList<DateBirth> births = new LinkedList<>(); // Ordered Date of Births
            Files.lines(file.toPath())
                    .map(s -> s.trim())
                    .filter(s->!s.equals("")) // Removing the Blank line of the file.
                    .forEach(line -> {
                        String[] dateString = line.split("\\s+"); // Regex to remove all extra spaces between year / Month and day
                        String id = dateString[0].trim();
                        String month = dateString[1].trim();
                        String day = dateString[2].trim();
                        String year = dateString[3].trim();
                        // Instantiate a DateBirth object with the date of birth
                        DateBirth dateBirth = new DateBirth(id, year, month, day);
                        births.add(dateBirth);
                    });
            Collections.sort(births); // Order the date of Birth based on CompareTo method.
            // The next CountDownLatch in a roll to control the list of executions.
            CountDownLatch next = new CountDownLatch(1);
            // Thread based on Runnable implementation
            Thread thread = new Thread(new ThreadPrint(births, threadId + 1, prev, next));
            thread.start();
            prev = next; // Set the previous CountDownLatch to verify if it finished.
        }
        // Call countDown method to start to count if the CountDownLatch is done.
        first.countDown();
    }

}
