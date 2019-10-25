package me.conmy.emu.chip8.operations;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class OperationFactoryTest {

    @Test
    public void createJumpOperationWithOpCode0x1NNN() {
        Operation op = OperationFactory.decodeOpCodeToOperation((char)0x1bac);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
        op = OperationFactory.decodeOpCodeToOperation((char) 0x1100);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
        op = OperationFactory.decodeOpCodeToOperation((char) 0x11ff);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
        op = OperationFactory.decodeOpCodeToOperation((char) 0x1f01);
        Assert.assertThat(op, instanceOf(JumpOperation.class));
    }

    @Test
    public void createCallSubroutineOperationWithOpCode0x2NNN() {
        Operation op = OperationFactory.decodeOpCodeToOperation((char) 0x2123);
        Assert.assertThat(op, instanceOf(CallSubroutineOperation.class));
    }

    @Test
    public void createVxEqualsOperationWithCode0x3XNN() {
        Operation op = OperationFactory.decodeOpCodeToOperation((char) 0x3123);
        Assert.assertThat(op, instanceOf(VxEqualsOperation.class));
    }

    @Test
    public void aCreatedJumpOperationContainsJumpAddress() {
        JumpOperation op = (JumpOperation) OperationFactory.decodeOpCodeToOperation((char) 0x1041);
        char address = op.getAddressNNN();

        Assert.assertEquals(0x041, address);
    }

    @Test
    public void aCreatedCallSubroutineOperationContainsJumpAddress() {
        CallSubroutineOperation op = (CallSubroutineOperation) OperationFactory.decodeOpCodeToOperation((char) 0x2041);
        char address = op.getAddressNNN();

        Assert.assertEquals(0x041, address);
    }

    @Test
    public void aCreatedVxEqualsOperationContainsRegisterAddressAndValue() {
        VxEqualsOperation op = (VxEqualsOperation) OperationFactory.decodeOpCodeToOperation((char) 0x3123);
        byte register = op.getRegister();
        byte value = op.getValue();

        Assert.assertEquals(0x01, register);
        Assert.assertEquals(0x23, value);
    }

    // TODO: Add additional operations that are already implemented
}
