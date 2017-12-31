package crafttweaker.tests.wiki;

import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

@BracketHandler(priority = 34)
@ZenRegister
public class BracketWiki implements IBracketHandler {

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if ((tokens.size() < 3)) return null;
        if (!tokens.get(0).getValue().equalsIgnoreCase("devBracket")) return null;
        if (!tokens.get(1).getValue().equals(":")) return null;

        return new devSymbol(tokens);
    }


    private class devSymbol implements IZenSymbol {

        private final String value;

        public devSymbol(List<Token> tokens) {
            StringBuilder sB = new StringBuilder();
            tokens.stream().map(Token::getValue).forEach(sB::append);
            this.value = sB.toString().replaceAll(":", " ");
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            return new ExpressionString(position, "DevSymbol: ".concat(value));
        }

    }

}
