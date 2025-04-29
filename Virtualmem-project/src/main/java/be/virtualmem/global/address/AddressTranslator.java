package be.virtualmem.global.address;

import be.virtualmem.global.Constants;

import java.math.BigInteger;
import java.util.Arrays;

public class AddressTranslator {
    public static Integer fromAddressToPageTableLevelEntryId(IAddress address, int level) {
        Integer pageTableEntryId = null;
        if (Constants.PAGE_TABLE_ENTRIES.length < level) {
            int pageTableSize = Constants.PAGE_TABLE_ENTRIES[level];
            int startIndex = Arrays.stream(Constants.PAGE_TABLE_ENTRIES).limit(level).sum() + Constants.ADDRESS_UNUSED_BITS;

            pageTableEntryId = Address.fromBitSetToInteger(address.getBitSet().get(startIndex, startIndex + pageTableSize));
        }

        return pageTableEntryId;
    }

    public static boolean validateConstants() {
        // All bits used for page table, offset and unused bits should add up to size of bit addressable
        return Constants.BIT_ADDRESSABLE == Arrays.stream(Constants.PAGE_TABLE_ENTRIES).sum() + Constants.ADDRESS_OFFSET_BITS + Constants.ADDRESS_UNUSED_BITS;
    }
}
