package me.conmy.emu.chip8.operations;

public class OperationFactory {

    public static Operation decodeOpCodeToOperation(char opCode) {
        char remainder = (char) (opCode & 0x0fff);
        byte nibble4 = (byte) ((opCode & 0xf000) >> 12);
        byte nibble3 = (byte) ((opCode & 0x0f00) >> 8);
        byte nibble2 = (byte) ((opCode & 0x00f0) >> 4);
        byte nibble1 = (byte) (opCode & 0x000f);
        byte byte2 = (byte) ((opCode & 0xff00) >> 8);
        byte byte1 = (byte) (opCode & 0x00ff);

        switch (nibble4) {
            case 0x1:
                return new JumpOperation(remainder);
            case 0x2:
                return new CallSubroutineOperation(remainder);
            case 0x3:
                return new VxEqualsOperation(nibble3, byte1);
            case 0x4:
                return new VxNotEqualsOperation(nibble3, byte1);
            case 0x5:
                return new VxEqualsVyOperation(nibble3, nibble2);
            case 0x6:
                return new SetVxToNNOperation(nibble3, byte1);
            case 0x7:
                return new SetVxToVxPlusNNOperation(nibble3, byte1);
        }
        return new JumpOperation((char) (opCode & 0x0fff));
    }
}
