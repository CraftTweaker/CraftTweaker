package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Used to indicate an exposed method.
 * <p>
 * If this method is part of a native class, it will simply accept the
 * parameters as provided. If it is part of an expansion method, it will also
 * accept the instance value as first parameter.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenMethod {
    
    /**
     * Method name. If omitted, the original method name will be used.
     *
     * @return method name
     */
    String value() default "";
}
