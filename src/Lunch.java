import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This is the Lunch class, and it will serve as a driver for the well-known dining philosophers deadlock
 * problem. It will execute the relevant methods associated with the problem domain.
 *
 * @author Kevin J James, Johnathon Malott
 * @version 04.14.15
 */
public class Lunch {
    /**
     * This method will serve as the main method for this driver class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create a fixed size thread pool */
        ExecutorService pool = Executors.newFixedThreadPool(PhilosopherInterface.DINERS);
        /* An array to hold dining philosophers */
        Diner[] diners = new Diner[PhilosopherInterface.DINERS];
        /* Initialize monitor for dining philosophers */
        DinerMonitor monitor = new DinerMonitor();


        /* Initialize diners */
        for (int i = 0; i < PhilosopherInterface.DINERS; i++) {
            diners[i] = new Diner(i, monitor);
        }

        /* Spawn off threads for execution */
        for (Diner d : diners) {
            pool.execute(d);
        }
        pool.shutdown();

        /* Wait for all threads to finish */
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {
            System.out.print("Error: interrupted." + ie);
            System.exit(-1);
        }

        System.out.println("\n-------This worked!-------\n");
    }
}
