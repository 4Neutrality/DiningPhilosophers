import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * This class will serve as a Monitor for the dining philosophers problem, and will ensure that both
 * chopsticks will be picked up at the same time without allowing deadlock or starvation.
 *
 * @author Kevin J James, Johnathon Malott
 * @version 04.15.15
 */
public class DinerMonitor {
    public static final int DINERS = 5;
    /** Dinner to the left of current Dinner.*/
    private static final int LEFT_DINER = 4;
    /** Dinner to the right of current Dinner.*/
    private static final int RIGHT_DINER = 1;
    /** An enumeration to hold the different state a philosopher can be in. */
    private enum State {EATING, THINKING, HUNGRY};
    /** Holds the state of each philosopher */
    private State[] states;
    private Condition[] self;
    Lock key = new ReentrantLock();

    /**
     * Creates a monitor for a given number of philosophers. Initially all philosophers are thinking.
     */
    public DinerMonitor() {
        states = new State[DINERS];
        self = new Condition[DINERS];
        for (int i = 0; i < DINERS; i++) {
            states[i] = State.THINKING;
            self[i]= key.newCondition();
        }
    }

    /**
     * This method will set dinner's state to hungry and then have the diner pick up 
     * both chopsticks if either diner to his sides are not eating or wait.
     *
     * @param id the given unique philosopher ID
     */
    public void takeChopsticks(int id) {
        /* Set diner state to hungry */
        states[id] = State.HUNGRY;
        key.lock();
        try{
           //if(!test(id))
               //self[id].await();
        
           //states[id] = State.EATING;
           test(id);
           if(states[id] != State.EATING) self[id].await();
        } catch (InterruptedException ie) {
           System.out.println("Philosopher" + id + " was interrupted!");
        } finally {
            key.unlock();
        }
        /**while (!test(id)) {
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println("Philosopher" + id + " was interrupted!");
            }
        }*/
    }
    
    /**
     * This method replaces the chopsticks after the diner is finished eating.
     *
     * @param id the given unique philosopher ID
     */
    public void replaceChopsticks(int id) {
        key.lock();
        states[id] = State.THINKING;
        test((id + LEFT_DINER) % DINERS);
        test((id + RIGHT_DINER) % DINERS);
        //self[id].signalAll();
        key.unlock();
    }

    /**
     * This method tests whether it is OK for the diner to pick up their chopsticks
     * by checking to see if the chopstick to their left and right are free. Also
     * checking to make sure the person is in their hungry state. 
     *
     * @param id The given unique philosopher ID.
     * @return True if both chopsticks are free.
     */
    public void test(int id) {
        //boolean chopsFree = false;

        /* Check if the diner on the left and right is eating. 
         * Also check if person is in the hungry state. */
        if ((states[(id + LEFT_DINER) % DINERS] != State.EATING) && 
           (states[(id + RIGHT_DINER) % DINERS] != State.EATING) &&
           (states[id] == State.HUNGRY)) {
              //chopsFree = true;
              states[id] = State.EATING;
              self[id].signal();
            }
        
        //return chopsFree;
    }

    /**
     * This is a simple getter method, which returns the state of the given philosopher.
     *
     * @param id the given unique philosopher ID
     * @return the state of the philosopher
     */
    public String getState(int id) {
       return "" + this.states[id];
    }
}
