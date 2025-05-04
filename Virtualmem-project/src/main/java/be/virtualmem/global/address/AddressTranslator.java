package be.virtualmem.global.address;

import be.virtualmem.global.Constants;

import java.util.Arrays;

public class AddressTranslator {
    public static Long fromAddressToPageTableLevelEntryId(IAddress address, int level) {
        Long pageTableEntryId = null;
        if (level >= 0 && level < Constants.PAGE_TABLE_ENTRIES.length) {
            final int pageTableSize = Constants.PAGE_TABLE_ENTRIES[level];
            int startIndex = Constants.ADDRESS_OFFSET_BITS;

            for (int i = level + 1; i < Constants.PAGE_TABLE_ENTRIES.length; i++)
                startIndex += Constants.PAGE_TABLE_ENTRIES[i];

            final int finalIndex = startIndex;
            pageTableEntryId = ((Address) address).getSubAddress(finalIndex, finalIndex + pageTableSize).getAsInteger(); // Maybe create custom method
        }

        return pageTableEntryId;
    }

    public static Long fromAddressGetPhysicalAddressOffset(IAddress address) {
        Long offset = null;
        if (address instanceof Address) {
            offset = ((Address) address).getSubAddress(0, Constants.ADDRESS_OFFSET_BITS).getAsInteger();
        }
        return offset;
    }

    public static boolean validateConstants() {
        // All bits used for page table, offset and unused bits should add up to size of bit addressable
        return Constants.BIT_ADDRESSABLE == Arrays.stream(Constants.PAGE_TABLE_ENTRIES).sum() + Constants.ADDRESS_OFFSET_BITS + Constants.ADDRESS_UNUSED_BITS;
    }
}
