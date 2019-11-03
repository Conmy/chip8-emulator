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
            case 0x0:
                if (byte1 == (byte) 0xee) {
                    return new ReturnFromSubroutineOperation();
                } else if (byte1 == (byte) 0xe0) {
                    return new ClearDisplayOperation();
                }
                break;
            case 0x1:
                return new JumpOperation(remainder);
            case 0x2:
                return new CallSubroutineOperation(remainder);
            case 0x3:
                return new VxEqualsNNOperation(nibble3, byte1);
            case 0x4:
                return new VxNotEqualsNNOperation(nibble3, byte1);
            case 0x5:
                return new VxEqualsVyOperation(nibble3, nibble2);
            case 0x6:
                return new SetVxToNNOperation(nibble3, byte1);
            case 0x7:
                return new SetVxToVxPlusNNOperation(nibble3, byte1);
            case 0x8:
                switch (nibble1) {
                    case 0x0:
                        return new SetVxToVyOperation(nibble3, nibble2);
                    case 0x01:
                        return new SetVxToVxORVyOperation(nibble3, nibble2);
                    case 0x02:
                        return new SetVxToVxANDVyOperation(nibble3, nibble2);
                    case 0x03:
                        return new SetVxToVxXORVyOperation(nibble3, nibble2);
                    case 0x04:
                        return new SetVxToVxPlusVyOperation(nibble3, nibble2);
                    case 0x05:
                        return new SetVxToVxMinusVyOperation(nibble3, nibble2);
                    case 0x06:
                        return new RightShiftVxOperation(nibble3);
                    case 0x07:
                        return new SetVxToVyMinusVxOperation(nibble3, nibble2);
                    case 0x0e:
                        return new LeftShiftVxOperation(nibble3);
                }
            case 0x9:
                return new VxNotEqualsVyOperation(nibble3, nibble2);
            case 0xa:
                return new SetIRegToNNNOperation(remainder);
            case 0xb:
                return new JumpToV0PlusNNNOperation(remainder);
            case 0xc:
                return new SetVxToRandANDNNOperation(nibble3, byte1);
            case 0xd:
                return new DrawSpriteOperation(nibble3, nibble2, Byte.toUnsignedInt(nibble1));
            case 0xe:
                if (byte1 == (byte) 0x9e) {
                    return new KeyPressEqualsVxOperation(nibble3);
                } else if (byte1 == (byte) 0xa1) {
                    return new KeyPressNotEqualsOperation(nibble3);
                }
                break;
            case 0xf:
                switch (byte1) {
                    case 0x07:
                        return new SetVxToDelayTimerOperation(nibble3);
                    case 0x15:
                        return new SetDelayTimerToVxOperation(nibble3);
                    case 0x18:
                        return new SetSoundTimerToVxOperation(nibble3);
                    case 0x1E:
                        return new SetIRegToIPlusVxOperation(nibble3);
                    case 0x29:
                        return new SetIRegToSpriteAddressOfVxValueOperation(nibble3);
                    case 0x33:
                        return new StoreBCDVxInMemoryOperation(nibble3);
                    case 0x55:
                        return new DumpRegV0ToVxInMemoryOperation(nibble3);
                    case 0x65:
                        return new LoadRegV0ToVxFromMemoryOperation(nibble3);
                }
        }
        throw new RuntimeException(String.format("Could not decode opCode passed to OperationFactory. Was: %04X", opCode & 0x0ffff));
    }
}
