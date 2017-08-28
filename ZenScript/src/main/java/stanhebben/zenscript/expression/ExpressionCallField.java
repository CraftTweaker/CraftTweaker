package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Field;

/**
 * @author Stanneke
 */
public class ExpressionCallField extends Expression {

    private final Field field;

    private final Expression receiver;
    
    public ExpressionCallField(ZenPosition position, IEnvironmentGlobal environment, Field field, Expression receiver) {
        super(position);

        this.field = field;

        this.receiver = receiver;
        // this.arguments = JavaMethod.rematch(position, field, environment, arguments);
    }

    @Override
    public ZenType getType() {
        return field.getReturnType();
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        receiver.compile(true, environment);

        for(Expression argument : arguments) {
            argument.compile(true, environment);
        }

        field.invokeVirtual(environment.getOutput());
        if(field.getReturnType() != ZenType.VOID && !result) {
            environment.getOutput().pop(field.getReturnType().isLarge());
        }
    }
}
