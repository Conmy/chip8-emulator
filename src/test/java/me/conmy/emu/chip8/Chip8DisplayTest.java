package me.conmy.emu.chip8;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

public class Chip8DisplayTest {

    private Chip8Display display;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        display = new Chip8Display();
    }

    @Test
    public void displayIsClearWhenCreated() {
        boolean[][] bitMatrix = display.getBitMatrix();
        for (boolean[] bits : bitMatrix) {
            for (boolean bit : bits) {
                Assert.assertFalse(bit);
            }
        }
    }

    @Test
    public void writeAByteToTheDisplayAtXYCoordinates() {
        int x = 0;
        int y = 0;
        byte displayByte = 0x17; // 0001 0111

        display.writeByte(x,y, displayByte);

        boolean[] displayRow = display.getDisplayRow(y);
        Assert.assertFalse(displayRow[0]);
        Assert.assertFalse(displayRow[1]);
        Assert.assertFalse(displayRow[2]);
        Assert.assertTrue(displayRow[3]);

        Assert.assertFalse(displayRow[4]);
        Assert.assertTrue(displayRow[5]);
        Assert.assertTrue(displayRow[6]);
        Assert.assertTrue(displayRow[7]);
    }

    @Test
    public void writeByteThrowsExceptionIfInputResultsInInvalidScreenPosition() {

        expectedException.expect(IllegalArgumentException.class);
        display.writeByte(64, 0, (byte) 0x01);
        expectedException.expect(IllegalArgumentException.class);
        display.writeByte(60, 0, (byte) 0x00);
    }

    @Test
    public void writeSpriteToTheDisplayStartingAtXYCoordinates() {

        int x = 0;
        int y = 0;
        byte[] sprite = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        boolean[][] expecteds = new boolean[][] {
                new boolean[] {false, false, false, false, false, false, false, true},
                new boolean[] {false, false, false, false, false, false, true, false},
                new boolean[] {false, false, false, false, false, false, true, true},
                new boolean[] {false, false, false, false, false, true, false, false},
                new boolean[] {false, false, false, false, false, true, false, true},
                new boolean[] {false, false, false, false, false, true, true, false},
                new boolean[] {false, false, false, false, false, true, true, true}
        };

        // Ensure clearing of the isCollisionDetected before-hand
        display.clearCollisionDetected();
        display.writeSprite(x, y, sprite);

        for (int i=0; i < sprite.length; i++) {
            boolean[] subDisplayRow = Arrays.copyOfRange(display.getDisplayRow(y+i), x, x+8);
            Assert.assertArrayEquals(expecteds[i], subDisplayRow);
        }
    }

    @Test
    public void ifWriteSpriteDoesntFlipBitsOnTheDisplayThenIsCollisionDetectedIsFalse() {
        int x = 0;
        int y = 0;
        byte[] sprite = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        boolean[][] expecteds = new boolean[][] {
                new boolean[] {false, false, false, false, false, false, false, true},
                new boolean[] {false, false, false, false, false, false, true, false},
                new boolean[] {false, false, false, false, false, false, true, true},
                new boolean[] {false, false, false, false, false, true, false, false},
                new boolean[] {false, false, false, false, false, true, false, true},
                new boolean[] {false, false, false, false, false, true, true, false},
                new boolean[] {false, false, false, false, false, true, true, true}
        };

        // Ensure clearing of the isCollisionDetected before-hand
        display.clearCollisionDetected();

        display.writeSprite(x, y, sprite);

        Assert.assertFalse(display.isCollisionDetected());
    }

    @Test
    public void ifWriteSpriteFlipsBitsOnTheDisplayThenCollisionDetectedIsTrue() {
        int x = 0;
        int y = 0;
        display.writeByte(x, y, (byte) 0xff);

        byte[] sprite = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        boolean[][] expecteds = new boolean[][] {
                new boolean[] {false, false, false, false, false, false, false, true},
                new boolean[] {false, false, false, false, false, false, true, false},
                new boolean[] {false, false, false, false, false, false, true, true},
                new boolean[] {false, false, false, false, false, true, false, false},
                new boolean[] {false, false, false, false, false, true, false, true},
                new boolean[] {false, false, false, false, false, true, true, false},
                new boolean[] {false, false, false, false, false, true, true, true}
        };

        // Ensure clearing of the isCollisionDetected before-hand
        display.clearCollisionDetected();
        display.writeSprite(x, y, sprite);

        Assert.assertTrue(display.isCollisionDetected());
    }

    @Test
    public void writeSpritePositionOfWritingTheSpriteCanBeDeterminedByXYCoordinates() {
        int x = 20;
        int y = 4;
        // Ensure clearing of the isCollisionDetected before-hand
        display.clearCollisionDetected();

        byte[] sprite = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        boolean[][] expecteds = new boolean[][] {
                new boolean[] {false, false, false, false, false, false, false, true},
                new boolean[] {false, false, false, false, false, false, true, false},
                new boolean[] {false, false, false, false, false, false, true, true},
                new boolean[] {false, false, false, false, false, true, false, false},
                new boolean[] {false, false, false, false, false, true, false, true},
                new boolean[] {false, false, false, false, false, true, true, false},
                new boolean[] {false, false, false, false, false, true, true, true}
        };

        display.writeSprite(x, y, sprite);

        for (int i=0; i < sprite.length; i++) {
            boolean[] subDisplayRow = Arrays.copyOfRange(display.getDisplayRow(y+i), x, x+8);
            Assert.assertArrayEquals(expecteds[i], subDisplayRow);
        }
    }

    @Test
    public void writeByteWillWriteTheByteToDisplayButKeepOtherBitsOnTheRowUntouched() {
        int x = 0;
        int y = 0;
        byte display7CByte = 0x7c; // 0111 1100
        boolean[] expected7CBooleanValues =
                new boolean[] {false, true, true, true, true, true, false, false};

        display.writeByte(x,y, display7CByte);

        boolean[] displayRow = display.getDisplayRow(y);
        for (int i=0; i < 8; i++) {
            Assert.assertEquals(expected7CBooleanValues[i], displayRow[i]);
        }
        for (int i=8; i < 64; i++) {
            Assert.assertFalse(displayRow[i]);
        }

        byte display1FByte = 0x1f;
        boolean[] expected1FBooleanValues =
                new boolean[] {false, false, false, true, true, true, true, true};
        display.writeByte(x+8,y,display1FByte);
        displayRow = display.getDisplayRow(y);
        for (int i=0; i < 8; i++) {
            Assert.assertEquals(expected7CBooleanValues[i], displayRow[i]);
        }
        for (int i=8; i < 16; i++) {
            Assert.assertEquals(expected1FBooleanValues[i-8], displayRow[i]);
        }
        for (int i=16; i < 64; i++) {
            Assert.assertFalse(displayRow[i]);
        }
    }

    @Test
    public void clearScreenTurnsOffAllPixelsOnTheDisplay() {
        int x=0;
        int y=0;
        byte noise = (byte) 0x0ff;
        boolean[] expectedFFBooleanValues =
                new boolean[] {true, true, true, true, true, true, true, true};

        display.writeByte(x, y, noise);
        // Confirm that the display has noise on it.
        boolean[] displayRow = display.getDisplayRow(y);
        for (int i=0; i < 8; i++) {
            Assert.assertEquals(expectedFFBooleanValues[i], displayRow[i]);
        }
        for (int i=8; i < 64; i++) {
            Assert.assertFalse(displayRow[i]);
        }

        display.clearScreen();

        for (boolean[] row: display.getBitMatrix()) {
            for(boolean bit: row) {
                Assert.assertFalse(bit);
            }
        }
    }
}
