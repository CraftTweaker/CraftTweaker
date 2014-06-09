/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker;

import java.util.List;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;

/**
 *
 * @author Stan
 */
public interface IBracketHandler {
	public IZenSymbol resolve(List<Token> tokens);
}
