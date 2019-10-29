package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;

public class SetVxToDelayTimerOperationTest {

    private SetVxToDelayTimerOperation op;

    @Before
    public void setUp() {
        op = new SetVxToDelayTimerOperation((byte) 0x01);
    }

    @Test
    public void createdObjectImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsTheDelayTimerValueToVx() {
        Chip8 chip8 = new Chip8();
        chip8.setDelayTimer((byte) 0x1f);
        byte[] registers = chip8.getVDataRegisters();
        registers[0x01] = 0x00;

        op.doOperation(chip8);
        Assert.assertEquals((byte) 0x1f, chip8.getDelayTimer());
    }

    @Test
    public void doOperationIncreasesProgramCounterAfterCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);
        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
