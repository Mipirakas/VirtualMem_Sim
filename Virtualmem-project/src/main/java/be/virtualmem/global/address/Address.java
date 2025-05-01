package be.virtualmem.global.address;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Address implements IAddress {
    private Set<Integer> bits;

    public Address(Set<Integer> bits) {
        this.bits = bits;
    }

    public Set<Integer> getBits() {
        return bits;
    }

    // Getters
    public Integer getAsInteger() {
        return bits.stream()
                .mapToInt(e -> (int) Math.pow(2, e))
                .sum();
    }


    // Conversions
    private static Set<Integer> fromBitStringToSet(String bits) {
        return IntStream.range(0, bits.length())
                .filter(e -> bits.charAt(bits.length() - 1 - e) == '1') // Count for LSB
                .boxed()
                .collect(Collectors.toSet());
    }

    public static Address fromHexToAddress(String hex) {
        // Filter string
        if (hex.contains("0x"))
            hex = hex.replace("0x", "");
        hex = hex.toUpperCase();

        // Conversion
        int decimal = Integer.parseInt(hex, 16);
        String binary = Integer.toBinaryString(decimal);
        return new Address(fromBitStringToSet(binary));
    }

    public static Address fromBitStringToAddress(String bits) {
        if (bits.contains("0b"))
            bits = bits.replace("0b", "");
        return new Address(fromBitStringToSet(bits));
    }

    public static Address fromDecimalToAddress(Integer decimal) {
        return Address.fromBitStringToAddress(Integer.toBinaryString(decimal));
    }

    public static Address offsetAddress(IAddress address, int offset) {
        return fromDecimalToAddress(address.getAsInteger() + offset);
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
        StringBuilder sb = new StringBuilder();
        sb.append("Binary: 0b");

        // Add binary value
        for (int i = 0; i < bits.stream().reduce(0, Integer::max); i++)
            sb.append(bits.contains(i) ? '1' : '0');

        sb.append(" [--] Decimal: ");
        sb.append(getAsInteger());

        return sb.toString();
    }
}
