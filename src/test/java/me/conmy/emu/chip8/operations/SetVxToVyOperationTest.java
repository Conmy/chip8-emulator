package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVyOperationTest {

    private SetVxToVyOperation op;

    @Before
    public void setUp() throws Exception {
        op = new SetVxToVyOperation((byte) 0x00, (byte) 0x01);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsVxToTheValueInVy() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0] = (byte) 0x01;
        registers[1] = (byte) 0xab;

        op.doOperation(chip8);
        Assert.assertEquals((byte) 0xab, registers[0]);
    }

    @Test
    public void doOperationIncreasesProgramCounterOnCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);
        Assert.assertEquals(pc+2, chip8.getProgramCounter());
    }
}
