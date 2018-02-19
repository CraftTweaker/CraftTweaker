package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Used to indicate static methods for expansions. For native classes, use
 * ZenMethod instead on a static method.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenMethodStatic {
    
    /**
     * Method name. If omitted, the annotated method name will be used.
     *
     * @return method name
     */
    String value() default "";
}
