package com.blamejared.crafttweaker.api.zencode.bracket;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.PrefixedBracketParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Like the normal {@link PrefixedBracketParser} but ignores case considerations.
 * So {@code <recipeType:crafting>} and {@code <recipetype:crafting>} are considered equal.
 * <p>
 * We also omit a fallback BEP because CrT won't need one, so the {@link #parse} method is simpler
 */
public class IgnorePrefixCasingBracketParser implements BracketExpressionParser {
    
    /**
     * The know subParsers.
     * Contains all names in lowercase, so checking needs to lowercase the value as well.
     * Just use {@link #getSubParser} so you don't have to worry about that
     */
    private final Map<String, BracketExpressionParser> subParsers = new HashMap<>();
    
    /**
     * Registers a subParser to this BEP
     *
     * @param name   The name for the BEP to be found at
     * @param parser The BEP
     */
    public void register(String name, BracketExpressionParser parser) {
        
        if(subParsers.containsKey(name)) {
            final BracketExpressionParser existing = subParsers.get(name);
            final String existingClassname = existing.getClass().getCanonicalName();
            final String newClassName = parser.getClass().getCanonicalName();
            CraftTweakerAPI.LOGGER.info("Overriding BEP '{}' of type '{}' with one of type '{}'", name, existingClassname, newClassName);
        }
        subParsers.put(name.toLowerCase(), parser);
    }
    
    /**
     * Retrieves a subParser by its prefix.
     * Use this method instead of accessing the map directly because it also lower-cases the prefix
     *
     * @param prefix The prefix of the BEP
     *
     * @return The found BEP or {@code null} if not found
     */
    private BracketExpressionParser getSubParser(String prefix) {
        
        return subParsers.get(prefix.toLowerCase());
    }
    
    /**
     * Does the heavy lifting.
     * Reads the prefix ([T_IDENTIFIER, T_COLON]), and then finds a matching BEP based on the identifier content.
     * Then delegates to the found BEP or throws an exception.
     *
     * @param position start of the expression (which is the location of the &gt; token)
     * @param tokens   tokens to be parsed
     *
     * @return The parsed Expression, based on the parse method of the subParser
     *
     * @throws ParseException If no BEP matched the prefix
     */
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        
        StringBuilder prefix = new StringBuilder();
        while(tokens.peek().type != ZSTokenType.T_COLON) {
            String content = tokens.next().getContent();
            prefix.append(content);
        }
        if(prefix.isEmpty()) {
            throw new ParseException(position.withLength(tokens.peek().getContent().length()), "identifier expected");
        }
        tokens.required(ZSTokenType.T_COLON, ": expected");
        BracketExpressionParser subParser = getSubParser(prefix.toString());
        if(subParser == null) {
            throw new ParseException(position, "Invalid bracket expression: no prefix " + prefix);
        }
        
        return subParser.parse(position, tokens);
    }
    
    public Set<String> getSubParsersName() {
        
        return subParsers.keySet();
    }
    
}
