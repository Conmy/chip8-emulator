package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;

public class SetIRegToIPlusVxOperationTest {

    private SetIRegToIPlusVxOperation op;

    @Before
    public void setUp() throws Exception {
        op = new SetIRegToIPlusVxOperation((byte) 0x01);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsTheIRegToIRegPlusVxValue() {
        Chip8 chip8 = new Chip8();
        chip8.setIAddressRegister((char) 0x0123);
        chip8.getVDataRegisters()[0x01] = 0x01;

        op.doOperation(chip8);
        Assert.assertEquals((char) 0x0124, chip8.getIAddressRegister());
    }

    @Test
    public void doOperationIncreasesProgramCounterByTwoWhenCompleted() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);
        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
