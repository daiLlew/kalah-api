package com.dai.llew.kalah.model;

import java.util.AbstractMap;

public class TestCase<K, V> extends AbstractMap.SimpleEntry<K, V> {

    public TestCase(K key, V value) {
        super(key, value);
    }

    public K input() {
        return getKey();
    }

    public V expected() {
        return getValue();
    }
}
