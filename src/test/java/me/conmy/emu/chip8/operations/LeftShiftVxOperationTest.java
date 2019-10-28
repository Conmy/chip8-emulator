package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class LeftShiftVxOperationTest {

    private LeftShiftVxOperation op;

    @Before
    public void setUp() throws Exception {
        op = new LeftShiftVxOperation((byte) 0x06);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationLeftShiftsTheValueInVxAndStoresTheResultInVx() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x06] = 0x06;

        op.doOperation(chip8);
        Assert.assertEquals(0x0c, registers[0x06]);
    }

    @Test
    public void doOperationStoresTheMostSignificantBitOfVxValueInRegisterVf() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x06] = 0x02;

        op.doOperation(chip8);
        Assert.assertEquals(0x00, registers[0x0f]);
        Assert.assertEquals(0x04, registers[0x06]);

        op.doOperation(chip8);
        Assert.assertEquals(0x00, registers[0x0f]);
        Assert.assertEquals(0x08, registers[0x06]);

        op.doOperation(chip8);
        Assert.assertEquals(0x00, registers[0x0f]);
        Assert.assertEquals(0x10, registers[0x06]);

        registers[0x06] = (byte) 0x80;
        op.doOperation(chip8);
        Assert.assertEquals(0x01, registers[0x0f]);
        Assert.assertEquals(0x00, registers[0x06]);
    }
}
