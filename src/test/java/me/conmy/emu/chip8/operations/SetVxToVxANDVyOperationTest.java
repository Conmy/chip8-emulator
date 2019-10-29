package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVxANDVyOperationTest {

    SetVxToVxANDVyOperation op;
    @Before
    public void setUp() throws Exception {
        byte vxReg = 0x00;
        byte vyReg = 0x01;
        op = new SetVxToVxANDVyOperation(vxReg, vyReg);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationDoesVxANDVyAndStoresInVx() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = 0x0f;
        registers[0x01] = 0x04;

        op.doOperation(chip8);
        Assert.assertEquals(0x04, registers[0x00]);
    }

    @Test
    public void doOperationIncreasesProgramCounterOnCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);
        Assert.assertEquals(pc+2, chip8.getProgramCounter());
    }
}
