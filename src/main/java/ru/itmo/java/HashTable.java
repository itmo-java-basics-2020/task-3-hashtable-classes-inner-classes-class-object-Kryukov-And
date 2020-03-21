package ru.itmo.java;

import java.util.Map;

public class HashTable {
    private static final int INITIAL_CAPACITY = 100;
    private static final double INITIAL_LOADFACTOR = 0.5;

    private Entry[] array;
    private int size = 0;
    private double loadFactor;

    public HashTable() {
        this(INITIAL_CAPACITY, INITIAL_LOADFACTOR);
    }

    public HashTable(double loadFactor) {
        this(INITIAL_CAPACITY, INITIAL_LOADFACTOR);
    }

    public HashTable(int size, double loadFactor) {
        array = new Entry[size];
        this.loadFactor = loadFactor;
    }

    Object put(Object key, Object value) {
        int ind = getIndex(key);

        Entry old = array[ind];
        array[ind] = new Entry(key, value);

        if (checkNullOrDeleted(old)) {
            size++;
            resizeIfNeeded();
            return null;
        }

        return old.value;
    }

    Object get(Object key) {
        int ind = getIndex(key);

        if (checkNullOrDeleted(array[ind])) {
            return null;
        }
        return array[ind].value;
    }

    Object remove(Object key) {
        int ind = getIndex(key);

        if (checkNullOrDeleted(array[ind])) {
            return null;
        }

        array[ind].deleted = true;
        size--;
        return array[ind].value;
    }

    int size() {
        return size;
    }

    private boolean checkNullOrDeleted(Entry obj) {
        if (obj == null || obj.deleted) {
            return true;
        }
        return false;
    }

    private void resizeIfNeeded() {
        if (size < loadFactor * array.length) {
            return;
        }

        Entry[] newArray = array;
        array = new Entry[newArray.length * 2];

        for (int i = 0; i < newArray.length; i++) {
            if (!checkNullOrDeleted(newArray[i])) {
                array[getIndex(newArray[i].key)] = newArray[i];
            }
        }
    }

    private int getIndex(Object key) {
        int hc = Math.abs(key.hashCode() % array.length);

        while (array[hc] != null && !array[hc].key.equals(key)) {
            hc = (hc + 1) % array.length;
        }

        return hc;
    }


    private class Entry {
        private final Object key;
        private Object value;
        private boolean deleted;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }
}
