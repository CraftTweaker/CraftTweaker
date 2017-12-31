package crafttweaker.zenscript;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;

import java.util.List;

/**
 * Bracket handlers enable processing of the bracket syntax.
 * <p>
 * Inside brackets, any kind of token is acceptable except the closing bracket.
 * (&gt;) Bracket handlers (multiple handlers can be registered) will resolve
 * these tokens into actual values. Values have to be ZenScript symbols and will
 * resolve at compile-time.
 * <p>
 * These may of course return an expression that are executed on run-time, but
 * the type of the resulting value must be known compile-time.
 *
 * @author Stan Hebben
 */
public interface IBracketHandler {

    /**
     * Resolves a set of tokens.
     * <p>
     * If the series of tokens is unrecognized, this method should return null.
     *
     * @param environment global compilation environment
     * @param tokens      token stream to be detected
     * @return the resolved symbol, or null
     */
    IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens);
}
