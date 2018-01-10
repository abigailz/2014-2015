package com.borya.core.userdata.store.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.borya.core.userdata.store.UserDataStore;

@SuppressWarnings("hiding")
public class UserDataStoreImpl<String,Object> implements UserDataStore<String, Object>{

	private Logger log = Logger.getLogger(getClass());
	private Map<String,Object> map;
	
	public UserDataStoreImpl(){
		this(100);
	}
	
	public UserDataStoreImpl(int initialCapacity){
		map = new ConcurrentHashMap<String, Object>(initialCapacity);
	}
	
	public Object get(String key) {
		if(key == null){
			log.warn("Key is null.");
			return null;
		}
		return map.get(key);
	}

	public Iterator<Entry<String, Object>> getAll() {
		return Collections.unmodifiableMap(map).entrySet().iterator();
	}

	public Object put(String key, Object value) {
		if(key == null){
			log.warn("Key is null.");
			return null;
		}
		return map.put(key, value);
	}

	public Object remove(String key) {
		if(key == null){
			log.warn("Key is null.");
			return null;
		}
		return map.remove(key);
	}

	public int size(){
		return map.size();
	}
}
