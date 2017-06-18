package crafttweaker.annotations;

import java.lang.annotation.*;

/**
 * Marks a bracket handler. Bracket handlers are automatically registered. The
 * marked class should have an empty constructor.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BracketHandler {
    
    /**
     * Indicates priority. A lower value means a higher priority. Only change if
     * you have issues, default value is 10. Internal crafttweaker handlers have
     * priorities up to 100.
     *
     * @return priority
     */
    int priority() default 10;
}
