package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVxORVyOperationTest {

    SetVxToVxORVyOperation op;

    @Before
    public void setUp() throws Exception {
        byte vxReg = 0x01;
        byte vyReg = 0x04;
        op = new SetVxToVxORVyOperation(vxReg, vyReg);

    }

    @Test
    public void createdObjectsImplementOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationPerformsOROperationAndStoresItInVxReg() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x01] = (byte) 0xab;
        registers[0x04] = (byte) 0x0f;

        op.doOperation(chip8);
        Assert.assertEquals((byte) 0xaf, registers[0x01]);

    }

    @Test
    public void doOperationIncreasesProgramCounterOnCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);
        Assert.assertEquals(pc+2, chip8.getProgramCounter());
    }
}
