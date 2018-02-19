package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Used to indicate an operator. The arguments depend on the kind of operator.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenOperator {
    
    /**
     * Operator type for this operator.
     *
     * @return operator type
     */
    OperatorType value();
}
