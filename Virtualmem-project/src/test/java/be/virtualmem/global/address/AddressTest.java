package be.virtualmem.global.address;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    Address address46;
    Address address69;

    @BeforeEach
    void setUp() {
        address46 = new Address(Set.of(1, 2, 3, 5));
        address69 = new Address(Set.of(0, 2, 6));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fromHexToAddress() {
        String hex = "0x2e"; // 46 in hex

        Address address = Address.fromHexToAddress(hex);
        Assertions.assertEquals(address, address46);
    }

    @Test
    void offsetAddress() {
        int offset = 23; // 46 + 23 = 69

        Address address = Address.offsetAddress(address46, offset);
        Assertions.assertEquals(address, address69);
    }

    @Test
    void getBits() {
        Assertions.assertEquals(Set.of(1, 2, 3, 5), address46.getBits());
        Assertions.assertEquals(Set.of(0, 2, 6), address69.getBits());
    }

    @Test
    void getSubAddress() {
        Address address = new Address(Set.of(0, 2));

        Assertions.assertEquals(address, address46.getSubAddress(3, 6));
        Assertions.assertEquals(address, address69.getSubAddress(0, 3));
    }

    @Test
    void getAsInteger() {
        String hex = "0xa8caa161dd3b";

        Address address = Address.fromHexToAddress(hex);
        long addressDecimal = address.getAsInteger();
        Address address1 = Address.fromDecimalToAddress(addressDecimal);

        Assertions.assertEquals(address, address1);
    }

    @Test
    void fromBitStringToAddress() {
    }

    @Test
    void fromDecimalToAddress() {
    }
}