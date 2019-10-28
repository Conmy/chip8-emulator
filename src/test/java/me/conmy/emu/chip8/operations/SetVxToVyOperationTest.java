package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVyOperationTest {

    @Test
    public void createsAnObjectThatImplementsOperation() {
        SetVxToVyOperation op = new SetVxToVyOperation((byte) 0x00, (byte) 0x01);

        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsVxToTheValueInVy() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0] = (byte) 0x01;
        registers[1] = (byte) 0xab;

        SetVxToVyOperation op = new SetVxToVyOperation((byte) 0x00, (byte) 0x01);

        op.doOperation(chip8);

        Assert.assertEquals((byte) 0xab, registers[0]);
    }
}
