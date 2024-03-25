package com.example.blog.utilities;

import com.example.blog.entities.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomCache {
    public static final int MAX_CACHE_SIZE = 10;

    private final Map<String, Set<User>> cacheMap = new LinkedHashMap<>()
    {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Set<User>> eldest)
        {
            return size() > MAX_CACHE_SIZE;
        }
    };

    public void addToCacheUser(String key, User user)
    {
        Set<User> users = cacheMap.computeIfAbsent(key, k -> new HashSet<>());
        users.add(user);
        cacheMap.put(key, users);
    }

    public void addToCache(String key, Set<User> value)
    {
        cacheMap.put(key, value);
    }

    public void removeFromCache(String key, User user) {
        Set<User> users = cacheMap.get(key);
        if (users != null)
        {
            users.remove(user);
            if (users.isEmpty())
            {
                cacheMap.remove(key);
            }
            else
            {
                cacheMap.put(key, users);
            }
        }
    }

    public Set<User> getFromCache(String key)
    {
        return cacheMap.get(key);
    }

    public boolean containsKey(String key)
    {
        return cacheMap.containsKey(key);
    }
}