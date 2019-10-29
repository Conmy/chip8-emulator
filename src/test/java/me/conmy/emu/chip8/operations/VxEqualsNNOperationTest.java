package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class VxEqualsNNOperationTest {

    @Test
    public void objectImplementsOperation() {
        Operation op = new VxEqualsNNOperation((byte) 0x01, (byte) 0x12);

        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void createsObjectWithKnownRegisterAndValue() {
        VxEqualsNNOperation op = new VxEqualsNNOperation((byte) 0x01, (byte) 0x23);
        Assert.assertEquals(0x01, op.getVxRegister());
        Assert.assertEquals(0x23, op.getValue());
    }

    @Test
    public void doOperationSkipsNextInstructionIfValueEqualsValueInVxRegister() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[1] = 0x23;
        int pc = chip8.getProgramCounter();
        VxEqualsNNOperation op = new VxEqualsNNOperation((byte) 0x01, (byte) 0x23);
        op.doOperation(chip8);

        Assert.assertEquals(pc + 4, chip8.getProgramCounter());
    }

    @Test
    public void doOperationMovesToNextInstructionIfValueDoesNotEqualValueInVxRegister() {
        Chip8 chip8 = new Chip8();
        chip8.getVDataRegisters()[1] = 0x22;
        int pc = chip8.getProgramCounter();
        VxEqualsNNOperation op = new VxEqualsNNOperation((byte) 0x01, (byte) 0x23);
        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
