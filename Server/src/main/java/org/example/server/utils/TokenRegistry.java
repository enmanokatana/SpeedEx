package org.example.server.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class TokenRegistry {

    private final Map<String, String> activeTokens = new ConcurrentHashMap<>();


    public void addToken(String token,String email){
        activeTokens.put(token,email);
    }
    public void removeToken(String token){
        activeTokens.remove(token);
    }

    public Map<String, String> getActiveTokens() {
        return activeTokens;
    }


}
