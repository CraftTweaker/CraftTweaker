/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeByte;
import stanhebben.zenscript.type.ZenTypeInt;
import stanhebben.zenscript.type.ZenTypeLong;
import stanhebben.zenscript.type.ZenTypeShort;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionInt extends Expression {
	private final long value;
	private final ZenType type;

	public ExpressionInt(ZenPosition position, long value, ZenType type) {
		super(position);

		this.value = value;
		this.type = type;
	}

	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
		if (type == this.type)
			return this;

		switch (type.getNumberType()) {
			case ZenType.NUM_BYTE:
			case ZenType.NUM_SHORT:
			case ZenType.NUM_INT:
			case ZenType.NUM_LONG:
				return new ExpressionInt(getPosition(), value, type);
			case ZenType.NUM_FLOAT:
			case ZenType.NUM_DOUBLE:
				return new ExpressionFloat(getPosition(), value, type);
		}

		return super.cast(position, environment, type);
	}

	@Override
	public ZenType getType() {
		return type;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (!result)
			return;

		MethodOutput output = environment.getOutput();
		if (type == ZenTypeByte.INSTANCE) {
			output.biPush((byte) value);
		} else if (type == ZenTypeShort.INSTANCE) {
			output.siPush((short) value);
		} else if (type == ZenTypeInt.INSTANCE) {
			output.constant((int) value);
		} else if (type == ZenTypeLong.INSTANCE) {
			output.constant(value);
		} else {
			throw new RuntimeException("Internal compiler error: int constant type is not an int");
		}
	}
}
