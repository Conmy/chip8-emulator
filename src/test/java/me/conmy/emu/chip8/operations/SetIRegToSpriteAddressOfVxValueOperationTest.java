package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;

public class SetIRegToSpriteAddressOfVxValueOperationTest {

    private SetIRegToSpriteAddressOfVxValueOperation op;

    @Before
    public void setUp() throws Exception {
        op = new SetIRegToSpriteAddressOfVxValueOperation((byte) 0x01);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsTheIRegAddressToTheLocationOfSpriteInformationForValueInVx() {
        Chip8 chip8 = new Chip8();

        for (byte nibble=0x0; nibble < 0x10; nibble++) {
            chip8.getVDataRegisters()[0x01] = nibble;

            op.doOperation(chip8);
            Assert.assertEquals("Incorrect address returned for font sprite: " + nibble, Chip8.getSpriteRegAddress(nibble), chip8.getIAddressRegister());
        }
    }

    @Test
    public void doOperationIncreasesProgramCounterByTwoWhenCompleted() {
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();

        op.doOperation(chip8);
        Assert.assertEquals(pc + 2, chip8.getProgramCounter());

    }
}
