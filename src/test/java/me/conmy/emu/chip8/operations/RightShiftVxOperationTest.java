package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class RightShiftVxOperationTest {

    private RightShiftVxOperation op;

    @Before
    public void setUp() throws Exception {
        op = new RightShiftVxOperation((byte) 0x05);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationRightShiftsTheValueInVxReg() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x05] = 0x08;

        op.doOperation(chip8);
        Assert.assertEquals(0x04, registers[0x05]);
    }

    @Test
    public void doOperationSetsTheCarryFlagIfTheLeastSignificantBitOfVxIsSet() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x05] = 0x09;

        op.doOperation(chip8);
        Assert.assertEquals(0x04, registers[0x05]);
        Assert.assertEquals(0x01, registers[0x0f]);
    }

    @Test
    public void doOperationUnSetsTheCarryFlagIfTheLeastSignificantBitOfVxIsUnSet() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x05] = 0x0c;

        op.doOperation(chip8);
        Assert.assertEquals(0x06, registers[0x05]);
        Assert.assertEquals(0x00, registers[0x0f]);
    }

    @Test
    public void doOperationIncreasesProgramCounterOnCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);
        Assert.assertEquals(pc+2, chip8.getProgramCounter());
    }
}
