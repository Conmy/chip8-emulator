package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVxPlusNNOperationTest {

    SetVxToVxPlusNNOperation op;

    @Before
    public void setup() {
        byte vxReg = 0x00;
        byte value = (byte) 0xf4;
        op = new SetVxToVxPlusNNOperation(vxReg, value);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsVxRegPlusNN() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[0] = 0x01;
        op.doOperation(chip8);

        Assert.assertEquals((byte) 0xf5, chip8.getVDataRegisters()[0]);
    }

    @Test
    public void doOperationAdditionWrapsProperlyWhenBitsOverflow() {

        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0xf] = 0;
        registers[0] = (byte) 0xf;

        op.doOperation(chip8);

        Assert.assertEquals(0x03, registers[0]);
    }

    @Test
    public void doOperationAdditionDoesNotChangeCarryFlag() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0xf] = 0;
        registers[0] = (byte) 0xff;

        op.doOperation(chip8);
        Assert.assertEquals(0, registers[0xf]);

        // Operation shouldn't reset it either.
        registers[0xf] = 0x01;
        registers[0] = (byte) 0xff;

        op.doOperation(chip8);
        Assert.assertEquals(0x01, registers[0x0f]);
    }
}
