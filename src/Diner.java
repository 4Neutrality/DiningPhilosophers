import java.util.Random;
import java.util.concurrent.locks.Lock;

/**
 * This is the Diner class, and it represents a dining philosopher at the table. It holds relevant methods
 * associated with being a Diner object.
 *
 * @author Kevin J James
 * @version 04.14.15
 */
public class Diner implements PhilosopherInterface, Runnable {
    /** The max wait time is 5 seconds **/
    private static final int WAIT_TIME = 5000;

    /** This variable holds the state of the dining philosopher **/
    private State state;
    /** This variable holds the unique ID of the dining philosopher **/
    private int id;
    /** This variable represents the left chopstick **/
    Lock leftChop;
    /** This variable represents the right chopstick **/
    Lock rightChop;
    /** Random number generator to generate random eat/think times **/
    private Random rdm = new Random(WAIT_TIME);


    /**
     * This is a basic constructor for a Diner object.
     *
     * @param id the given unique ID of the diner
     * @param leftChop the given left chopstick
     * @param rightChop the given right chopstick
     */
    public Diner(int id, Lock leftChop, Lock rightChop) {
        this.leftChop = leftChop;
        this.rightChop = rightChop;
        this.id = id;
        this.state = State.THINKING;
    }

    /**
     * This method will have the diner try and take chopsticks for his left and right.
     */
    public void takeChopsticks() {
        /* Take left chopstick */
        this.leftChop.lock();
        /* Take right chopstick */
        this.rightChop.lock();
        System.out.println("Philosopher" + this.id + "  is holding his chopsticks!");
        System.out.flush();
    }

    /**
     * This method will replace the chopsticks on the left and right of the diner.
     */
    public void replaceChopsticks() {
        /* Replace left chopstick */
        this.leftChop.unlock();
        /* Replace right chopstick */
        this.rightChop.unlock();
    }

    /**
     * This method simulates a diner thinking using a random amount of time and changes the state of the
     * diner to thinking.
     *
     * @throws InterruptedException
     */
    public void think() throws InterruptedException {
        setState(State.THINKING);
        /* Print the state of the diner to the screen. */
        System.out.println("Philosopher" + this.id + "  is " + this.state + "!");
        System.out.flush();
        Thread.sleep(rdm.nextInt(WAIT_TIME) + 1); // Sleep for no more than 5 seconds
    }

    /**
     * This method simulates a diner eating using a random amount of time and changes the state of the
     * diner to eating.
     *
     * @throws InterruptedException
     */
    public void eat() throws InterruptedException {
        setState(State.EATING);
        /* Print the state of the diner to the screen. */
        System.out.println("Philosopher" + this.id + "  is " + this.state + "!");
        System.out.flush();
        Thread.sleep(rdm.nextInt(WAIT_TIME) + 1); // Sleep for no more than 5 seconds
    }

    /**
     * This method is called whenever the thread executes.
     */
    public void run() {
        try {
            while(true) {
                think();
                takeChopsticks();
                eat();
                replaceChopsticks();
            }
        } catch (InterruptedException ie) {
            System.out.println("Philosopher" + this.id + " was interrupted!");
        }
    }

    /**
     * This is a simple setter method for the state of the diner.
     *
     * @param state the given state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * This is an enumeration of states of which the diner can be in.
     */
    public enum State {
        THINKING,
        EATING,
        HUNGRY
    }
}
