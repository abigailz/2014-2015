package com.borya.core;

import java.util.Iterator;
import java.util.Map;

public abstract interface Store<K,V> {
	
	V get(K key);
	
	Iterator<Map.Entry<K, V>> getAll();
	
	V put(K key,V value);
	
	V remove(K key);

}
