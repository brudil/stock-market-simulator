package swe;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void getFive() throws Exception {
        Main m = new Main();
        assertEquals(m.getFive(), 5);
    }

}
