package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVxPlusVyOperationTest {

    SetVxToVxPlusVyOperation op;

    @Before
    public void setUp() throws Exception {
        op = new SetVxToVxPlusVyOperation((byte) 0x00, (byte) 0x01);
    }

    @Test
    public void createObjectIsOfTypeOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationAddsVxToVyAndStoresItInVx() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = 0x04;
        registers[0x01] = 0x06;

        op.doOperation(chip8);
        Assert.assertEquals(0x0a, registers[0x00]);
    }

    @Test
    public void doOperationSetsCarryFlagIfAdditionIsGreaterThanHexFF() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = (byte) 0xf1;
        registers[0x01] = 0x0f;

        op.doOperation(chip8);
        Assert.assertEquals(0x00, registers[0x00]);
        Assert.assertEquals(0x01, registers[0x0f]);
    }

    @Test
    public void doOperationUnSetsCarryFlagIfAdditionIsLessThanHexFF() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = (byte) 0x01;
        registers[0x01] = 0x0f;
        registers[0x0f] = 0x03; // Dummy set value - should be cleared in doOperation.

        op.doOperation(chip8);
        Assert.assertEquals(0x10, registers[0x00]);
        Assert.assertEquals(0x00, registers[0x0f]);
    }
}
