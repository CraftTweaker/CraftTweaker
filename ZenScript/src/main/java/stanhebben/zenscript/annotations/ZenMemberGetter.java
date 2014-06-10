package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a member getter. A member getter is a special kind of operator which
 * is a fallback if a member could not be resolved. Can be used to make contents
 * available as if they were members.
 * 
 * If this method is a native class, it accepts a single string parameter with
 * the member name. If this method is an expansion method, it accepts the source
 * value and the member name.
 * 
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenMemberGetter {
    
}
