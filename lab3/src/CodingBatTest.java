import org.junit.Test;

import static org.junit.Assert.*;

public class CodingBatTest {
    @Test
    public void TestlastTwo(){
        assertEquals("codign", new CodingBat().lastTwo("coding"));
        assertEquals("cta", new CodingBat().lastTwo("cat"));
        assertEquals("ba", new CodingBat().lastTwo("ab"));
        assertEquals("a", new CodingBat().lastTwo("a"));
        assertEquals("", new CodingBat().lastTwo(""));

    }

    @Test
    public void TestmiddleWay1(){
        assertArrayEquals(new int[]{2,5}, new CodingBat().middleWay(new int[]{1,2,3},new int[]{4,5,6}));

    }
    @Test
    public void TestmiddleWay2(){
        assertArrayEquals(new int[]{6,2}, new CodingBat().middleWay(new int[]{1,6,3},new int[]{4,2,6}));

    }
    @Test
    public void TestmiddleWay3(){
        assertArrayEquals(new int[]{2,4}, new CodingBat().middleWay(new int[]{5,2,8},new int[]{2,4,6}));

    }
    @Test
    public void Test1in1020() {
        assertEquals((true),new CodingBat().in1020(12,99));

    }
    @Test
    public void Test2in1020() {
        assertEquals((true),new CodingBat().in1020(21,12));

    }
    @Test
    public void Test3in1020() {
        assertEquals((false),new CodingBat().in1020(21,21));

    }

}