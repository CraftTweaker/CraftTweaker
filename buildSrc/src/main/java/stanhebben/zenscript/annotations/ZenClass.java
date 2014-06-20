package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Exposes this class to ZenScript. The class can then be imported and accessed
 * into any script.
 * 
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ZenClass {
	/**
	 * Contains the class' package and name. If omitted, the java package and
	 * name will be used.
	 * 
	 * @return 
	 */
	String value() default "";
}
