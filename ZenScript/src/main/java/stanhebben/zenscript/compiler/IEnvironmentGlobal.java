/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import java.lang.reflect.Type;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public interface IEnvironmentGlobal extends ITypeRegistry, IZenErrorLogger {
	public IZenCompileEnvironment getEnvironment();
	
	public TypeExpansion getExpansion(String name);
	
	public String makeClassName();
	
	public boolean containsClass(String name);
	
	public void putClass(String name, byte[] data);
	
	public IPartialExpression getValue(String name, ZenPosition position);
	
	public void putValue(String name, IZenSymbol value);
}
