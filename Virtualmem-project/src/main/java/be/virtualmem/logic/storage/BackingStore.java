package be.virtualmem.logic.storage;

import be.virtualmem.global.address.Address;
import be.virtualmem.logic.process.memory.Page;

import java.util.HashMap;
import java.util.Map;

public class BackingStore {
    private final static BackingStore instance = new BackingStore();
    Map<Integer, Map<Address, Page>> backingStores;

    private BackingStore() {
        backingStores = new HashMap<>();
    }

    public static BackingStore getInstance() {
        return instance;
    }

    public void addPage(Integer pid, Address address, Page page) {
        backingStores.computeIfAbsent(pid, k -> new HashMap<>());
        backingStores.get(pid).put(address, page);
    }

    public void removePage(Integer pid, Address address) {
        if (backingStores.containsKey(pid))
            backingStores.get(pid).remove(address);
    }

    public Page getPage(Integer pid, Address address) {
        if (!backingStores.containsKey(pid))
            return null;
        return backingStores.get(pid).get(address);
    }

    public Map<Integer, Map<Address, Page>> getBackingStores() {
        return backingStores;
    }

}
