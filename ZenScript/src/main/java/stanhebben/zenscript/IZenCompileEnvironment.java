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
	IZenErrorLogger getErrorLogger();

	IZenSymbol getGlobal(String name);

	IZenSymbol getDollar(String name);

	IZenSymbol getBracketed(IEnvironmentGlobal environment, List<Token> tokens);

	TypeRegistry getTypeRegistry();

	TypeExpansion getExpansion(String type);
}
