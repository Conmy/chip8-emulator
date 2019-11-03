package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import me.conmy.emu.chip8.Chip8Display;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;

public class DrawSpriteOperationTest {

    private DrawSpriteOperation op;
    private byte vxReg = 0x04;
    private byte vyReg = 0x05;
    private int height = 4;

    @Before
    public void setUp() throws Exception {
        op = new DrawSpriteOperation(vxReg, vyReg, height);
    }

    @Test
    public void createdObjectImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationWritesASpriteToTheDisplay() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        int rowValue = 0x10;
        int startColumn = 0x0a;
        registers[vxReg] = (byte) (startColumn & 0x0ff);
        registers[vyReg] = (byte) (rowValue & 0x0ff);

        chip8.setIAddressRegister((char) 0x0000);
        byte[] memory = chip8.getMemory();
        memory[0] = 0x0f;
        memory[1] = 0x01;
        memory[2] = (byte) 0xf0;
        memory[3] = 0x07;

        boolean[][] expectedBooleanArraySprite = new boolean[][] {
            new boolean[] {false, false, false, false, true, true, true, true},
            new boolean[] {false, false, false, false, false, false, false, true},
            new boolean[] {true, true, true, true, false, false, false, false},
            new boolean[] {false, false, false, false, false, true, true, true}
        };

        op.doOperation(chip8);

        Chip8Display display = chip8.getScreenDisplay();
        for (int i=0; i< height; i++) {
            boolean[] displayRow = display.getDisplayRow(rowValue+i);
            boolean[] spriteRow = Arrays.copyOfRange(displayRow, startColumn, startColumn + 8);
            Assert.assertArrayEquals(expectedBooleanArraySprite[i], spriteRow);
        }
    }

    @Test
    public void doOperationIncreasesProgramCounterByTwoWhenCompleted() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }

    @Test
    public void doOperationSetsTheCollisionDetectionFlagIfADisplayBitIsFlippedFromOnToOff() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        int rowValue = 0x10;
        int startColumn = 0x0a;
        registers[vxReg] = (byte) (startColumn & 0x0ff);
        registers[vyReg] = (byte) (rowValue & 0x0ff);
        registers[0x0f] = (byte) 0x02;

        chip8.setIAddressRegister((char) 0x0000);
        byte[] memory = chip8.getMemory();
        memory[0] = (byte) 0xa0;
        memory[1] = (byte) 0xb0;
        memory[2] = (byte) 0xc0;
        memory[3] = (byte) 0xd0;

        // Set a bit that will be flipped
        Chip8Display display = chip8.getScreenDisplay();
        display.writeByte(startColumn, rowValue + 1, (byte) 0xff);

        // Notice expectedBooleanArraySprite[1] is XORed with the value that already occupied the row.
        boolean[][] expectedBooleanArraySprite = new boolean[][] {
                new boolean[] {true, false, true, false, false, false, false, false},
                new boolean[] {false, true, false, false, true, true, true, true},
                new boolean[] {true, true, false, false, false, false, false, false},
                new boolean[] {true, true, false, true, false, false, false, false}
        };

        op.doOperation(chip8);

        display = chip8.getScreenDisplay();
        for (int i=0; i< height; i++) {
            boolean[] displayRow = display.getDisplayRow(rowValue+i);
            boolean[] spriteRow = Arrays.copyOfRange(displayRow, startColumn, startColumn + 8);
            Assert.assertArrayEquals(expectedBooleanArraySprite[i], spriteRow);
        }

        Assert.assertEquals(0x01, chip8.getVDataRegisters()[0x0f]);
    }

    @Test
    public void doOperationUnSetsTheCollisionDetectionFlagIfNoDisplayBitsAreFlippedFromOnToOff() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        int rowValue = 0x10;
        int startColumn = 0x0a;
        registers[vxReg] = (byte) (startColumn & 0x0ff);
        registers[vyReg] = (byte) (rowValue & 0x0ff);
        registers[0x0f] = (byte) 0x02;

        chip8.setIAddressRegister((char) 0x0000);
        byte[] memory = chip8.getMemory();
        memory[0] = 0x0c;
        memory[1] = (byte) 0xa1;
        memory[2] = (byte) 0xff;
        memory[3] = 0x66;

        boolean[][] expectedBooleanArraySprite = new boolean[][] {
                new boolean[] {false, false, false, false, true, true, false, false},
                new boolean[] {true, false, true, false, false, false, false, true},
                new boolean[] {true, true, true, true, true, true, true, true},
                new boolean[] {false, true, true, false, false, true, true, false}
        };

        op.doOperation(chip8);

        Chip8Display display = chip8.getScreenDisplay();
        for (int i=0; i< height; i++) {
            boolean[] displayRow = display.getDisplayRow(rowValue+i);
            boolean[] spriteRow = Arrays.copyOfRange(displayRow, startColumn, startColumn + 8);
            Assert.assertArrayEquals(expectedBooleanArraySprite[i], spriteRow);
        }

        Assert.assertEquals(0x00, chip8.getVDataRegisters()[0x0f]);
    }
}
