package com.blamejared.crafttweaker.gametest.framework.parameterized;

import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Arguments {
    
    private final Map<String, Object> arguments;
    
    private Arguments(Map<String, Object> arguments) {
        
        this.arguments = Collections.unmodifiableMap(arguments);
    }
    
    public <T> T named(String name) {
        
        return GenericUtil.uncheck(this.arguments.get(name));
    }
    
    public <T> T expected() {
        
        return GenericUtil.uncheck(this.arguments.get("expected"));
    }
    
    public <T> T actual() {
        
        return GenericUtil.uncheck(this.arguments.get("actual"));
    }
    
    public <T> T input() {
        
        return GenericUtil.uncheck(this.arguments.get("input"));
    }
    
    public <T> T other() {
        
        return GenericUtil.uncheck(this.arguments.get("other"));
    }
    
    public String listArgs() {
        
        return this.arguments.entrySet()
                .stream()
                .map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(","));
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("Arguments{");
        sb.append("arguments=").append(arguments);
        sb.append('}');
        return sb.toString();
    }
    
    public static class Builder {
        
        private final String name;
        
        private final Map<String, Supplier<?>> arguments = new HashMap<>();
        
        private Builder(String name) {
            
            this.name = name;
        }
        
        public static Arguments.Builder named(String name) {
            
            return new Builder(name);
        }
        
        public <T> Arguments.Builder with(String name, Supplier<T> t) {
            
            this.arguments.put(name, t);
            return this;
        }
        
        public <T> Arguments.Builder with(String name, T t) {
            
            return with(name, () -> t);
        }
        
        public <T> Arguments.Builder input(Supplier<T> t) {
            
            
            return with("input", t);
        }
        
        public <T> Arguments.Builder input(T t) {
            
            return with("input", t);
        }
        
        public <T> Arguments.Builder other(Supplier<T> t) {
            
            return with("other", t);
        }
        
        public <T> Arguments.Builder other(T t) {
            
            return with("other", t);
        }
        
        public <T> Arguments.Builder actual(Supplier<T> t) {
            
            return with("actual", t);
        }
        
        public <T> Arguments.Builder actual(T t) {
            
            return with("actual", t);
        }
        
        public <T> Arguments.Builder expected(Supplier<T> t) {
            
            return with("expected", t);
        }
        
        public <T> Arguments.Builder expected(T t) {
            
            return with("expected", t);
        }
        
        public String name() {
            
            return name;
        }
        
        public Arguments build() {
            
            return new Arguments(this.arguments.entrySet()
                    .stream()
                    .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                    .collect(Collectors.toMap(o -> o.getKey(), o -> o.getValue())));
        }
        
    }
    
}
