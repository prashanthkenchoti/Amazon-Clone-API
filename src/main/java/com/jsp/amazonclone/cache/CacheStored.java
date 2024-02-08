package com.jsp.amazonclone.cache;

import java.time.Duration;

import com.google.common.cache.CacheBuilder;

import com.google.common.cache.Cache;

public class CacheStored<T> {
	
	private Cache<String,T> cache;
	
	public CacheStored(Duration expiry)
	{
		this.cache=CacheBuilder.newBuilder()
				.expireAfterWrite(expiry)
				.concurrencyLevel(Runtime.getRuntime().availableProcessors())
				.build();
	}
	
	// method to add cache object
	public void add(String key,T value)
	{
		cache.put(key, value);
	}
	
	// method to retrieve cache object
	public T get(String key)
	{
		return cache.getIfPresent(key);
	}
	
	// method to delete cache object
	public void remove(String key)
	{
		cache.invalidate(key);
	}
	

}
