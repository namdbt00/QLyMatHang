package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

class TestTest {
    @BeforeAll
    public static void beforeAl1() {
        System.out.println("BEFORE ALL");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("AFTER ALL");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("BEFORE EACH");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("AFTER EACH");
    }

    @Test
    public void testNtChan() {
        int n = 2;
        boolean expected = true;
        boolean actual = test.Test.ktNt(n);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testNtLe() {
        int n = 5;
        boolean actual = test.Test.ktNt(n);

        Assertions.assertTrue(actual);
    }
}