package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        System.out.println(arb.peek());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }

    @Test
    public void addTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue("1");
        arb.enqueue("2");
        arb.enqueue("3");
        arb.enqueue("4");
        arb.enqueue("5");
        arb.enqueue("6");
        arb.enqueue("7");
        arb.enqueue("8");
        arb.enqueue("9");
        arb.enqueue("10");
        // arb.enqueue("11");

        assertEquals("1", arb.peek());
        assertEquals("1", arb.dequeue());
        assertEquals("2", arb.dequeue());
        assertEquals("3", arb.peek());
        assertEquals("3", arb.dequeue());
        assertEquals("4", arb.dequeue());
        assertEquals("5", arb.dequeue());
        assertEquals("6", arb.dequeue());
        assertEquals("7", arb.dequeue());
        assertEquals("8", arb.dequeue());
        assertEquals("9", arb.dequeue());
        assertEquals("10", arb.dequeue());

        arb.enqueue("1");
        arb.enqueue("2");
        arb.enqueue("3");
        arb.enqueue("4");
        arb.enqueue("5");
        arb.enqueue("6");
        arb.enqueue("7");
        arb.enqueue("8");
        arb.enqueue("9");
        arb.enqueue("10");

        assertEquals("1", arb.peek());
        assertEquals("1", arb.dequeue());
        assertEquals("2", arb.dequeue());
        assertEquals("3", arb.peek());
        assertEquals("3", arb.dequeue());
        assertEquals("4", arb.dequeue());
        assertEquals("5", arb.dequeue());
        assertEquals("6", arb.dequeue());
        assertEquals("7", arb.dequeue());
        assertEquals("8", arb.dequeue());
        assertEquals("9", arb.dequeue());
        assertEquals("10", arb.dequeue());
        assertEquals("10", arb.dequeue());
    }


} 
