package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;

public class JumpToV0PlusNNNOperationTest {

    private JumpToV0PlusNNNOperation op;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
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

    @Test
    public void doOperationThrowsRuntimeExceptionIfAddressExceedsMemorySize() {
        expectedException.expect(RuntimeException.class);

        Chip8 chip8 = new Chip8();
        Operation jOp = new JumpToV0PlusNNNOperation((char) 0x0fff);
        chip8.getVDataRegisters()[0x00] = (byte) 0xff;
        jOp.doOperation(chip8);

    }
}
