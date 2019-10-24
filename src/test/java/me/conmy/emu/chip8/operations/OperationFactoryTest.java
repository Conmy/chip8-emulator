package me.conmy.emu.chip8.operations;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class OperationFactoryTest {

    @Test
    public void createJumpOperationWithOpCode0x1NNN() {
        Operation op = OperationFactory.translateOpCodeToOperation((char)0x1bac);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
        op = OperationFactory.translateOpCodeToOperation((char) 0x1100);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
        op = OperationFactory.translateOpCodeToOperation((char) 0x11ff);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
        op = OperationFactory.translateOpCodeToOperation((char) 0x1f01);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
    }

    @Test
    public void aCreatedJumpOperationContainsJumpAddress() {
        JumpOperation op = (JumpOperation) OperationFactory.translateOpCodeToOperation((char) 0x1041);
        char address = op.getAddressNNN();

        Assert.assertEquals(0x041, address);
    }
}
