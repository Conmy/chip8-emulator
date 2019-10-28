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

    /**
     * 0x8XY0: Vx = Vy
     */
    @Test
    public void createSetVxToVyOperationWithCode0x8XY0() {
        expectClassInstanceGivenOpCode(SetVxToVyOperation.class, (char) 0x8120);
    }

    /**
     * 0x8XY1: Vx = Vx | Vy
     */
    @Test
    public void createSetVxToVxORVyOperationWithCode0x8XY1() {
        expectClassInstanceGivenOpCode(SetVxToVxORVyOperation.class, (char) 0x8141);
    }

    /**
     * 0x8XY2: Vx = Vx & Vy
     */
    @Test
    public void createSetVxToVxANDVyOperationWithCode0x8XY2() {
        expectClassInstanceGivenOpCode(SetVxToVxANDVyOperation.class, (char) 0x85a2);
    }

    /**
     * 0x8XY3: Vx = Vx ^ Vy
     */
    @Test
    public void createSetVxToVxXORVyOperationWithCode0x8XY3() {
        expectClassInstanceGivenOpCode(SetVxToVxXORVyOperation.class, (char) 0x8ba3);
    }

    /**
     * 0x8XY4: Vx = Vx + Vy
     */
    @Test
    public void createSetVxToVxPlusVyOperationWithCode0x8XY4() {
        expectClassInstanceGivenOpCode(SetVxToVxPlusVyOperation.class, (char) 0x8ec4);
    }

    /**
     * 0x8XY5: Vx = Vx - Vy
     */
    @Test
    public void createSetVxToVxMinusVyOperationWithCode0x8XY5() {
        expectClassInstanceGivenOpCode(SetVxToVxMinusVyOperation.class, (char) 0x81b5);
    }

    /**
     * 0x8XY6: Vx = Vx << 1
     */
    @Test
    public void createRightShiftVxOperationWithCode0x8XY6() {
        expectClassInstanceGivenOpCode(RightShiftVxOperation.class, (char) 0x8326);
    }

    /**
     * 0x8XY7: Vx = Vy - Vx
     */
    @Test
    public void createSetVxToVyMinusVxOperationWithCode0x8XY7() {
        expectClassInstanceGivenOpCode(SetVxToVyMinusVxOperation.class, (char) 0x8127);
    }

    @Test
    public void createLeftShiftVxOperationWithCode0x8XYE() {
        expectClassInstanceGivenOpCode(LeftShiftVxOperation.class, (char) 0x8e1e);
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

    @Test
    public void aCreatedSetVxToVyOperationWasCreatedWithCorrectValues() {
        SetVxToVyOperation op = (SetVxToVyOperation) OperationFactory.decodeOpCodeToOperation((char) 0x8030);
        byte vxReg = op.getVxReg();
        byte vyReg = op.getVyReg();

        Assert.assertEquals((byte) 0x00, vxReg);
        Assert.assertEquals((byte) 0x03, vyReg);
    }

    @Test
    public void aCreatedSetVxToVxPlusNNOperationWasCreatedWithTheCorrectValues() {
        SetVxToVxPlusNNOperation op = (SetVxToVxPlusNNOperation) OperationFactory.decodeOpCodeToOperation((char) 0x7123);
        Assert.assertEquals(0x01, op.getVxReg());
        Assert.assertEquals(0x023, op.getValue());
    }

    // TODO: Assert all operations are created with correct parsed values from opcode.

    // --------------------------------------------------------------------------------------------
    // Private methods
    // --------------------------------------------------------------------------------------------

    private void expectClassInstanceGivenOpCode(Class expectedClass, char opCode) {
        Operation op = OperationFactory.decodeOpCodeToOperation(opCode);
        Assert.assertThat(op, instanceOf(expectedClass));
    }
}
