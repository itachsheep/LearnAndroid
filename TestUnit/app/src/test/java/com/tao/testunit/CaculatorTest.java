package com.tao.testunit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by SDT14324 on 2017/11/1.
 */
public class CaculatorTest {
    private Caculator mCaculator;

    @Before
    public void setUp() throws Exception {
        mCaculator = new Caculator();
    }

    @Test
    public void sum() throws Exception {
        assertEquals(3,mCaculator.sum(1,2),0);
    }

    @Test
    public void substract() throws Exception {
        assertEquals(1,mCaculator.substract(5,4),0);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(4,mCaculator.divide(20,5),0);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(10,mCaculator.multiply(2,5),0);
    }

}