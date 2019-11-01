package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class LoadRegV0ToVxFromMemoryOperationTest {

    private LoadRegV0ToVxFromMemoryOperation op;
    byte vxReg = 0x04;

    @Before
    public void setUp() throws Exception {
        op = new LoadRegV0ToVxFromMemoryOperation(vxReg);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationStoresMemoryValuesInV0ToVxStartingAtIRegAddress() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        byte[] memory = chip8.getMemory();
        char startAddress = 0x0900;
        int startAddressValue = startAddress & 0x0ffff;
        chip8.setIAddressRegister(startAddress); // 2304

        byte[] expectedValues = new byte[] { 0x01, 0x04, 0x05, 0x08, 0x01};

        for (int i=0; i < expectedValues.length; i++) {
            memory[startAddressValue + i] = expectedValues[i];
        }

        op.doOperation(chip8);
        int vxRegInt = Byte.toUnsignedInt(vxReg);
        for (int i=0; i <= vxRegInt; i++) {
            byte expectedValue = expectedValues[i];
            byte regValue = registers[i];
            Assert.assertEquals(String.format("Unexpected value in memory at location %d for expectedValue[%d]", startAddress+i, i), expectedValue, regValue);
        }
    }

    @Test
    public void doOperationIncreasesTheProgramCounterByTwoOnCompletion() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pc+2, chip8.getProgramCounter());
    }
}
