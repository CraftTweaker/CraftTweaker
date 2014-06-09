/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression.partial;

import java.lang.reflect.Field;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.compiler.ITypeRegistry;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class ExpressionJavaStaticField extends Expression {
	private final Class cls;
	private final Field field;
	private final ITypeRegistry types;
	
	public ExpressionJavaStaticField(ZenPosition position, Class cls, Field field, ITypeRegistry types) {
		super(position);
		
		this.cls = cls;
		this.field = field;
		this.types = types;
	}

	@Override
	public ZenType getType() {
		return types.getType(field.getGenericType());
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			environment.getOutput().getStaticField(cls, field);
		}
	}
}
