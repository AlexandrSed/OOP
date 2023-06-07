package seaBattle.model;

import org.junit.jupiter.api.Test;
import seaBattle.model.Field;
import seaBattle.model.FieldGenerator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс генератора поля
 */
class FieldGeneratorTest {

    @Test
    void testPlacementOfShipsOnField() {
        Field field = new Field(11, 11);
        FieldGenerator fieldGenerator = new FieldGenerator();

        fieldGenerator.placementOfShipsOnField(field);

        assertEquals(10, field.getShips().size());

    }

    @Test
    void testThereAlreadyShipsOnField() {
        Field field = new Field(11, 11);
        FieldGenerator fieldGenerator = new FieldGenerator();

        boolean isException = false;

        fieldGenerator.placementOfShipsOnField(field);

        try {
            fieldGenerator.placementOfShipsOnField(field);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: it is impossible to install the eleventh ship on the field", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }
}