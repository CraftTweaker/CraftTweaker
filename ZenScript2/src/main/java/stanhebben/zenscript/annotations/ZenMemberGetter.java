package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Indicates a member getter. A member getter is a special kind of operator
 * which is a fallback if a member could not be resolved. Can be used to make
 * contents available as if they were members.
 * <p>
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
