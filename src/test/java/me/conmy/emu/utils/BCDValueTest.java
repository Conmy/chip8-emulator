package me.conmy.emu.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;

public class BCDValueTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void convertToByteArrayConvertsDecimalValuesToBytes() {

        List<Integer> inDecimals = new ArrayList<Integer>(
                Arrays.asList(123456, 654321, 123456789, 123, 852741963)
        );

        List<Byte[]> outByteArrays = new ArrayList<Byte[]>();
        outByteArrays.add(new Byte[]{1,2,3,4,5,6});
        outByteArrays.add(new Byte[]{6,5,4,3,2,1});
        outByteArrays.add(new Byte[]{1,2,3,4,5,6,7,8,9});
        outByteArrays.add(new Byte[]{1,2,3});
        outByteArrays.add(new Byte[]{8,5,2,7,4,1,9,6,3});

        for (int i=0; i < inDecimals.size(); i++) {
            BCDValue bcd = new BCDValue(inDecimals.get(i));

            byte[] outArray = bcd.convertToByteArray();

            Assert.assertEquals("Length of outArray doesn't match expected", outByteArrays.get(i).length, outArray.length);
            Byte[] expectedArray = outByteArrays.get(i);
            for (int j=0; j < outArray.length; j++) {
                byte expectedValue = expectedArray[j].byteValue();
                byte actualValue = outArray[j];
                Assert.assertEquals("Value of outArray byte didn't match expected", expectedValue, actualValue);
            }
        }
    }

    @Test
    public void convertToByteArrayWhenPassedAnIntegerXWillReturnArrayToXSignificantDigits() {
        List<Integer> inDecimals = new ArrayList<Integer>(
                Arrays.asList(123456, 654321, 123456789, 123, 852741963)
        );

        List<Integer> significantDigits = new ArrayList<Integer>(
                Arrays.asList(3, 4, 5, 6, 7)
        );

        List<Byte[]> outByteArrays = new ArrayList<Byte[]>();
        outByteArrays.add(new Byte[]{4,5,6});
        outByteArrays.add(new Byte[]{4,3,2,1});
        outByteArrays.add(new Byte[]{5,6,7,8,9});
        outByteArrays.add(new Byte[]{0,0,0,1,2,3}); // Pads the number if too small
        outByteArrays.add(new Byte[]{2,7,4,1,9,6,3});

        for (int i=0; i < inDecimals.size(); i++) {
            BCDValue bcd = new BCDValue(inDecimals.get(i));

            int significantDigitsValue = significantDigits.get(i);
            byte[] outArray = bcd.convertToByteArray(significantDigitsValue);

            Assert.assertEquals("Length of outArray doesn't match expected", outByteArrays.get(i).length, outArray.length);
            Byte[] expectedArray = outByteArrays.get(i);
            for (int j=0; j < outArray.length; j++) {
                byte expectedValue = expectedArray[j].byteValue();
                byte actualValue = outArray[j];
                Assert.assertEquals("Value of outArray byte didn't match expected", expectedValue, actualValue);
            }
        }
    }

    @Test
    public void constructorStoresTheCorrectValueAsDecimalValue() {
        BCDValue value = new BCDValue(12);
        Assert.assertEquals(12, value.getDecimalValue());
        value = new BCDValue((char) 0x05);
        Assert.assertEquals(5, value.getDecimalValue());
        value = new BCDValue((byte) 0x012);
        Assert.assertEquals(18, value.getDecimalValue());
    }

    @Test
    public void convertToByteArrayThrowsExceptionIfBCDValueIsNegative() {
        expectedException.expect(IllegalArgumentException.class);

        BCDValue value = new BCDValue(-12);
        value.convertToByteArray();
    }
}
