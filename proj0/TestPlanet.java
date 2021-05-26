import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPlanet {
    public static void main(String[] args) {

    }

    @Test
    public void testCalcForceExertedBy() {

        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        assertEquals(133.4, p1.calcForceExertedBy(p2), 0);
    }

    @Test
    public void testDouble() {
        assertEquals(1, 1.0 / 3 * 3, 0);//ok
        // assertEquals(1, 1.0 / 3 * 3);//ok
    }

    @Before
    public void hi() {
        System.out.println("start....");
    }

    @After
    public void bye() {
        System.out.println("over....");
    }
}
