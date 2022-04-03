package com.blamejared.crafttweaker.api.natives;

/**
 * Holds information relative to a specific native type that is yet to be registered to ZenCode.
 *
 * <p>This record is used as a holder to store all information related to a specific native type, providing thus a
 * uniform interface from which CraftTweaker is able to read information and {@linkplain IBakedTypeInfo bake} data
 * necessary to properly provide information to the ZenCode scripting engine.</p>
 *
 * <p>Once all information has been used to build a well-formed type, instances of the ZenCode data will be available
 * through the {@link INativeTypeRegistry}.</p>
 *
 * @param name         The fully qualified name of the class as exposed to ZenCode. It does not have to match the actual
 *                     name.
 * @param targetedType The native class this native type information is for.
 * @param constructors {@link Constructor}s that ZenCode should know about, allowing them to be used in scripts.
 * @param methods      {@link Method}s that ZenCode should know about, allowing them to be used in scripts.
 *
 * @since 9.1.0
 */
public record NativeTypeInfo(String name, Class<?> targetedType, Constructor[] constructors, Method... methods) {
    
    /**
     * Holds information relative to a specific native constructor that should be exposed to ZenCode.
     *
     * @param description        The description of the constructor, acting as documentation, or empty if no description
     *                           is to be provided.
     * @param sinceVersion       The first version of the API this constructor was exposed in, acting as documentation,
     *                           or empty if no initial version is to be provided.
     * @param deprecationMessage The message that accompanies a deprecation warning for this constructor, or empty if
     *                           the constructor is not deprecated.
     * @param parameters         The {@link Parameter}s accepted by this constructor.
     *
     * @since 9.1.0
     */
    public record Constructor(String description, String sinceVersion, String deprecationMessage,
                              Parameter... parameters) {
        
        /**
         * Creates an instance holding information relative to a native constructor that should be exposed to ZenCode.
         *
         * <p>The created constructor is not deprecated.</p>
         *
         * @param description  The description of the constructor, acting as documentation, or empty if no description
         *                     is to be provided.
         * @param sinceVersion The first version of the API this constructor was exposed in, acting as documentation, or
         *                     empty if no initial version is to be provided.
         * @param parameters   The {@link Parameter}s accepted by this constructor.
         *
         * @since 9.1.0
         */
        public Constructor(final String description, final String sinceVersion, Parameter... parameters) {
            
            this(description, sinceVersion, "", parameters);
        }
        
        /**
         * Creates an instance holding information relative to a native constructor that should be exposed to ZenCode.
         *
         * <p>The created constructor is not deprecated and no initial exposure version is provided.</p>
         *
         * @param description The description of the constructor, acting as documentation, or empty if no description is
         *                    to be provided.
         * @param parameters  The {@link Parameter}s accepted by this constructor.
         *
         * @since 9.1.0
         */
        public Constructor(final String description, final Parameter... parameters) {
            
            this(description, "", parameters);
        }
        
        /**
         * Creates an instance holding information relative to a native constructor that should be exposed to ZenCode.
         *
         * <p>The created constructor is not deprecated, no initial exposure version is provided, and it has no
         * description.</p>
         *
         * @param parameters The {@link Parameter}s accepted by this constructor.
         *
         * @since 9.1.0
         */
        public Constructor(final Parameter... parameters) {
            
            this("", parameters);
        }
        
    }
    
    /**
     * Holds information relative to a specific native method that should be exposed to ZenCode.
     *
     * @param name       The name of the method that should be exposed. The name has to be the same as the native
     *                   method's one.
     * @param getter     The name of the property that can be used to invoke this method as if it were a ZenCode
     *                   property, if desired; empty otherwise. E.g., if the method name is {@code getFoo}, specifying
     *                   {@code foo} as getter means that the method will also be called when the property {@code foo}
     *                   is accessed in a getter context in a ZenCode script.
     * @param setter     The name of the property that can be used to invoke this method as if it were a ZenCode
     *                   property, if desired; empty otherwise. E.g., if the method name is {@code setFoo}, specifying
     *                   {@code foo} as setter means that the method will also be called when the property {@code foo}
     *                   is accessed in a setter context in a ZenCode script.
     * @param parameters The {@link Parameter}s accepted by this method.
     *
     * @since 9.1.0
     */
    public record Method(String name, String getter, String setter, Parameter... parameters) {
        
