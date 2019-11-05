package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class GetKeyPressOperationTest {

    private GetKeyPressOperation op;
    private byte vxReg = 0x01;

    @Before
    public void setUp() throws Exception {
        op = new GetKeyPressOperation(vxReg);
    }

    @Test
    public void createsObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationWaitsForAKeyPressAndStoresItInVx() {
        // This is a blocking operation
        // Set up event listener
        Chip8 chip8 = new Chip8();
        int pc = chip8.getProgramCounter();
        for (int i=0; i < 40; i++) {
            op.doOperation(chip8);
            Assert.assertEquals((byte) 0x00, chip8.getVDataRegisters()[vxReg]);
            Assert.assertEquals(pc, chip8.getProgramCounter());
        }
        chip8.setKeyPressed((byte) 0x0c);
        op.doOperation(chip8);
        Assert.assertEquals((byte) 0x0c, chip8.getVDataRegisters()[vxReg] );
        Assert.assertEquals(pc + 2, chip8.getProgramCounter());
    }
}
