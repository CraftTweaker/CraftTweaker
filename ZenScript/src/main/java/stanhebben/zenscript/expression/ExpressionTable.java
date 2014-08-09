package stanhebben.zenscript.expression;

import java.util.HashMap;
import java.util.Map;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.type.ZenTypeAssociative;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;

public class ExpressionTable extends Expression {
	private final Expression[] keys;
	private final Expression[] values;
	private final ZenTypeAssociative type;
	
	public ExpressionTable(ZenPosition position, Expression[] keys, Expression[] values) {
		super(position);
		
		this.keys = keys;
		this.values = values;
		
		type = new ZenTypeAssociative(ZenTypeAny.INSTANCE, ZenTypeAny.INSTANCE);
	}
	
	public ExpressionTable(ZenPosition position, Expression[] keys, Expression[] values, ZenTypeAssociative type) {
		super(position);
		
		this.keys = keys;
		this.values = values;
		
		this.type = type;
	}

	@Override
	public ZenType getType() {
		return type;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			ZenType keyType = type.getKeyType();
			ZenType valueType = type.getValueType();
			
			MethodOutput output = environment.getOutput();
			output.newObject(HashMap.class);
			output.dup();
			output.invokeSpecial(internal(HashMap.class), "<init>", "()V");
			
			for (int i = 0; i < keys.length; i++) {
				output.dup();
				keys[i].cast(getPosition(), environment, keyType).compile(true, environment);
				values[i].cast(getPosition(), environment, valueType).compile(true, environment);
				output.invokeInterface(internal(Map.class), "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
				output.pop();
			}
		} else {
			for (Expression key : keys) {
				key.compile(false, environment);
			}
			for (Expression value : values) {
				value.compile(false, environment);
			}
		}
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
		if (type instanceof ZenTypeAssociative) {
			ZenTypeAssociative keyType = (ZenTypeAssociative) type;
			Expression[] newKeys = new Expression[keys.length];
			Expression[] newValues = new Expression[values.length];
			
			for (int i = 0; i < keys.length; i++) {
				newKeys[i] = keys[i].cast(position, environment, keyType.getKeyType());
				newValues[i] = values[i].cast(position, environment, keyType.getValueType());
			}
			
			return new ExpressionTable(getPosition(), newKeys, newValues, keyType);
		} else if (this.type.canCastImplicit(type, environment)) {
			return this.type.cast(position, environment, this, type);
		} else {
			environment.error(position, "cannot cast " + this.type + " to " + type);
			return new ExpressionInvalid(position, type);
		}
	}
}
