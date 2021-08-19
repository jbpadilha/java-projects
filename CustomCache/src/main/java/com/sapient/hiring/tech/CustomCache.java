package com.sapient.hiring.tech;
import com.sapient.hiring.tech.common.Shape;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class CustomCache<K, V> {
    private long timeToLive = 5;
    private final LinkedHashSet<CacheListMap<K,V>> cacheList;

    public CustomCache(){
        cacheList =new LinkedHashSet<>();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(timeToLive * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cleanupCache();
        });
        t.setDaemon(true);
        t.start();
    }

    public static boolean compare(Object o1, Object o2) {
        if (o1 instanceof Shape) {
            return o2 instanceof Shape;
        }
        return o1.getClass() == o2.getClass();
    }

    public void put(K key, V value) throws Exception {
        synchronized (cacheList) {
            CacheListMap<K,V> cacheListMap = new CacheListMap<>(key, value);
            if (cacheList.isEmpty()) {
                cacheList.add(cacheListMap);
            } else {
                // Check for type
                CacheListMap<K,V> cacheListMapKeyTypeCheck =  cacheList.stream().filter(c -> c.getKey().getClass() == key.getClass()).findFirst().orElse(null);
                if (cacheListMapKeyTypeCheck == null || compare(value, cacheListMapKeyTypeCheck.getValue())) {
                    cacheList.add(cacheListMap);
                } else {
                    String allowedType = cacheListMapKeyTypeCheck.getValue().getClass().toString();
                    if (cacheListMapKeyTypeCheck.getValue() instanceof Shape) {
                        allowedType = Shape.class.toString();
                    }
                    String errMsg = "Object of class ["+value.getClass()+"] not allowable for this Key Type ["+cacheListMapKeyTypeCheck.getKey().getClass()+"]. Allowed types are ["+allowedType+"] or it sub and super types";
                    throw new Exception(errMsg);
                }
            }
        }
    }

    public boolean remove(K key){
        synchronized (cacheList) {
            CacheListMap<K,V> foundObj = null;
            for (CacheListMap<K,V> cacheListMap : cacheList) {
                if (key.equals(cacheListMap.getKey())) {
                    foundObj = cacheListMap;
                }
            }
            if (foundObj != null) {
                cacheList.remove(foundObj);
                return true;
            }
        }
        return false;
    }


    public V get(K key){
        synchronized (cacheList) {
            for (CacheListMap<K,V> cacheListMap : cacheList) {
                if (key.equals(cacheListMap.getKey())) {
                    return cacheListMap.getValue();
                }
            }
        }
        return null;
    }

    public void cleanupCache() {
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;

        synchronized (cacheList) {
            deleteKey = new ArrayList<K>((cacheList.size() / 2) + 1);
            K key = null;
            for (CacheListMap<K,V> cacheListMap : cacheList) {
                key = cacheListMap.getKey();
                if (now > (timeToLive + cacheListMap.getTime())) {
                    deleteKey.add(key);
                }
            }
        }
        for (K key : deleteKey) {
            synchronized (cacheList) {
                remove(key);
            }
            Thread.yield();
        }
    }

}