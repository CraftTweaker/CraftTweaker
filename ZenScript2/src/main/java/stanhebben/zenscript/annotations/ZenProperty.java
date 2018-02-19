package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate Properties. Will find and set the setter and getter methods,
 * that is, it will set up the methods for calling foo.property and for foo.property = bar.
 * </b>
 * Will error if it can't find the setter and getter methods
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ZenProperty {
    
    /**
     * Property name. If omitted, the field name is used.
     *
     * @return property name
     */
    String value() default "";

    /**
     * returns the method name used to get the setter, an empty string will use set plus the variable name,
     * if null, will not try to grab a setter.
     *
     * @return setter method name
     */
    String setter() default "";

    /**
     * returns the method name used to get the getter, an empty string will use get plus the variable name,
     * if null, will not try to grab a getter.
     *
     * @return getter method name
     */
    String getter() default "";
}
