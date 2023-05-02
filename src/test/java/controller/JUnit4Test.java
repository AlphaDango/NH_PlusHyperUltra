package controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class JUnit4Test {

    @Test
    public void testTwo() {
        System.out.println("testTwo() method in TestJunitTwo");
        String str = "JunitTwo is working fine";
        assertEquals("JunitTwo is working fine", str);
    }
}
