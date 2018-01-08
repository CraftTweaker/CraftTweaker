package crafttweaker.annotations;

import java.lang.annotation.*;

/**
 * Called when a class is initially registered with CraftTweaker.
 *
 * This annotation should be attached to a public static method with no parameters.
 * @author Stan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnRegister {
    
}
