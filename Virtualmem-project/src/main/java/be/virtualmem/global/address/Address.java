package be.virtualmem.global.address;

import java.math.BigInteger;
import java.util.BitSet;

public class Address implements IAddress {
    private BitSet bitSet;

    public Address(BitSet bitSet) {
        this.bitSet = bitSet;
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public static Integer fromBitSetToInteger(BitSet bitSet) {
        return new BigInteger(bitSet.toByteArray()).intValue();
    }

    public static Address fromHexToBitSet(String hex) {
        String hexString = hex.substring(2); // remove 0x
        long value = Long.parseUnsignedLong(hexString, 16);

        return new Address(BitSet.valueOf(new long[]{value}));
    }
}
