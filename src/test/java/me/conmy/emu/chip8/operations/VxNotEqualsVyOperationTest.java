package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class VxNotEqualsVyOperationTest {

    private VxNotEqualsVyOperation op;

    @Before
    public void setUp() throws Exception {
        op = new VxNotEqualsVyOperation((byte) 0x00, (byte) 0x01);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSkipsTheNextInstructionIfVxNotEqualsVy() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = (byte) 0xaf;
        registers[0x01] = (byte) 0x0a;
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);
        Assert.assertEquals(pc + 4, chip8.getProgramCounter());
    }

    @Test
    public void doOperationMovesToTheNextInstructionIfVxEqualsVy() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = (byte) 0xa1;
        registers[0x01] = (byte) 0xa1;
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);
        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
