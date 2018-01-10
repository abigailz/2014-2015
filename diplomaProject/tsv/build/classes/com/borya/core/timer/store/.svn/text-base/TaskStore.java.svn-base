package com.borya.core.timer.store;

import java.util.Iterator;
import java.util.Map;

public interface TaskStore<K,V> {
	
	public V get(K key);
	
	public Iterator<Map.Entry<K, V>> getAll();
	
	public V put(K key,V value);
	
	public V remove(K key);
}