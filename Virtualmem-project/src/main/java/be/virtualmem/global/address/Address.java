package be.virtualmem.global.address;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Address {
    private LinkedHashSet<Integer> bits;

    public Address(Set<Integer> bits) {
        this.bits = new LinkedHashSet<>(bits);
    }

    public Address(Address address) {
        this.bits = new LinkedHashSet<>(address.getBits());
    }

    public LinkedHashSet<Integer> getBits() {
        return bits;
    }

    // Getters
    public Long getAsInteger() {
        return bits.stream()
                .mapToLong(e -> 1L << e)
                .sum();
    }

    public String getAsHex() {
        return "0x" + Long.toHexString(getAsInteger()).toUpperCase();
    }

    public Address getSubAddress(int from, int to, boolean shift) {
        LinkedHashSet<Integer> set = bits.stream()
                .filter(e -> e >= from && e < to)
                .map(e -> e - (shift ? from : 0))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return new Address(set);
    }


    // Conversions
    private static LinkedHashSet<Integer> fromBitStringToSet(String bits) {
        return IntStream.range(0, bits.length())
                .filter(e -> bits.charAt(bits.length() - 1 - e) == '1') // Count for LSB
                .boxed()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static Address fromHexToAddress(String hex) {
        // Filter string
        hex = hex.replace("0x", "").toUpperCase();

        // Conversion
        long decimal = Long.parseLong(hex, 16);
        String binary = Long.toBinaryString(decimal);
        return new Address(fromBitStringToSet(binary));
    }

    public static Address fromBitStringToAddress(String bits) {
        bits = bits.replace("0b", "");
        return new Address(fromBitStringToSet(bits));
    }

    public static Address fromDecimalToAddress(Long decimal) {
        return Address.fromBitStringToAddress(Long.toBinaryString(decimal));
    }

    public static Address offsetAddress(Address address, int offset) {
        return fromDecimalToAddress(address.getAsInteger() + offset);
    }

    @Override
    public int hashCode() {
        return bits.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Address) {
            Address address = (Address) obj;
            return bits.equals(address.getBits());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Decimal: " + getAsInteger() + " [==] Hex: " + getAsHex();
     }
}
