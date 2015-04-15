import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is the Lunch class, and it will serve as a driver for the well-known dining philosophers deadlock
 * problem. It will execute the relevant methods associated with the problem domain.
 *
 * @author Kevin J James
 * @version 04.14.15
 */
public class Lunch {
    private static final int PHILOSOPHERS = 5;
    /**
     * This method will serve as the main method for this driver class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create a fixed size thread pool */
        ExecutorService pool = Executors.newFixedThreadPool(PHILOSOPHERS);
        /* An arraylist to hold dining philosophers */
        ArrayList<Diner> diners = new ArrayList<>();
        /* Represent chopsticks with a reentrant lock */
        Lock[] chopsticks = new ReentrantLock[PHILOSOPHERS];

        for (Lock c : chopsticks) {
            c = new ReentrantLock();
        }

        /* Initialize diners */
        for (int i = 0; i < PHILOSOPHERS; i++) {
            /* new Diner(ID, LEFTCHOPSTICK, RIGHTCHOPSTICK */
            diners.add(new Diner(i, chopsticks[i], chopsticks[(i+1)%PHILOSOPHERS]));
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
