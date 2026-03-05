package com.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testSqrt(){
        assertEquals(4.0, App.sqrt(16),0);
    }

    @Test
    public void testFactorial(){
        assertEquals(120, App.factorial(5));
    }

    @Test
    public void testLog(){
        assertEquals(2.3025, App.log(10),0.01);
    }

    @Test
    public void testPower(){
        assertEquals(8.0, App.power(2,3),0);
    }
}
