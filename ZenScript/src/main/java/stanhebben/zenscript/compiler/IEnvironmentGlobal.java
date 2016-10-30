/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import java.util.Set;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public interface IEnvironmentGlobal extends ITypeRegistry, IZenErrorLogger {
	IZenCompileEnvironment getEnvironment();

	TypeExpansion getExpansion(String name);

	String makeClassName();

	boolean containsClass(String name);

	Set<String> getClassNames();

	byte[] getClass(String name);

	void putClass(String name, byte[] data);

	IPartialExpression getValue(String name, ZenPosition position);

	void putValue(String name, IZenSymbol value, ZenPosition position);
}
