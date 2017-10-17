package stanhebben.zenscript.expression;

import java.util.List;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeVoid;
import stanhebben.zenscript.util.ZenPosition;

public class ExpressionArrayListSet extends Expression {
	
	private final Expression array;
	private final Expression index;
	private final Expression value;
	private final ZenType type;
	private final ZenPosition position;
	
	public ExpressionArrayListSet(ZenPosition position, Expression array, Expression index, Expression value) {
		super(position);
		this.array = array;
		this.index = index;
		this.value = value;
		this.type = ZenTypeVoid.INSTANCE;
		this.position = position;
	}
	
	@Override
	public ZenType getType() {
		return type;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		array.compile(result, environment);
		index.compile(result, environment);
		value.compile(result, environment);
		//environment.warning(position, "Compiling EALS with result = "+String.valueOf(result));
		if(result){			
			environment.getOutput().invokeInterface(List.class, "set", Object.class, int.class, Object.class);
			//environment.getOutput().checkCast(type.toASMType().getInternalName());
			
		}
	}

}
