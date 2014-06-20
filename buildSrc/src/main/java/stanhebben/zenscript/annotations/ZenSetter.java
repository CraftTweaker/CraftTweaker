package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate setters. Will make the given method accessible as setter, that is,
 * it's called when value.name is being assinged a value.
 * 
 * For a native class, a single argument with the assinged value is provided.
 * For an expansion, the target object and assigned value are provided.
 * 
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenSetter {
	/**
	 * Setter name. If omitted, the method name is used as property name.
	 * 
	 * @return setter name
	 */
	String value() default "";
}
