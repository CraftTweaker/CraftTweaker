package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

public class PartialScriptReference implements IPartialExpression {
    
    private final HashMap<String, PartialScriptReference> subs = new HashMap<>();
    private EnvironmentClass environmentScript = null;
    
    public PartialScriptReference() {
    }
    
    public void addScriptOrDirectory(EnvironmentClass environmentScript, String[] names) {
        if(names.length == 0) {
            this.environmentScript = environmentScript;
            return;
        }
        String name = names[0];
        subs.putIfAbsent(name, new PartialScriptReference());
        subs.get(name).addScriptOrDirectory(environmentScript, Arrays.copyOfRange(names, 1, names.length));
        
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
        IPartialExpression out = subs.get(name);
        if(out == null && environmentScript != null) {
            out = environmentScript.getValue(name, position);
        }
        
        if(out != null) {
            return out;
        }
        
        environment.error(position, "Could not get Reference: " + name);
        return new ExpressionInvalid(position);
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
