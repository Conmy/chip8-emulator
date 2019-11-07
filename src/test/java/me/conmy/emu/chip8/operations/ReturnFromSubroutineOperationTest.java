package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.hamcrest.CoreMatchers.instanceOf;

public class ReturnFromSubroutineOperationTest {

    private ReturnFromSubroutineOperation op;

    @Before
    public void setUp() throws Exception {
        op = new ReturnFromSubroutineOperation();
    }

    @Test
    public void createsAnObjectThatImplementsOperation() {
        Assert.assertThat(op, instanceOf(Operation.class));
    }

    @Test
    public void doOperationSetsProgramCounterToTheLocationOnTopOfStackPlusTwo() {
        Chip8 chip8 = new Chip8();
        Stack<Character> stack = chip8.getStack();
        char stackChar = (char) 0x0f02; // 3842
        int stackCharValue = stackChar & 0x0ffff;
        stack.push(Character.valueOf(stackChar));

        op.doOperation(chip8);

        Assert.assertEquals(stackCharValue + 2, chip8.getProgramCounter());
    }
}
