import java.util.Random;

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
    /** This variable holds the ID of hte dining philosopher **/
    private int id;
    /** Random number generator to generate random eat/think times **/
    private Random rdm = new Random(WAIT_TIME);

    // ReentrantLock lock  <-- ONLY CAN USE ONE

    /**
     * This is a basic constructor for a Diner object.
     *
     * @param state the given state of the diner
     * @param id the given ID of the diner
     */
    public Diner(State state, int id) {
        this.state = state;
        this.id = id;
    }

    /**
     * This method will have the diner take
     */
    public void takeChopsticks() {}

    public void replaceChopsticks() {}

    public void think() {}

    public void eat() {}

    public void run() {
        System.out.println("Philosopher" + this.id + "  is " + this.state + "!");
    }

    public enum State {
        THINKING,
        EATING,
        HUNGRY
    }
}
