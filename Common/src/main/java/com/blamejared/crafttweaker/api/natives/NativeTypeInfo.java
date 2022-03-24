package com.blamejared.crafttweaker.api.natives;

public record NativeTypeInfo(String name, Class<?> targetedType, Constructor[] constructors, Method... methods) {
    
    public record Constructor(String description, String sinceVersion, String deprecationMessage,
                              Parameter... parameters) {
        
        public Constructor(final String description, final String sinceVersion, Parameter... parameters) {
            
            this(description, sinceVersion, "", parameters);
        }
        
        public Constructor(final String description, final Parameter... parameters) {
            
            this(description, "", parameters);
        }
        
        public Constructor(final Parameter... parameters) {
            
            this("", parameters);
        }
        
    }
    
    public record Method(String name, String getter, String setter, Parameter... parameters) {
        
        public Method(String name, String getter, Parameter... parameters) {
            
            this(name, getter, "", parameters);
        }
        
        public Method(String name, Parameter... parameters) {
            
            this(name, "", parameters);
        }
        
    }
    
    public record Parameter(Class<?> type, String name, String description, String... examples) {
        
        public Parameter(Class<?> type, String name, String... examples) {
            
            this(type, name, "No description provided", examples);
        }
        
        public Parameter(Class<?> type, String name) {
            
            this(type, name, new String[0]);
        }
        
    }
    
    public NativeTypeInfo(String name, Class<?> targetedType, Constructor... constructors) {
        
        this(name, targetedType, constructors, new Method[0]);
    }
    
}
