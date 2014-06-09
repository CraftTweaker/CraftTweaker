/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import java.util.List;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.compiler.TypeRegistry;
import stanhebben.zenscript.parser.Token;

/**
 *
 * @author Stanneke
 */
public interface IZenCompileEnvironment {
	public IZenErrorLogger getErrorLogger();
	
	public IZenSymbol getGlobal(String name);
	
	public IZenSymbol getDollar(String name);
	
	public IZenSymbol getBracketed(List<Token> tokens);
	
	public TypeRegistry getTypeRegistry();
	
	public TypeExpansion getExpansion(String type);
}
