package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class KeyPressEqualsVxOperationTest {

    private KeyPressEqualsVxOperation op;
    private byte vxReg = 0x04;

    @Before
    public void setUp() throws Exception {
        op = new KeyPressEqualsVxOperation(vxReg);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationJumpsToProgramCounterPlus4IfValueInVxRegIsInPressedKeys() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[vxReg] = 0x0c;
        chip8.setKeyPressed((byte) 0x0c);

        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pc + 4, chip8.getProgramCounter());
    }

    @Test
    public void doOperationJumpsToProgramCounterPlus2IfValueInVxRegIsNotInPressedKeys() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[vxReg] = 0x0c;
        chip8.clearPressedKeys(); // explicit clear of pressed keys

        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }

    @Test
    public void doOperationJumpsToProgramCounterPlus2IfValueInVxRegIsNotInPressedKeysEvenIfOtherValuesArePresent() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[vxReg] = 0x0c;
        chip8.setKeyPressed((byte) 0x0f);

        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
