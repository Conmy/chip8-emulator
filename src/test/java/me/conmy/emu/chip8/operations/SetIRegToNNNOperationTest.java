package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetIRegToNNNOperationTest {

    private SetIRegToNNNOperation op;

    @Before
    public void setUp() throws Exception {
        op = new SetIRegToNNNOperation((char) 0x0123);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsIAddressRegisterToNNN() {
        Chip8 chip8 = new Chip8();
        op.doOperation(chip8);

        Assert.assertEquals(0x123, chip8.getIAddressRegister());
    }

    @Test
    public void doOperationMovesProgramCounterToTheNextOperation() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
