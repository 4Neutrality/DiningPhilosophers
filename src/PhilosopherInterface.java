/**
 * This is the PhilosopherInterface, which will be implemented by philosopher objects.
 * This interface sets the maximum number of philosophers at the table along with
 * the methods to take and release chopsticks.
 *
 * @author Kevin J James, Johnathon Malott
 * @version 04.14.15
 */
public interface PhilosopherInterface {
    /**Maximum number of philosophers.*/
    public static final int DINERS = 5;
    /**Allows philosopher to enter hungry state.*/
    public void replaceChopsticks(int id);
    /**Allows philosopher to enter eating state.*/
    public void takeChopSticks(int id);
}
