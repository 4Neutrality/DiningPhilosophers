import java.util.Random;

/**
 * This is the Diner class, and it represents a dining philosopher at the table. It holds relevant methods
 * associated with being a Diner object.
 *
 * @author Kevin J James, Johnathon Malott
 * @version 04.14.15
 */
public class Diner implements Runnable {
    /** The max wait time is 5 seconds */
    private static final int WAIT_TIME = 5000;
    /** This variable holds the unique ID of the dining philosopher */
    private int id;
    /** This variable represents the monitor for the philosopher */
    DinerMonitor monitor;
    /** Random number generator to generate random eat/think times */
    private Random rdm = new Random(WAIT_TIME);
    /** Holds variable representing is thread is interrupted */
    Boolean cancelled;


    /**
     * This is a basic constructor for a Diner object.
     *
     * @param id the given unique ID of the diner.
     * @param monitor the given monitor.
     */
    public Diner(int id, DinerMonitor monitor) {
        this.monitor = monitor;
        this.id = id;
    }

    /**
     * This method simulates a diner thinking using a random amount of time and changes the state of the
     * diner to thinking.
     *
     * @throws InterruptedException
     */
    public void think() throws InterruptedException{
        /* Print the state of the diner to the screen. */
        printStateAndWait();
        monitor.takeChopsticks(this.id);
    }

    /**
     * This method simulates a diner eating using a random amount of time and changes the state of the
     * diner to eating.
     *
     * @throws InterruptedException
     */
    public void eat() throws InterruptedException{
        /* Print the state of the diner to the screen. */
        printStateAndWait();
        monitor.replaceChopsticks(this.id);
    }

    /**
     * This method is called whenever the thread executes.
     */
    public void run() {
        this.cancelled = false;
        try {
            while(!cancelled) {
                think();
                eat();
                if (isInterrupted())
                    this.cancelled = true;
            }
        } catch (InterruptedException ie) {
            System.out.println("Philosopher" + this.id + " was interrupted!");
        }
    }

    /**
     * This method checks periodically if the current thread is interrupted.
     *
     * @return true if interrupted, false otherwise.
     */
    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }

    /**
     * Prints the current state of the philosopher and then waits for a random amount
     * of time, mazimum is 5 seconds.
     *
     * @throws InterruptedException
     */
    private void printStateAndWait() throws InterruptedException{
        System.out.println("Philosopher " + this.id + "  is " + monitor.getState(this.id) + "!");
        System.out.flush();
        Thread.sleep(rdm.nextInt(WAIT_TIME) + 1); // Sleep for no more than 5 seconds
    }
}
