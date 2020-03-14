package ru.itmo.java;

import java.util.Map;

public class HashTable {
    private static final int INITIAL_CAPACITY = 100;
    private static final double INITIAL_LOADFACTOR = 0.5;

    private Entry[] array;
    private int size = 0;
    private double loadFactor;

    public HashTable() {
        array = new Entry[INITIAL_CAPACITY];
        this.loadFactor = INITIAL_LOADFACTOR;
    }

    public HashTable(int size) {
        array = new Entry[INITIAL_CAPACITY];
        this.loadFactor = INITIAL_LOADFACTOR;
    }

    public HashTable(double loadFactor) {
        array = new Entry[INITIAL_CAPACITY];
        this.loadFactor = loadFactor;
    }

    public HashTable(int size, double loadFactor) {
        array = new Entry[size];
        this.loadFactor = loadFactor;
    }

    Object put(Object key, Object value) {
        int ind = getInd(key);

        Entry old = array[ind];
        array[ind] = new Entry(key, value);

        if (old == null || old.deleted) {
            size++;
            resize();
            return null;
        } else {
            return old.value;
        }
    }

    Object get(Object key) {
        int ind = getInd(key);

        if (array[ind] == null || array[ind].deleted) {
            return null;
        } else {
            return array[ind].value;
        }
    }

    Object remove(Object key) {
        int ind = getInd(key);

        if (array[ind] == null || array[ind].deleted) {
            return null;
        } else {
            array[ind].deleted = true;
            size--;
            return array[ind].value;
        }
    }

    int size() {
        return size;
    }

    private void resize() {
        if (size >= loadFactor * array.length) {
            Entry[] newArray = array;
            array = new Entry[newArray.length * 2];

            for (int i = 0; i < newArray.length; i++) {
                if (newArray[i] != null && !newArray[i].deleted) {
                    array[getInd(newArray[i].key)] = newArray[i];
                }
            }
        }
    }

    private int getInd(Object key) {
        int hc = Math.abs(key.hashCode() % array.length);

        while (array[hc] != null && !array[hc].key.equals(key)) {
            hc = (hc + 1) % array.length;
        }

        return hc;
    }


    private class Entry {
        private Object key;
        private Object value;
        private boolean deleted;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }
}
