package symulator;

import org.junit.Test;
import static org.junit.Assert.*;

public class PozycjaTest {

    @Test
    public void testMoveToStraight() {

        Pozycja p = new Pozycja(0, 0);
        Pozycja cel = new Pozycja(10, 0);


        p.moveTo(2.0f, 1.0f, cel);


        assertEquals(2.0, p.getX(), 0.001);
        assertEquals(0.0, p.getY(), 0.001);
    }
}