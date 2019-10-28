package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class JumpToV0PlusNNNOperationTest {

    private JumpToV0PlusNNNOperation op;

    @Before
    public void setUp() throws Exception {
        op = new JumpToV0PlusNNNOperation((char) 0x123);
    }

    @Test
    public void createdObjectImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSkipsToNewLocationOfV0PlusNNN() {
        Chip8 chip8 = new Chip8();
        byte[] registers = chip8.getVDataRegisters();
        registers[0] = 0x23;

        op.doOperation(chip8);
        Assert.assertEquals(0x146, chip8.getProgramCounter());
    }
}
