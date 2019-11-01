package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class DumpRegV0ToVxInMemoryOperationTest {

    private DumpRegV0ToVxInMemoryOperation op;

    @Before
    public void setUp() throws Exception {
        byte vxReg = 0x02;
        op = new DumpRegV0ToVxInMemoryOperation(vxReg);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationStoresTheValuesOfV0ToVxInMemoryStartingAtIRegAddress() {
        Chip8 chip8 = new Chip8();
        chip8.setIAddressRegister((char) 0xf00); // 3840
        byte[] registers = chip8.getVDataRegisters();
        byte val1 = 0x01;
        byte val2 = 0x02;
        byte val3 = 0x03;
        registers[0] = val1;
        registers[1] = val2;
        registers[2] = val3;

        op.doOperation(chip8);

        byte[] memory = chip8.getMemory();
        Assert.assertEquals(val1, memory[0xf00]);
        Assert.assertEquals(val2, memory[0xf01]);
        Assert.assertEquals(val3, memory[0xf02]);
    }

    @Test
    public void doOperationDoesNotAlterTheValueInIReg() {
        Chip8 chip8 = new Chip8();
        chip8.setIAddressRegister((char) 0x123);

        op.doOperation(chip8);

        Assert.assertEquals((char)0x123, chip8.getIAddressRegister());
    }

    @Test
    public void doOperationIncreasesTheProgramCounterByTwoAfterCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
