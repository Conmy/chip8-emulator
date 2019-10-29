package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVyMinusVxOperationTest {

    private SetVxToVyMinusVxOperation op;

    @Before
    public void setUp() throws Exception {
        op = new SetVxToVyMinusVxOperation((byte) 0x01, (byte) 0x02);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsVxToTheValueOfVyMinusTheValueOfVx() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x01] = 0x05;
        registers[0x02] = 0x09;

        op.doOperation(chip8);
        Assert.assertEquals(0x04, registers[0x01]);
    }

    @Test
    public void doOperationSetsTheVfRegisterIfBorrowIsNotNeeded() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x01] = 0x05;
        registers[0x02] = 0x09;

        op.doOperation(chip8);
        Assert.assertEquals(0x04, registers[0x01]);
        Assert.assertEquals(0x01, registers[0x0f]);
    }

    @Test
    public void doOperationUnSetsTheVfRegisterIfBorrowIsNeeded() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x01] = 0x09;
        registers[0x02] = 0x05;

        op.doOperation(chip8);
        Assert.assertEquals((byte) 0xfc, registers[0x01]);
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
