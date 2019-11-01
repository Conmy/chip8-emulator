package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import me.conmy.emu.chip8.Chip8Display;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class ClearDisplayOperationTest {

    private ClearDisplayOperation op;

    @Before
    public void setUp() throws Exception {
        op = new ClearDisplayOperation();
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    /**
     * TODO: Could this be changed to just assert that Chip8Display.clearScreen was called?
     */
    public void doOperationClearsTheDisplayOnChip8() {
        // Setup
        Chip8 chip8 = new Chip8();
        Chip8Display display = chip8.getScreenDisplay();

        display.writeByte(0, 0, (byte) 0x7a);
        display.writeByte(16, 2, (byte) 0x0f);

        boolean[][] matrix = display.getBitMatrix();

        boolean hasBitsSet = false;
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[i].length; j++) {
                if (matrix[i][j]) {
                    hasBitsSet = true;
                }
            }
        }
        // Ensures that screen isn't clear.
        Assert.assertTrue(hasBitsSet);

        op.doOperation(chip8);

        boolean[][] clearedMatrix = display.getBitMatrix();

        hasBitsSet = false;
        for (int i=0; i<clearedMatrix.length; i++) {
            for (int j=0; j<clearedMatrix[i].length; j++) {
                if (clearedMatrix[i][j]) {
                    hasBitsSet = true;
                }
            }
        }
        Assert.assertFalse(hasBitsSet);

    }

    @Test
    public void doOperationIncreasesTheProgramCounterByTwoWhenCompleted() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
