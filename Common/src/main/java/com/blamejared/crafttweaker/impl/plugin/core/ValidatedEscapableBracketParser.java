package com.blamejared.crafttweaker.impl.plugin.core;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.CallStaticExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSToken;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionBinary;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

final class ValidatedEscapableBracketParser implements BracketExpressionParser {
    
    private static final class StaticMethodCallExpression extends ParsedExpression {
        
        private final DefinitionTypeID id;
        private final FunctionalMemberRef targetMethod;
        private final ParsedExpression call;
        
        public StaticMethodCallExpression(final CodePosition position, final DefinitionTypeID id, final FunctionalMemberRef targetMethod, final List<ParsedExpression> expressions) {
            
            super(position);
            this.id = id;
            this.targetMethod = targetMethod;
            this.call = expressions.stream()
                    .skip(1L)
                    .reduce(expressions.get(0), (a, b) -> new ParsedExpressionBinary(b.position, a, b, OperatorType.ADD));
        }
        
        @Override
        public IPartialExpression compile(ExpressionScope scope) throws CompileException {
            
            final Expression methodCall = this.call.compile(scope.withHint(BasicTypeID.STRING)).eval();
            final CallArguments arguments = new CallArguments(methodCall);
            return new CallStaticExpression(this.position, this.id, this.targetMethod, this.targetMethod.getHeader(), arguments);
        }
        
        @Override
        public boolean hasStrongType() {
            
            return true;
        }
        
    }
    
    private final FunctionalMemberRef method;
    private final MethodHandle validationMethod;
    private final String name;
    private final DefinitionTypeID targetType;
    
    ValidatedEscapableBracketParser(final String name, final FunctionalMemberRef parserMethod, final MethodHandle validationMethod, final GlobalTypeRegistry registry) {
        
        this.method = parserMethod;
        this.validationMethod = validationMethod;
        this.name = name;
        this.targetType = registry.getForDefinition(parserMethod.getTarget().definition);
    }
    
    public String getName() {
        
        return this.name;
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        
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
            if(this.validationMethod != null && parsedExpression instanceof ParsedExpressionString stringExpression) {
                final String value = stringExpression.value;
                boolean valid;
                try {
                    valid = (boolean) this.validationMethod.invokeExact(value);
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
        
        return new StaticMethodCallExpression(position, this.targetType, this.method, expressionList);
    }
    
}
