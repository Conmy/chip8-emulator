package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import me.conmy.emu.utils.Quadruple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;

public class StoreBCDVxInMemoryOperationTest {
	
	private StoreBCDVxInMemoryOperation op;
	private byte vxReg;
	
	@Before
	public void setUp() {
		vxReg = 0x01;
		op = new StoreBCDVxInMemoryOperation(vxReg);
	}
	
	@Test
	public void createsObjectThatImplementsOperation() {
		Assert.assertThat(op, instanceOf(Operation.class));		
	}
	
	@Test
	public void doOperationStoresBCDOfVxInMemoryAtLocationInIReg() {
		Chip8 chip8 = new Chip8();
		byte[] memory = chip8.getMemory();
		byte[] registers = chip8.getVDataRegisters();
		int iAddressReg = 0x00;
		chip8.setIAddressRegister((char) (iAddressReg & 0xffff));

		ArrayList<Quadruple<Integer, Byte, Byte, Byte>> testList = new ArrayList<>(
				Arrays.asList(
						new Quadruple<>(3, (byte)0x00, (byte)0x00, (byte)0x03),
						new Quadruple<>(23, (byte)0x00, (byte)0x02, (byte)0x03),
						new Quadruple<>(123, (byte)0x01, (byte)0x02, (byte)0x03))
		);

		for (Object testObj: testList) {
			Quadruple<Integer, Byte, Byte, Byte> testInOut = (Quadruple<Integer, Byte, Byte, Byte>) testObj;

			registers[vxReg] = (byte) (testInOut.getFirst() & 0x0ff);

			op.doOperation(chip8);

			Assert.assertEquals((byte) testInOut.getSecond(), memory[iAddressReg]);
			Assert.assertEquals((byte) testInOut.getThird(), memory[iAddressReg + 1]);
			Assert.assertEquals((byte) testInOut.getFourth(), memory[iAddressReg + 2]);
		}
	}
}
