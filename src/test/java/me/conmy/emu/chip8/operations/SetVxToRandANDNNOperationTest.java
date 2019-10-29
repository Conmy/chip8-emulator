package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class SetVxToRandANDNNOperationTest {

    private SetVxToRandANDNNOperation op;

    @Before
    public void setUp() throws Exception {
        byte vxReg = 0x01;
        byte maskNumber = (byte) 0x1f;
        op = new SetVxToRandANDNNOperation(vxReg, maskNumber);
    }

    @Test
    public void createsObjectsThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsVxToARandomNumberANDedWithNN() {
        Chip8 chip8 = new Chip8();

        for (int i=0; i < 5; i++) {
            chip8.getVDataRegisters()[0x01] = (byte) 0xff;
            op.doOperation(chip8);
            int value = Byte.toUnsignedInt(chip8.getVDataRegisters()[0x01]);
            Assert.assertThat(value, lessThanOrEqualTo(0x01f));
        }

        // Further testing for a different mask number.
        SetVxToRandANDNNOperation newOp = new SetVxToRandANDNNOperation((byte) 0x01, (byte) 0x07);
        for (int i=0; i < 5; i++) {
            chip8.getVDataRegisters()[0x01] = (byte) 0xff;
            newOp.doOperation(chip8);
            int value = Byte.toUnsignedInt(chip8.getVDataRegisters()[0x01]);
            Assert.assertThat(value, lessThanOrEqualTo(0x07));
        }
    }

    @Test
    public void doOperationIncreasedTheProgramCounterByTwo() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
