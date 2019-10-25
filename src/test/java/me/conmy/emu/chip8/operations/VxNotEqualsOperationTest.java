package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class VxNotEqualsOperationTest {

    @Test
    public void objectImplementsOperation() {
        Operation op = new VxNotEqualsOperation((byte) 0x00, (byte) 0x12);
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSkipsTheNextInstructionIfValueDoesNotEqualValueInVxRegister() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[1] = 0x23;
        int pc = chip8.getProgramCounter();
        VxNotEqualsOperation op = new VxNotEqualsOperation((byte) 0x01, (byte) 0x01);
        op.doOperation(chip8);

        Assert.assertEquals(pc + 4, chip8.getProgramCounter());
    }

    @Test
    public void doOperationMovesToTheNextInstructionIfValueDoesNotEqualValueInVxRegister() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[1] = (byte) 0xaf;
        int pc = chip8.getProgramCounter();
        VxNotEqualsOperation op = new VxNotEqualsOperation((byte) 0x01, (byte) 0xaf);
        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
