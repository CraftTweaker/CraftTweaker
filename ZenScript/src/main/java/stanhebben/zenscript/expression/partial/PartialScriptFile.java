package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class PartialScriptFile implements IPartialExpression {
    
    private final EnvironmentClass environmentScript;
    
    public PartialScriptFile(EnvironmentClass environmentScript) {
        this.environmentScript = environmentScript;
    }
    
    @Override
    public Expression eval(IEnvironmentGlobal environment) {
        throw new UnsupportedOperationException("Cannot eval a script file!");
    }
    
    @Override
    public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
        throw new UnsupportedOperationException("Cannot assign to a script file!");
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        return environmentScript.getValue(name, position);
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
        throw new UnsupportedOperationException("Cannot call a script file!");
    }
    
    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        return new ZenType[0];
    }
    
    @Override
    public IZenSymbol toSymbol() {
        throw new UnsupportedOperationException("Cannot make a script file a symbol!");
    }
    
    @Override
    public ZenType getType() {
        throw new UnsupportedOperationException("Cannot get a script file's type since it has no type!");
    }
    
    @Override
    public ZenType toType(IEnvironmentGlobal environment) {
        throw new UnsupportedOperationException("Cannot cast a script file!");
    }
}
