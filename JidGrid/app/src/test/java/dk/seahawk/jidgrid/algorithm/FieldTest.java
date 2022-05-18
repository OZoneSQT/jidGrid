package dk.seahawk.jidgrid.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.seahawk.jidgrid.algorithm.gridcalc.Field;

class FieldTest {
    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field();
    }

    @Test
    void getFieldOutOfRange() {
        assertEquals("BØ" , field.getField(1, 999));
    }

    @Test
    void getFieldNegative() {
        assertEquals("ØK" , field.getField(-180, 10));
    }

    @Test
    void getFieldTrueA() {
        assertEquals("ID" , field.getField(8, 3));
    }

    @Test
    void getFieldTrueB() {
        assertEquals("HI" , field.getField(7, 8));
    }

    @Test
    void getFieldFalse() {
        assertNotEquals("no" , field.getField(13, 14));
    }

}