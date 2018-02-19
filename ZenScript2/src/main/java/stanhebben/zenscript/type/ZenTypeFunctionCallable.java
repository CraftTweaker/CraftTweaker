package stanhebben.zenscript.type;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.type.casting.CastingRuleMatchedFunction;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

/**
 * @author Stanneke
 */
public class ZenTypeFunctionCallable extends ZenTypeFunction {
    
    private final String className;
    private final String descriptor;
    private final Map<ZenType, CastingRuleMatchedFunction> implementedInterfaces = new HashMap<>();
    
    public ZenTypeFunctionCallable(ZenType returnType, List<ParsedFunctionArgument> arguments, String className, String descriptor) {
        super(returnType, arguments);
        this.className = className;
        this.descriptor = descriptor;
    }
    
    public ZenTypeFunctionCallable(ZenType returnType, ZenType[] argumentTypes, String className, String descriptor) {
        super(returnType, argumentTypes);
        this.className = className;
        this.descriptor = descriptor;
    }
    
    public ZenTypeFunctionCallable(ZenType returnType, ZenType[] argumentTypes, String className) {
        super(returnType, argumentTypes);
        this.className = className;
        StringBuilder sb = new StringBuilder("(");
        Arrays.stream(argumentTypes).map(ZenType::getSignature).forEach(sb::append);
        sb.append(")").append(returnType.getSignature());
        this.descriptor = sb.toString();
    }
    
    public String getClassName() {
        return className;
    }
    
    @Override
    public String getSignature() {
        return getClassName();
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
        return new ExpressionFunctionCall(position, arguments, returnType, receiver, className, descriptor);
    }
    
    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        return Arrays.copyOf(argumentTypes, numArguments);
    }
    
    @Override
    public Class toJavaClass() {
        // TODO: complete
        return null;
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionNull(position);
    }
}
