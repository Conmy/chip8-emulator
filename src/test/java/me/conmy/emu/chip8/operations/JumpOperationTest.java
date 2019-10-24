package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;

public class JumpOperationTest {

    private JumpOperation operation;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        operation = new JumpOperation((char) 0x100);
    }

    @Test
    public void jumpOperationCreates() {
        Assert.assertNotNull(operation);
    }

    @Test
    public void jumpOperationOnlyAcceptsUpToThreeByteAddressOnCreate() {
        expectedEx.expect(instanceOf(RuntimeException.class));
        new JumpOperation((char) 0xf000);
    }

    @Test
    public void jumpOperationImplementsOperation() {
        Assert.assertThat(operation, instanceOf(Operation.class));
    }

    @Test
    public void jumpOperationInitializesWithThreeByteAddress() {
        char address = operation.getAddressNNN();
        Assert.assertEquals(0x100, address);
        address = (char) (0x0fff & address);
        Assert.assertEquals(0x100, address);
    }

    @Test
    public void doOperationChangesProgramCounterToAddressLocation() {
        Chip8 chip8 = new Chip8();
        operation.doOperation(chip8);

        Assert.assertEquals(0x100, chip8.getProgramCounter());
    }
}
