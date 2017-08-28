package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Used to annotate Properties. Will make the given method accessible as setter, TODO: CHANGE DESCRIPTION
 * that is, it's called when value.name is being assinged a value.
 * <p>
 * For a native class, a single argument with the assinged value is provided.
 * For an expansion, the target object and assigned value are provided.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ZenProperty {
    
    /**
     * Setter name. If omitted, the method name is used as property name.
     *
     * @return setter name
     */
    String value() default "";
}
