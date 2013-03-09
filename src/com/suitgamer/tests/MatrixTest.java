package com.suitgamer.tests;
import org.junit.*;
import org.junit.Assert;
import com.suitgamer.tools.*;

/**
 * Created by IntelliJ IDEA.
 * User: Omid
 * Date: Dec 16, 2007
 * Time: 11:16:16 AM
 */
public class MatrixTest {


    @BeforeClass
    public static void setupTest() { }

    @Before
    public void setup() { }

    @Test
    public void values() {

        Matrix m = new Matrix(3, 5);
        m.addToCell(1, 1, 2);
        m.substractFromCell(3, 5, 6);
        m.addToCell(2, 2, 10);
        m.addToCell(2, 3, 1);
        m.setCell(3, 3, 17);
        m.setCell(3, 3, -5);

        Assert.assertEquals(m.getRowSum(2), 11);
        Assert.assertEquals(m.getColumnSum(3), -4);

    }

    @After
    public void cleanup() { }

    @AfterClass
    public static void cleanupTest() { }

}
