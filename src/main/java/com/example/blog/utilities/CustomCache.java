package com.example.blog.utilities;

import com.example.blog.entities.User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomCache
{
    public static final int MAX_CACHE_SIZE = 10;

    private final Map<String, List<User>> cacheMap = new LinkedHashMap<>()
    {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, List<User>> eldest)
        {
            return size() > MAX_CACHE_SIZE;
        }
    };

    public void addToCache(String key, List<User> value)
    {
        cacheMap.put(key, value);
    }

    public void removeFromCache(String key)
    {
        cacheMap.remove(key);
    }

    public List<User> getFromCache(String key)
    {
        return cacheMap.get(key);
    }

    public boolean containsKey(String key)
    {
        return cacheMap.containsKey(key);
    }
}