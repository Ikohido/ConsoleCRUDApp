import org.junit.Test;
import org.main.TestClass;

import static org.junit.Assert.assertEquals;

public class TestClassTest {
    @Test
    public void sumTest(){
        TestClass test = new TestClass();
        assertEquals(3, test.sum(1, 2));
    }
}
