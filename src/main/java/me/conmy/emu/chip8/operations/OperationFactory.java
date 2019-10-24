package me.conmy.emu.chip8.operations;

public class OperationFactory {

    public static Operation translateOpCodeToOperation(char opCode) {
        return new JumpOperation((char) (opCode & 0x0fff));
    }
}
