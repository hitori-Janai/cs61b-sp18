import org.junit.Test;

import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        for (int i = 0; i < 500; i++) {
//            assertTrue(i==i);
//            System.out.println(i);
            //==比较地址  128开始new包装对象
            assertTrue("error num :" + i, Flik.isSameNumber(i, i));
        }
    }
}
