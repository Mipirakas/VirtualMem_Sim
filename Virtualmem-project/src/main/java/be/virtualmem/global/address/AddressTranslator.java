package be.virtualmem.global.address;

import be.virtualmem.global.Constants;

import java.util.Arrays;

public class AddressTranslator {
    public static Integer fromAddressToPageTableLevelEntryId(IAddress address, int level) {
        Integer pageTableEntryId = null;
        if (level <= Constants.PAGE_TABLE_ENTRIES.length) {
            int pageTableSize = Constants.PAGE_TABLE_ENTRIES[level];
            int startIndex = Constants.ADDRESS_OFFSET_BITS;

            for (int i = level + 1; i < Constants.PAGE_TABLE_ENTRIES.length; i++)
                startIndex += Constants.PAGE_TABLE_ENTRIES[i];

            // Make custom bit class, problems with little endian and big endian for conversion
            /*BitSet set = address.getBitSet().get(startIndex, startIndex + pageTableSize);
            int offset = 0;
            for (int i = 0; i < pageTableSize; i++)
                if (set.get(i))
                    offset += (int) Math.pow(2, i);
            pageTableEntryId = offset;
             */
        }

        return pageTableEntryId;
    }

    public static boolean validateConstants() {
        // All bits used for page table, offset and unused bits should add up to size of bit addressable
        return Constants.BIT_ADDRESSABLE == Arrays.stream(Constants.PAGE_TABLE_ENTRIES).sum() + Constants.ADDRESS_OFFSET_BITS + Constants.ADDRESS_UNUSED_BITS;
    }
}
