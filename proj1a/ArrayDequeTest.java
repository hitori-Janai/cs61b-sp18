import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class ArrayDequeTest {
    ArrayDeque<Integer> ad = new ArrayDeque<>();

    @After
    public void bye() {
        System.out.println("bye.........");
    }
    
    @Test
    public void testAdd() {
        assertEquals(true, ad.isEmpty());
        ad.addFirst(1);
        ad.printDeque();
        assertEquals(1, ad.size());
        System.out.println();
        ad.addFirst(2);
        ad.printDeque();
        System.out.println();

    }

    @Test
    public void testGet() {
        ad.addFirst(100);
        print();
        ad.addFirst(200);
        print();
        ad.addLast(300);
        print();
        ad.addFirst(400);
        print();
        System.out.println(ad.get(-1));
        System.out.println(ad.get(0));
        System.out.println(ad.get(1));
        System.out.println(ad.get(2));
        System.out.println(ad.get(3));
        System.out.println(ad.get(4));


    }
    @Test
    public void testRemove(){
        ad.addFirst(100);
        ad.addFirst(200);
        ad.addLast(300);
        ad.addFirst(400);
        print();
        ad.removeFirst();
        print();
        ad.removeLast();
        print();
    }

    public void print(){
        ad.printDeque();
        System.out.println(" :"+ad.items.length);
    }

    @Test
    public void testResize() {
        for (int i = 0; i < 16; i++) {
            ad.addFirst(i);
            print();
        }
        for (int i = 0; i < 16; i++) {
            ad.addLast(-i);
            print();
        }

        for (int i = 0; i < 16; i++) {
            ad.removeFirst();
            print();
        }

        for (int i = 0; i < 16; i++) {
            ad.removeLast();
            print();
        }
    }

    @Test
    public void tetsLargeAddRemove() {
        for (int i = 0; i < 10000; i++) {
            ad.addFirst(i);
        }
        System.out.println(ad.items.length);
        while (!ad.isEmpty()) {
            ad.removeLast();
        }
        print();
    }

}
