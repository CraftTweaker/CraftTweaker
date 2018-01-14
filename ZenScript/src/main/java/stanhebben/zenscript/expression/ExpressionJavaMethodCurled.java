package stanhebben.zenscript.expression;

import org.objectweb.asm.Type;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

/**
 * @author Stanneke
 */

public class ExpressionJavaMethodCurled extends Expression {

    private final JavaMethod method;
    private final ZenType type;
    private final Expression receiver;

    public ExpressionJavaMethodCurled(ZenPosition position, JavaMethod method, IEnvironmentGlobal environment, Expression receiver) {
        super(position);

        this.method = method;
        this.receiver = receiver;

        List<ParsedFunctionArgument> arguments = new ArrayList<>();
        for(int i = 0; i < method.getMethod().getParameterTypes().length; i++) {
            arguments.add(new ParsedFunctionArgument("p" + i, environment.getType(method.getMethod().getGenericParameterTypes()[i])));
        }

        this.type = new ZenTypeFunction(environment.getType(method.getMethod().getGenericReturnType()), arguments);
    }

    @Override
    public ZenType getType() {
        return type;
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        // TODO: compile
    }
}