        /**
         * Constructs an instance holding information relative to a native method that should be exposed to ZenCode.
         *
         * <p>The created method has no setter alias.</p>
         *
         * @param name       The name of the method that should be exposed. The name has to be the same as the native
         *                   method's one.
         * @param getter     The name of the property that can be used to invoke this method as if it were a ZenCode
         *                   property, if desired; empty otherwise. E.g., if the method name is {@code getFoo},
         *                   specifying {@code foo} as getter means that the method will also be called when the
         *                   property {@code foo} is accessed in a getter context in a ZenCode script.
         * @param parameters The {@link Parameter}s accepted by this method.
         *
         * @since 9.1.0
         */
        public Method(String name, String getter, Parameter... parameters) {
            
            this(name, getter, "", parameters);
        }
        
        /**
         * Constructs an instance holding information relative to a native method that should be exposed to ZenCode.
         *
         * <p>The created method has no setter or getter alias.</p>
         *
         * @param name       The name of the method that should be exposed. The name has to be the same as the native
         *                   method's one.
         * @param parameters The {@link Parameter}s accepted by this method.
         *
         * @since 9.1.0
         */
        public Method(String name, Parameter... parameters) {
            
            this(name, "", parameters);
        }
        
    }
    
    /**
     * Holds information relative to a specific parameter of a {@linkplain Method native method} or
     * {@linkplain Constructor native constructor} that is exposed to ZenCode.
     *
     * @param type        The type of the parameter that should be exposed.
     * @param name        The name of the parameter that is being targeted.
     * @param description The description of the parameter, acting as documentation.
     * @param examples    A series of strings that can be used to provide examples of valid arguments for this
     *                    parameter, acting as documentation; or an empty array if no examples are to be provided.
     *
     * @since 9.1.0
     */
    public record Parameter(Class<?> type, String name, String description, String... examples) {
        
        /**
         * Constructs an instance holding information relative to a parameter of a method or constructor that is exposed
         * to ZenCode.
         *
         * <p>The created parameter has no description.</p>
         *
         * @param type     The type of the parameter that should be exposed.
         * @param name     The name of the parameter that is being targeted.
         * @param examples A series of strings that can be used to provide examples of valid arguments for this
         *                 parameter, acting as documentation; or an empty array if no examples are to be provided.
         *
         * @since 9.1.0
         */
        public Parameter(Class<?> type, String name, String... examples) {
            
            this(type, name, "No description provided", examples);
        }
        
        /**
         * Constructs an instance holding information relative to a parameter of a method or constructor that is exposed
         * to ZenCode.
         *
         * <p>The created parameter has no description and no examples.</p>
         *
         * @param type The type of the parameter that should be exposed.
         * @param name The name of the parameter that is being targeted.
         *
         * @since 9.1.0
         */
        public Parameter(Class<?> type, String name) {
            
            this(type, name, new String[0]);
        }
        
    }
    
    /**
     * Creates an instance holding information relative to a specific native type that should be exposed to ZenCode.
     *
     * <p>The created instance will not expose any native methods.</p>
     *
     * @param name         The fully qualified name of the class as exposed to ZenCode. It does not have to match the
     *                     actual name.
     * @param targetedType The native class this native type information is for.
     * @param constructors {@link Constructor}s that ZenCode should know about, allowing them to be used in scripts.
     *
     * @since 9.1.0
     */
    public NativeTypeInfo(String name, Class<?> targetedType, Constructor... constructors) {
        
        this(name, targetedType, constructors, new Method[0]);
    }
    
}
