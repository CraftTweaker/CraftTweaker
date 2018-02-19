package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Used to indicate a member setter. Companion to member getters, but allow
 * setting contents of this object as if they were members.
 * <p>
 * If this method is a native class, it accepts a string parameter with the
 * member name and a second parameter with the value. If this method is an
 * expansion method, it accepts the source value, the member name and the value
 * to be assigned.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenMemberSetter {
    
}
