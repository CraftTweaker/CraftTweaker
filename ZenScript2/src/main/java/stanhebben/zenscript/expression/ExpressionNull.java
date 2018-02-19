package stanhebben.zenscript.expression;

import org.objectweb.asm.Label;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ExpressionNull extends Expression {

    public ExpressionNull(ZenPosition position) {
        super(position);
    }

    @Override
    public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
        if(type.isPointer()) {
            return this;
        } else {
            environment.error(position, "Cannot convert null to " + type);
            return new ExpressionInvalid(position);
        }
    }

    @Override
    public ZenType getType() {
        return ZenTypeNull.INSTANCE;
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(result) {
            environment.getOutput().aConstNull();
        }
    }

    @Override
    public void compileIf(Label onElse, IEnvironmentMethod environment) {
        environment.getOutput().goTo(onElse);
    }
}
