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


public class ExpressionJavaMethodStatic extends Expression {

    private final JavaMethod method;
    private final ZenType type;

    public ExpressionJavaMethodStatic(ZenPosition position, JavaMethod method, IEnvironmentGlobal environment) {
        super(position);

        this.method = method;

        List<ParsedFunctionArgument> arguments = new ArrayList<>();
        for(int i = 0; i < method.getParameterTypes().length; i++) {
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
