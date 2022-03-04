package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSToken;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionBinary;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionCast;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;
import org.openzen.zenscript.parser.expression.ParsedExpressionVariable;
import org.openzen.zenscript.parser.type.ParsedTypeBasic;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("ClassCanBeRecord")
final class ValidatedEscapableBracketParser implements BracketExpressionParser {
    
    private static final class BracketResolverCallExpression extends ParsedExpression {
        
        private static final String PACKAGE_SEPARATOR = Pattern.quote(".");
        
        private final Method resolver;
        private final ParsedExpression call;
        
        BracketResolverCallExpression(
                final CodePosition position,
                final Method resolver,
                final List<ParsedExpression> arguments
        ) {
            
            super(position);
            this.resolver = resolver;
            this.call = arguments.stream()
                    .skip(1L)
                    .reduce(arguments.get(0), (a, b) -> new ParsedExpressionBinary(b.position, a, b, OperatorType.ADD));
        }
        
        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            
            final ParsedExpression classCall = this.compileClassCall(this.resolver.getDeclaringClass());
            final ParsedExpression methodReference = this.compileMethodReference(classCall, this.resolver);
            return this.generateCall(methodReference, this.call).compile(scope);
        }
        
        @Override
        public boolean hasStrongType() {
            
            return true;
        }
        
        private ParsedExpression compileClassCall(final Class<?> owner) {
            
            final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
            final IZenClassRegistry registry = CraftTweakerAPI.getRegistry().getZenClassRegistry();
            final String ownerName = registry.getNameFor(loader, owner).orElseThrow();
            final String[] packages = ownerName.split(PACKAGE_SEPARATOR);
            
            return Arrays.stream(packages)
                    .skip(1L)
                    .reduce(
                            (ParsedExpression) new ParsedExpressionVariable(this.position, packages[0], null),
                            (prev, next) -> new ParsedExpressionMember(this.position, prev, next, null),
                            (a, b) -> {throw new UnsupportedOperationException("Never called");}
                    );
        }
        
        private ParsedExpression compileMethodReference(final ParsedExpression classCall, final Method target) {
            
            final String targetName = target.getName();
            return new ParsedExpressionMember(this.position, classCall, targetName, null);
        }
        
        private ParsedExpression generateCall(final ParsedExpression method, final ParsedExpression argument) {
            
            final ParsedExpression castedArgument = new ParsedExpressionCast(this.position, argument, ParsedTypeBasic.STRING, false);
            final ParsedCallArguments parsedArguments = new ParsedCallArguments(null, List.of(castedArgument));
            return new ParsedExpressionCall(this.position, method, parsedArguments);
        }
        
    }
    
    private final String name;
    private final Method resolver;
    private final MethodHandle validator;
    
    ValidatedEscapableBracketParser(final String name, final Method resolver, final MethodHandle validator) {
        
        this.name = name;
        this.resolver = resolver;
        this.validator = validator;
    }
    
    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        
        StringBuilder string = new StringBuilder();
        
        //This list will contain the BEP calls
        //If this is only a normal BEP, then it will contain exactly one String ParsedExpression.
        final List<ParsedExpression> expressionList = new ArrayList<>();
        
        while(tokens.optional(ZSTokenType.T_GREATER) == null) {
            ZSToken next = tokens.next();
            
            if(next.type != ZSTokenType.T_DOLLAR) {
                string.append(next.content);
                string.append(tokens.getLastWhitespace());
                continue;
            }
            
            //We found a $, now check that it has a { directly after it.
            final String ws = tokens.getLastWhitespace();
            if(!ws.isEmpty()) {
                //$  {..} is not ${..} so we print it as literal
                string.append(next.content).append(ws);
                continue;
            }
            
            next = tokens.next();
            //Now we check if it is a {
            if(next.type == ZSTokenType.T_AOPEN) {
                if(string.length() != 0) {
                    expressionList.add(new ParsedExpressionString(position, string.toString(), false));
                    string = new StringBuilder();
                }
                expressionList.add(ParsedExpression.parse(tokens));
                tokens.required(ZSTokenType.T_ACLOSE, "} expected.");
                string.append(tokens.getLastWhitespace());
            } else {
                //No { after the $, so we treat them both as literal
                string.append("$")
                        .append(ws); //Technically, the whitespace here is empty, but let's be sure
                string.append(next.content).append(tokens.getLastWhitespace());
            }
            
        }
        
        if(string.length() != 0) {
            expressionList.add(new ParsedExpressionString(position, string.toString(), false));
        }
        
        if(expressionList.isEmpty()) {
            expressionList.add(new ParsedExpressionString(position, "", false));
        }
        
        if(expressionList.size() == 1) {
            final ParsedExpression parsedExpression = expressionList.get(0);
            if(this.validator != null && parsedExpression instanceof ParsedExpressionString stringExpression) {
                final String value = stringExpression.value;
                boolean valid;
                try {
                    valid = (boolean) this.validator.invokeExact(value);
                } catch(final Throwable e) {
                    final String message = String.format("Invalid parameters to BEP %s: '<%s:%s>", this.name, this.name, value);
                    throw new ParseException(position, message, e);
                }
                
                if(!valid) {
                    final String format = String.format("Invalid parameters to BEP %s: '<%s:%s>. There may be more information about this in the log.", this.name, this.name, value);
                    throw new ParseException(position, format);
                }
            }
        }
        
        return new BracketResolverCallExpression(position, this.resolver, expressionList);
    }
    
}
