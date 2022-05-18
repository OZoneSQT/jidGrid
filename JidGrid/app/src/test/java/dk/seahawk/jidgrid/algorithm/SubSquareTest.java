package dk.seahawk.jidgrid.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.seahawk.jidgrid.algorithm.gridcalc.SubSquare;

class SubSquareTest {
    private SubSquare subSquare;

    @BeforeEach
    void setUp() {
        subSquare = new SubSquare();
    }

    @Test
    void getSubSquareOutOfRange() {
        assertEquals("øj" , subSquare.getSubSquare(360, 9));
    }

    @Test
    void getSubSquareNegative() {
        assertEquals("øl" , subSquare.getSubSquare(-3, 11));
    }

    @Test
    void getSubSquareTrueA() {
        assertEquals("ib" , subSquare.getSubSquare(8, 1));
    }

    @Test
    void getSubSquareTrueB() {
        assertEquals("xo" , subSquare.getSubSquare(23, 14));
    }

    @Test
    void getSubSquareFalse() {
        assertNotEquals("ES" , subSquare.getSubSquare(4, 18));
    }

}