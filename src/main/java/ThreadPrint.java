import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

/**
 * Class which implements Runnable to control Threads execution
 */
public class ThreadPrint implements Runnable{
    /**
     * Id of the Thread
     */
    private int id;
    /**
     * List of Date of Births to be printed
     */
    private LinkedList<DateBirth> birthsToPrint;
    /**
     * Previous CountDownLatch to control execution
     */
    private CountDownLatch prev;
    /**
     * Next CountDownLatch to control execution
     */
    private CountDownLatch next;

    public ThreadPrint(LinkedList<DateBirth> birthsToPrint,
                        int id,
                       CountDownLatch prev,
                       CountDownLatch next) {
        if (birthsToPrint == null || birthsToPrint.size() == 0) {
            throw new NullPointerException("Something wrong happened with your execution. Please, try it again or contact the support team.");
        }
        this.id = id;
        this.birthsToPrint = birthsToPrint;
        this.prev = prev;
        this.next = next;
    }

    /**
     * Run execution method
     */
    @Override
    public void run() {
        try {
            // Wait until the previous thread is done
            prev.await();
            // wait to start printing
            Thread.sleep(1000);
            for (DateBirth birth : birthsToPrint) {
                System.out.print("Thread " + id + ": " + birth.getDateOfBirthString() + "\n");
            }
            System.out.print("\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // tells to CountDownLatch that it is done.
            next.countDown();
        }
    }
}
