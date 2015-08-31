/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript;

import java.util.List;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.TypeRegistry;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;

/**
 *
 * @author Stanneke
 */
public interface IZenCompileEnvironment {
	public IZenErrorLogger getErrorLogger();

	public IZenSymbol getGlobal(String name);

	public IZenSymbol getDollar(String name);

	public IZenSymbol getBracketed(IEnvironmentGlobal environment, List<Token> tokens);

	public TypeRegistry getTypeRegistry();

	public TypeExpansion getExpansion(String type);
}
