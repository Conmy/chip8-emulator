package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;

public class CallSubroutineOperationTest {

    private CallSubroutineOperation op;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        op = new CallSubroutineOperation((char) 0x0f23);
    }
    @Test
    public void createObjectStoresTheAddress() {
        char address = op.getAddressNNN();
        Assert.assertEquals(0x0f23, address);
    }

    @Test
    public void doOperationJumpsToSubroutineLocation() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(0x0f23, chip8.getProgramCounter());
        Assert.assertEquals(pc, chip8.getStack().peek().charValue());
    }

    @Test
    public void constructorOnlyAcceptsUpToThreeByteAddressOnCreate() {
        expectedEx.expect(instanceOf(RuntimeException.class));
        new CallSubroutineOperation((char) 0xf000);
    }
}
