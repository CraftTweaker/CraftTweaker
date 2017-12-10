package stanhebben.zenscript.expression;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeArrayBasic;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import stanhebben.zenscript.util.ZenTypeUtil;

public class ExpressionArrayAdd extends Expression {
	
	private final Expression array, value;
	private final ZenTypeArrayBasic type;

	public ExpressionArrayAdd(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression val) {
		super(position);
		this.array = array;
		this.value = val;
		this.type = (ZenTypeArrayBasic) array.getType();
	}

	@Override
	public ZenType getType() {
		return type;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		MethodOutput output = environment.getOutput();
		output.newObject(ArrayList.class);
		output.dup();
		array.compile(true, environment);
		output.invokeStatic(Arrays.class, "asList", List.class, Object[].class);

		output.construct(ArrayList.class, Collection.class);
		output.dup();
		value.cast(getPosition(), environment, ZenTypeUtil.checkPrimitive(type.getBaseType())).compile(true, environment);
		output.invokeInterface(List.class, "add", boolean.class, Object.class);
		output.pop();
		output.invokeInterface(List.class, "toArray", Object[].class, new Class<?>[]{});
	}

}
