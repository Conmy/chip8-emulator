package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;

public class SetSoundTimerToVxOperationTest {

    private SetSoundTimerToVxOperation op;

    @Before
    public void setUp() {
        op = new SetSoundTimerToVxOperation((byte)0x01);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsTheValueOfVxToTheSoundTimer() {
        Chip8 chip8 = new Chip8();
        chip8.setSoundTimer((byte) 0x00);
        chip8.getVDataRegisters()[0x01] = (byte) 0xa4;

        op.doOperation(chip8);

        Assert.assertEquals((byte) 0x0a4, chip8.getSoundTimer());

    }

    @Test
    public void doOperationIncreasesProgramCounterWhenCompleted() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);
        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
