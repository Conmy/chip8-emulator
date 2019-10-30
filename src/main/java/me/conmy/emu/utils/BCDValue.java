package me.conmy.emu.utils;

public class BCDValue {

    private int decimalValue;

    public BCDValue(int decimalValue) {
        this.decimalValue = decimalValue;
    }

    public BCDValue(char valueAsChar) {
        this.decimalValue = (valueAsChar & 0x0ffff);
    }

    public BCDValue(byte valueAsByte) {
        this.decimalValue = (valueAsByte & 0x0ff);
    }

    public int getDecimalValue() {
        return decimalValue;
    }

    public byte[] convertToByteArray(int significantDigits) {
        byte[] returnValues = new byte[significantDigits];
        byte[] fullNumber = convertToByteArray();

        int numberLength = fullNumber.length;
        if (numberLength > significantDigits) {
            // Cut returnValues
            for (int i=0; i<significantDigits; i++) {
                returnValues[significantDigits-i-1] = fullNumber[numberLength-i-1];
            }
        } else {
            // Pad returnValues
            for (int i=0; i<significantDigits; i++) {
                if (i >= numberLength) {
                    returnValues[significantDigits-i-1] = 0;
                } else {
                    returnValues[significantDigits-i-1] = fullNumber[numberLength-i-1];
                }
            }
        }
        return returnValues;
    }

    public byte[] convertToByteArray() {
        if (decimalValue < 0) {
            throw new IllegalArgumentException("BCDValue cannot convert negative numbers");
        }

        long num = decimalValue;

        int digits = 0;

        long temp = num;
        while (temp != 0) {
            digits++;
            temp /= 10;
        }

        byte[] bcd = new byte[digits];

        for (int i = 0; i < digits; i++) {
            byte tmp = (byte) (num % 10);
            bcd[i] = tmp;
            num = num / 10;
        }

        for (int i = 0; i < digits / 2; i++) {
            byte tmp = bcd[i];
            bcd[i] = bcd[digits - i - 1];
            bcd[digits - i - 1] = tmp;
        }

        return bcd;
    }
}
