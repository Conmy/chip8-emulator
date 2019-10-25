package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class VxEqualsVyOperationTest {

    @Test
    public void createsAnObjectThatImplementsOperation() {
        byte vxReg = 0x01;
        byte vyReg = 0x00;
        Operation op = new VxEqualsVyOperation(vxReg, vyReg);

        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSkipsTheNextInstructionIfVxEqualsVy() {
        byte vxReg = 0x00;
        byte vyReg = 0x01;

        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        byte[] registers = chip8.getVDataRegisters();
        registers[vxReg] = 0x12;
        registers[vyReg] = 0x12;

        Operation op = new VxEqualsVyOperation(vxReg, vyReg);
        op.doOperation(chip8);

        Assert.assertEquals(pc + 4, chip8.getProgramCounter());
    }

    @Test
    public void doOperationMovesToTheNextInstructionIfValueDoesNotEqualValueInVxRegister() {
        byte vxReg = 0x00;
        byte vyReg = 0x01;

        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        byte[] registers = chip8.getVDataRegisters();
        registers[vxReg] = (byte) 0xaf;
        registers[vyReg] = (byte) 0x01;

        VxEqualsVyOperation op = new VxEqualsVyOperation(vxReg, vyReg);
        op.doOperation(chip8);

        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }

    // TODO: Decide what to do with vxReg adn vyReg if value is over 0x0f.
    // This can't happen in normal execution since vxReg and vyReg are parsed from a single nibble

}
