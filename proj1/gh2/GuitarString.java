package gh2;

// TODO: uncomment the following import once you're ready to start this portion
import deque.LinkedListDeque;
import deque.Deque;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
    private Deque<Double> buffer;
    
    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer array with zeros.
        int deque_size = (int) Math.round(this.SR / frequency);
        this.buffer = new LinkedListDeque<Double>();
        
        for (int i = 0; i < deque_size; i++) {
            this.buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        double r = Math.random() - 0.5;
        for (int i = 0; i < this.buffer.size(); i++) {
            this.buffer.removeFirst();
            this.buffer.addLast(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**
        
        for (int j = 0; j < this.buffer.size(); j++) {
            double first = this.buffer.get(0);
            double second = this.buffer.get(1);
            double newDouble = this.DECAY * 0.5 * (first + second);
            this.buffer.removeFirst();
            this.buffer.addLast(newDouble);
        }
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return this.buffer.get(0);
    }
}
    // TODO: Remove all comments that say TODO when you're done.
