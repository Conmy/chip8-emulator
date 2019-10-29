package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToVxMinusVyOperationTest {

    private SetVxToVxMinusVyOperation op;

    @Before
    public void setUp() throws Exception {
        op = new SetVxToVxMinusVyOperation((byte) 0x00, (byte) 0x01);
    }

    @Test
    public void createsAnObjectThatImplmementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSubtractsVyFromVxAndStoresTheResultInVx() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = (byte) 0xff;
        registers[0x01] = 0x01;

        op.doOperation(chip8);
        Assert.assertEquals((byte)0xfe, chip8.getVDataRegisters()[0x00]);
    }

    @Test
    public void doOperationSetsTheCarryFlagIfTheSubtractionDidntUseABorrow() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = (byte) 0x0f;
        registers[0x01] = 0x01;

        op.doOperation(chip8);
        Assert.assertEquals((byte)0x0e, chip8.getVDataRegisters()[0x00]);
        Assert.assertEquals(0x01, chip8.getVDataRegisters()[0x0f]);
    }

    @Test
    public void doOperationUnSetsTheCarryFlagIfTheSubtractionUsesABorrow() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0x00] = (byte) 0x01;
        registers[0x01] = 0x0f;

        op.doOperation(chip8);
        Assert.assertEquals((byte)0xf2, chip8.getVDataRegisters()[0x00]);
        Assert.assertEquals(0x00, chip8.getVDataRegisters()[0x0f]);
    }

    @Test
    public void doOperationIncreasesProgramCounterOnCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        op.doOperation(chip8);
        Assert.assertEquals(pc+2, chip8.getProgramCounter());
    }
}
