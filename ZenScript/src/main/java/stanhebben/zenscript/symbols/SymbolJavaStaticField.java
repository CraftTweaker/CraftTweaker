/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import java.lang.reflect.Field;
import stanhebben.zenscript.compiler.ITypeRegistry;
import stanhebben.zenscript.expression.partial.ExpressionJavaStaticField;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class SymbolJavaStaticField implements IZenSymbol {
	private final Class cls;
	private final Field field;
	private final ITypeRegistry types;
	
	public SymbolJavaStaticField(Class cls, Field field, ITypeRegistry types) {
		this.cls = cls;
		this.field = field;
		this.types = types;
	}
	
	@Override
	public IPartialExpression instance(ZenPosition position) {
		return new ExpressionJavaStaticField(position, cls, field, types);
	}
}
