package crafttweaker.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks that a class should be automatically registered with Crafttweaker.
 * Combine with @{@link ModOnly} to automatically load the class if a mod is present
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ZenRegister {
}
