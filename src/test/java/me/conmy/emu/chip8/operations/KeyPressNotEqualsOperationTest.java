package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;

public class KeyPressNotEqualsOperationTest {

    private KeyPressNotEqualsOperation op;
    byte vxReg = 0x05;

    @Before
    public void setUp() throws Exception {
        op = new KeyPressNotEqualsOperation(vxReg);
    }

    @Test
    public void createdObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationJumpsToProgramCounterPlus4IfValueInVxRegIsNotInPressedKeys() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[vxReg] = 0x01;

        int pc = chip8.getProgramCounter();

        Assert.assertThat(chip8.getPressedKeys(), not(contains((byte) 0x01)));
        op.doOperation(chip8);

        Assert.assertEquals(pc + 4, chip8.getProgramCounter());
    }

    @Test
    public void doOperationJumpsToProgramCounterPlus2IfValueInVxRegIsPresentInPressedKeys() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[vxReg] = 0x01;
        chip8.setKeyPressed((byte) 0x01);

        int pc = chip8.getProgramCounter();

        Assert.assertThat(chip8.getPressedKeys(), contains((byte) 0x01));
        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
