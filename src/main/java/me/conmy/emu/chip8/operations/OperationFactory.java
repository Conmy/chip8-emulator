package me.conmy.emu.chip8.operations;

public class OperationFactory {

    public static Operation decodeOpCodeToOperation(char opCode) {
        char filteredCode = (char) (opCode & 0xf000);
        char remainder = (char) (opCode & 0x0fff);
        switch (filteredCode) {
            case 0x1000:
                return new JumpOperation(remainder);
            case 0x2000:
                return new CallSubroutineOperation(remainder);
            case 0x3000:
                byte register = (byte) (remainder >> 8);
                byte value = (byte) (remainder & 0x00ff);
                return new VxEqualsOperation(register, value);
        }
        return new JumpOperation((char) (opCode & 0x0fff));
    }
}
