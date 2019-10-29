package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVxXORVyOperationTest {

    SetVxToVxXORVyOperation op;

    @Before
    public void setUp() throws Exception {
        byte vxReg = 0x0f;
        byte vyReg = 0x01;
        op = new SetVxToVxXORVyOperation(vxReg, vyReg);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationPerformsExclusiveOROnVxVyAndStoresTheResultInVx() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x0f] = (byte) 0xaf;
        registers[0x01] = (byte) 0xa0;

        op.doOperation(chip8);
        Assert.assertEquals(0x0f, registers[0x0f]);
    }

    @Test
    public void doOperationIncreasesProgramCounterOnCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);
        Assert.assertEquals(pc+2, chip8.getProgramCounter());
    }
}
