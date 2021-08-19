package com.sapient.hiring.tech;

public class CacheListMap<K,V> {
    private long time;
    private K key;
    private V value;
    public CacheListMap(K k, V v) {
        this.key = k;
        this.value = v;
        this.time = System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
