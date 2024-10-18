package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setup(){
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Testing sum of two positive numbers")
    void TestSumPositive(){
        assertEquals(10, calculator.sum(5.5, 4.5));
    }

    @Test
    @DisplayName("Testing division by zero")
    void TestDivisionByZero(){
        Throwable exception = assertThrows(ArithmeticException.class, () -> calculator.div(2., 0.));
        assertEquals("Division by zero", exception.getMessage());
    }

    @Disabled
    @Test
    void multipleTest(){
        assertAll("hz",
                () -> assertEquals(2, calculator.sub(2, 0)),
                () -> assertEquals(2, calculator.sub(3, 2)));
    }

}
