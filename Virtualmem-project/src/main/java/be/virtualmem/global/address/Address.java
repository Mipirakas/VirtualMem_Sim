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

    public static Address fromHexToAddress(String hex) {
        String hexString = hex.substring(2); // remove 0x
        long value = Long.parseUnsignedLong(hexString, 16);

        return new Address(BitSet.valueOf(new long[]{value}));
    }

    public static BigInteger fromBitSetToBigInteger(BitSet bitSet) {
        return new BigInteger(1, bitSet.toByteArray());
    }

    public static Address fromBigIntegerToAddress(BigInteger value) {
        return new Address(BitSet.valueOf(value.toByteArray()));
    }

    public static Address offsetAddress(IAddress address, int offset) {
        BigInteger addrValue = fromBitSetToBigInteger(address.getBitSet());
        BigInteger newValue = addrValue.add(BigInteger.valueOf(offset));
        return fromBigIntegerToAddress(newValue);
    }

}
