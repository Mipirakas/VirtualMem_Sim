package be.virtualmem.global.address;

import java.util.LinkedHashSet;

public interface IAddress {
    Long getAsInteger();
    LinkedHashSet<Integer> getBits();
}
