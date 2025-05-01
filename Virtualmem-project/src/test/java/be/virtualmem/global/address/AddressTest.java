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
        System.out.println("fromHexToAddress");
        String hex = "0x2e"; // 46 in hex

        Address address = Address.fromHexToAddress(hex);
        Assertions.assertEquals(address, address46);
    }

    @Test
    void offsetAddress() {
        System.out.println("offsetAddress");
        int offset = 23; // 46 + 23 = 69

        Address address = Address.offsetAddress(address46, offset);
        Assertions.assertEquals(address, address69);
    }

    @Test
    void getBits() {
        System.out.println("getBits");

        Assertions.assertEquals(Set.of(1, 2, 3, 5), address46.getBits());
        Assertions.assertEquals(Set.of(0, 2, 6), address69.getBits());
    }

    @Test
    void getAsInteger() {
    }

    @Test
    void testFromHexToAddress() {
    }

    @Test
    void fromBitStringToAddress() {
    }

    @Test
    void fromDecimalToAddress() {
    }

    @Test
    void testOffsetAddress() {
    }
}