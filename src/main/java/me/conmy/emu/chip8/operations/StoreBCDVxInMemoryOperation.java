package me.conmy.emu.chip8.operations;

import me.conmy.emu.chip8.Chip8;
import me.conmy.emu.utils.BCDValue;

public class StoreBCDVxInMemoryOperation implements Operation {

	private byte vxReg;
	
	public StoreBCDVxInMemoryOperation(byte vxReg) {
		setVxReg(vxReg);
	}

	@Override
	public void doOperation(Chip8 chip8) {
		byte vxValue = chip8.getVDataRegisters()[getVxReg()];
		BCDValue bcdValue = new BCDValue(vxValue);
		byte[] storeValues = bcdValue.convertToByteArray(3); // Always returns an array of length 3

		char address = chip8.getIAddressRegister();
		int addressLoc = (address & 0x0ffff);
		byte[] memory = chip8.getMemory();

		memory[addressLoc] = storeValues[0];
		memory[addressLoc + 1] = storeValues[1];
		memory[addressLoc + 2] = storeValues[2];

		chip8.incProgramCounter(2);
	}

	public byte getVxReg() {
		return vxReg;
	}

	private void setVxReg(byte vxReg) {
		this.vxReg = vxReg;
	}

	public String toString() {
		return String.format("SET: I = BCD(V[%d])", getVxReg());
	}
}
