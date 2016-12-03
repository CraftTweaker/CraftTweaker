package minetweaker.annotations;

import java.lang.annotation.*;

/**
 * Indicates that a certain class is only available when a specific mod is
 * available. Multiple mods can be specified.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ModOnly {
	
	String[] value();
	
	String version() default "";
}
