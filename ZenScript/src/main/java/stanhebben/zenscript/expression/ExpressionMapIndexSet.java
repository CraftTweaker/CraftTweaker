/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import java.util.Map;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeVoid;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 *
 * @author Stanneke
 */
public class ExpressionMapIndexSet extends Expression {
	private final Expression map;
	private final Expression index;
	private final Expression value;

	private final ZenType type;

	public ExpressionMapIndexSet(ZenPosition position, Expression map, Expression index, Expression value) {
		super(position);

		this.map = map;
		this.index = index;
		this.value = value;

		type = ZenTypeVoid.INSTANCE;
	}

	@Override
	public ZenType getType() {
		return type;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			map.compile(result, environment);
			index.compile(result, environment);
			environment.getOutput().invokeInterface(
					internal(Map.class),
					"put",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
		}
	}
}
