package dk.seahawk.jidgrid.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.seahawk.jidgrid.algorithm.gridcalc.Square;

class SquareTest {
    private Square square;

    @BeforeEach
    void setUp() {
        square = new Square();
    }

    @Test
    void getSquareOutOfRange() {
        assertEquals("Ø9" , square.getSquare(269, 9));
    }

    @Test
    void getSquareNegative() {
        assertEquals("3Ø" , square.getSquare(3, -5));
    }

    @Test
    void getSquareTrueA() {
        assertEquals("27" , square.getSquare(2, 7));
    }

    @Test
    void getSquareTrueB() {
        assertEquals("86" , square.getSquare(8, 6));
    }

    @Test
    void getSquareFalse() {
        assertNotEquals("63" , square.getSquare(3, 6));
    }

}