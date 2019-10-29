package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;

public class SetDelayTimerToVxOperationTest {

    private SetDelayTimerToVxOperation op;

    @Before
    public void setUp() {
        op = new SetDelayTimerToVxOperation((byte) 0x04);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsTheDelayTimerToTheValueInVx() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[0x04] = (byte) 0x1f;
        chip8.setDelayTimer((byte) 0x00);

        op.doOperation(chip8);
        Assert.assertEquals((byte) 0x1f, chip8.getDelayTimer());
    }

    @Test
    public void doOperationIncreasesTheProgramCounterByTwoWhenCompleted() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
