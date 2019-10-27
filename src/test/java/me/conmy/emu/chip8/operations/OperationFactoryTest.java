package me.conmy.emu.chip8.operations;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class OperationFactoryTest {

    // --------------------------------------------------------------------------------------------
    // Checks that objects are created for the expected opCodes
    // --------------------------------------------------------------------------------------------

    /**
     * 0x1NNN: Jump Operation
     */
    @Test
    public void createJumpOperationWithOpCode0x1NNN() {
        expectClassInstanceGivenOpCode(JumpOperation.class, (char) 0x1023);
    }

    /**
     * 0x2NNN: Call Subroutine Operation
     */
    @Test
    public void createCallSubroutineOperationWithOpCode0x2NNN() {
        expectClassInstanceGivenOpCode(CallSubroutineOperation.class, (char) 0x2023);
    }

    /**
     * 0x3XNN: Condition Vx == NN
     */
    @Test
    public void createVxEqualsOperationWithCode0x3XNN() {
        expectClassInstanceGivenOpCode(VxEqualsOperation.class, (char) 0x311f);
    }

    /**
     * 0x4XNN: Condition Vx != NN
     */
    @Test
    public void createVxNotEqualsOperationWithCode0x4XNN() {
        expectClassInstanceGivenOpCode(VxNotEqualsOperation.class, (char) 0x41f0);
    }

    /**
     * 0x5XY0: Condition Vx == Vy
     */
    @Test
    public void createVxEqualsVyOperationWithCode0x5XY0() {
        expectClassInstanceGivenOpCode(VxEqualsVyOperation.class, (char) 0x5100);
    }

    /**
     * 0x6XNN: Set Vx Reg with NN
     */
    @Test
    public void createSetVxOperationWithCode0x6XNN() {
        expectClassInstanceGivenOpCode(SetVxToNNOperation.class, (char) 0x6120);
    }

    /**
     * 0x7XNN: Vx = Vx + NN
     */
    @Test
    public void createVxPlusNNOperationWithCode0x7XNN() {
        expectClassInstanceGivenOpCode(SetVxToVxPlusNNOperation.class, (char) 0x7412);
    }

    // --------------------------------------------------------------------------------------------
    // Checks that objects created were created with the correct data
    // --------------------------------------------------------------------------------------------

    @Test
    public void aCreatedJumpOperationWasCreatedWithTheCorrectJumpAddress() {
        JumpOperation op = (JumpOperation) OperationFactory.decodeOpCodeToOperation((char) 0x1041);
        char address = op.getAddress();

        Assert.assertEquals(0x041, address);
    }

    @Test
    public void aCreatedCallSubroutineOperationContainsJumpAddress() {
        CallSubroutineOperation op = (CallSubroutineOperation) OperationFactory.decodeOpCodeToOperation((char) 0x2041);
        char address = op.getAddress();

        Assert.assertEquals(0x041, address);
    }

    @Test
    public void aCreatedVxEqualsOperationContainsRegisterAddressAndValue() {
        VxEqualsOperation op = (VxEqualsOperation) OperationFactory.decodeOpCodeToOperation((char) 0x3123);
        byte register = op.getVxRegister();
        byte value = op.getValue();

        Assert.assertEquals(0x01, register);
        Assert.assertEquals(0x23, value);
    }

    @Test
    public void aCreatedVxNotEqualsOperationWasCreatedWithCorrectValues() {
        VxNotEqualsOperation op = (VxNotEqualsOperation) OperationFactory.decodeOpCodeToOperation((char) 0x4123);
        byte register = op.getVxRegister();
        byte value = op.getValue();

        Assert.assertEquals(0x01, register);
        Assert.assertEquals(0x23, value);
    }

    @Test
    public void aCreatedVxEqualsVyOperationWasCreatedWithCorrectValues() {
        VxEqualsVyOperation op = (VxEqualsVyOperation) OperationFactory.decodeOpCodeToOperation((char) 0x5123);
        byte vxReg = op.getVxRegister();
        byte vyReg = op.getVyRegister();

        Assert.assertEquals(0x1, vxReg);
        Assert.assertEquals(0x2, vyReg);
    }

    @Test
    public void aCreatedSetVxToNNOperationWasCreatedWithCorrectValues() {
        SetVxToNNOperation op = (SetVxToNNOperation) OperationFactory.decodeOpCodeToOperation((char) 0x6fed);
        byte vxReg = op.getVxReg();
        byte value = op.getValue();

        Assert.assertEquals(0x0f, vxReg);
        Assert.assertEquals((byte) 0xed, value);
    }

    // --------------------------------------------------------------------------------------------
    // Private methods
    // --------------------------------------------------------------------------------------------

    private void expectClassInstanceGivenOpCode(Class expectedClass, char opCode) {
        Operation op = OperationFactory.decodeOpCodeToOperation(opCode);
        Assert.assertThat(op, instanceOf(expectedClass));
    }
}
