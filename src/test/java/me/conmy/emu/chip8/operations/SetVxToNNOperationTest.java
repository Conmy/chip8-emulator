package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class SetVxToNNOperationTest {

    private byte vxReg;
    private byte value;
    private Operation op;

    @Before
    public void setup() {
        vxReg = 0x00;
        value = 0x01;
        op = new SetVxToNNOperation(vxReg, value);
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsTheValueOfVxRegisterToValue() {
        Chip8 chip8 = new Chip8();
        op.doOperation(chip8);

        Assert.assertEquals(value, chip8.getVDataRegisters()[vxReg]);
    }

    @Test
    public void doOperationIncreasesTheProgramCounterByTwo() {
        Chip8 chip8 = new Chip8();
        int pcBefore = chip8.getProgramCounter();

        op.doOperation(chip8);

        Assert.assertEquals(pcBefore + 2, chip8.getProgramCounter());
    }

    @Test
    public void creatingObjectStoresValueForVxRegAndValue() {
        SetVxToNNOperation setOp = (SetVxToNNOperation) op;

        Assert.assertEquals(value, setOp.getValue());
        Assert.assertEquals(vxReg, setOp.getVxReg());
    }
}
