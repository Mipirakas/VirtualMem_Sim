package be.virtualmem.logic.process.memory.reallocation;

import java.util.LinkedHashSet;
import java.util.Set;

public class ReorderingSet<T> {
    private final LinkedHashSet<T> set = new LinkedHashSet<>();

    public void add(T value) {
        // Remove if it already exists, so insertion order is updated
        set.remove(value);
        set.add(value);
    }

    public boolean contains(T value) {
        return set.contains(value);
    }

    public T poll() {
        T first = set.getFirst();
        set.remove(first);
        return first;
    }

    public T peek() {
        if (set.isEmpty()) return null;
        return set.getFirst();
    }

    public boolean remove(T value) {
        return set.remove(value);
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public Set<T> toSet() {
        return set;
    }

    public String toString() {
        return set.toString();
    }
}