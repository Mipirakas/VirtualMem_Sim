package be.virtualmem.global.address;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTranslatorTest {
    IAddress address;

    @BeforeEach
    void setUp() {
        String hexAddress = "0xa8caa15ca000";
        address = Address.fromHexToAddress(hexAddress);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fromAddressToPageTableLevelEntryId() {
        // Hex to separate levels
        int levelZeroOffset = 337; // 101010001
        int levelOneOffset = 298; // 100101010
        int levelTwoOffset = 266; // 100001010
        int levelThreeOffset = 458; //111001010

        // Assertions
        Assertions.assertEquals(levelZeroOffset, AddressTranslator.fromAddressToPageTableLevelEntryId(address, 0));
        Assertions.assertEquals(levelOneOffset, AddressTranslator.fromAddressToPageTableLevelEntryId(address, 1));
        Assertions.assertEquals(levelTwoOffset, AddressTranslator.fromAddressToPageTableLevelEntryId(address, 2));
        Assertions.assertEquals(levelThreeOffset, AddressTranslator.fromAddressToPageTableLevelEntryId(address, 3));
    }

    @Test
    void validateConstants() {
    }
}