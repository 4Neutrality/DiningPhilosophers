/**
 * This class will serve as a Monitor for the dining philosophers problem, and will ensure that both
 * chopsticks will be picked up at the same time.
 *
 * @author Kevin J James
 * @version 04.15.15
 */
public class DinerMonitor implements PhilosopherInterface {
    /* An enumeration to hold the different state a philosopher can be in. */
    private enum State {EATING, THINKING, HUNGRY};
    /* Holds the state of each philosopher */
    private State[] states;

    /**
     * Creates a monitor for a given number of philosophers. Initially all philosophers are thinking.
     *
     * @param diners the given number of philosophers
     */
    public DinerMonitor(int diners) {
        this.states = new State[diners];
        for (State s : states) {
            s = State.THINKING;
        }
    }

    /**
     * This method will have the diner pick up both chopsticks or wait if either diner of his sides
     * are eating.
     *
     * @param id the given unique philosopher ID
     */
    public synchronized void takeChopsticks(int id) {
        /* Set diner state to hungry */
        states[id] = State.HUNGRY;
        System.out.println("Philosopher" + id + "  is HUNGRY!");
        System.out.flush();

        while (!test(id)) {
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println("Philosopher" + id + " was interrupted!");
            }
        }
        states[id] = State.EATING;
    }

    /**
     * This method replaces the chopsticks after the diner is finished eating.
     *
     * @param id the given unique philosopher ID
     */
    public synchronized void replaceChopsticks(int id) {
        states[id] = State.THINKING;
        notifyAll();
    }

    /**
     * This method tests whether it is OK for the diner to pick up his chopsticks.
     *
     * @param id the given unique philosopher ID
     */
    public boolean test(int id) {
        /* Check if the diner on the left is eating */
        if (states[(id + 1) % DINERS] == State.EATING)
            return false;
        /* Check if the diner on the right is eating */
        if (states[(id + DINERS - 1) % DINERS] == State.EATING)
            return false;
        /* No adjacent diners are eating */
        return true;
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
