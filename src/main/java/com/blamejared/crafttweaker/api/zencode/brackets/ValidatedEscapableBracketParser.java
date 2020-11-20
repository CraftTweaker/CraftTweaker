package com.blamejared.crafttweaker.api.zencode.brackets;

import org.openzen.zencode.shared.*;
import org.openzen.zenscript.codemodel.*;
import org.openzen.zenscript.codemodel.expression.*;
import org.openzen.zenscript.codemodel.member.ref.*;
import org.openzen.zenscript.codemodel.partial.*;
import org.openzen.zenscript.codemodel.scope.*;
import org.openzen.zenscript.codemodel.type.*;
import org.openzen.zenscript.lexer.*;
import org.openzen.zenscript.parser.*;
import org.openzen.zenscript.parser.expression.*;

import java.lang.reflect.*;
import java.util.*;

public class ValidatedEscapableBracketParser implements BracketExpressionParser {
    
    private final FunctionalMemberRef method;
    private final Method validationMethod;
    private final String name;
    private final DefinitionTypeID targetType;
    
    public ValidatedEscapableBracketParser(String name, FunctionalMemberRef parserMethod, Method validationMethod, GlobalTypeRegistry registry) {
        this.method = parserMethod;
        this.validationMethod = validationMethod;
        this.name = name;
        targetType = registry.getForDefinition(parserMethod.getTarget().definition);
    }
    
    private static ParsedExpression createCallee(String methodFull, CodePosition position) {
        ParsedExpression expression = null;
        for(String s : methodFull.split("[.]")) {
            if(expression == null) {
                expression = new ParsedExpressionVariable(position, s, null);
            } else {
                expression = new ParsedExpressionMember(position, expression, s, null);
            }
        }
        
        return expression;
    }
    
    public String getName() {
        return name;
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
            if(validationMethod != null && parsedExpression instanceof ParsedExpressionString) {
                final String value = ((ParsedExpressionString) parsedExpression).value;
                boolean valid;
                try {
                    valid = (boolean) validationMethod.invoke(null, value);
                } catch(Exception e) {
                    final String message = String.format("Could not validate Parameters to BEP %s: '<%s:%s>%n%s", name, name, value, e);
                    return new ParsedExpressionInvalid(position, message);
                }
                
                if(!valid) {
                    final String format = String.format("Invalid parameters to BEP %s: '<%s:%s>. There may be more information about this in the log.", name, name, value);
                    return new ParsedExpressionInvalid(position, format);
                }
            }
        }
        
        return new StaticMethodCallExpression(position, expressionList);
    }
    
    private class StaticMethodCallExpression extends ParsedExpression {
        
        private final ParsedExpression call;
        
        public StaticMethodCallExpression(CodePosition position, List<ParsedExpression> expressions) {
            super(position);
            ParsedExpression p = null;
            for (ParsedExpression expression : expressions) {
                p = p == null ? expression : new ParsedExpressionBinary(expression.position, p, expression, OperatorType.ADD);
            }
            
            this.call = p;
        }
        
        @Override
        public IPartialExpression compile(ExpressionScope scope) throws CompileException {
            final Expression methodCall = call.compile(scope.withHint(BasicTypeID.STRING)).eval();
            final CallArguments arguments = new CallArguments(methodCall);
            return new CallStaticExpression(position, targetType, method, method.getHeader(), arguments);
        }
        
        @Override
        public boolean hasStrongType() {
            return true;
        }
    }
    
    public static final class ParsedExpressionInvalid extends ParsedExpression {
    
        private final String message;
    
        public ParsedExpressionInvalid(CodePosition position, String message) {
            super(position);
            this.message = message;
        }
    
        @Override
        public IPartialExpression compile(ExpressionScope scope) {
            return new InvalidExpression(position, BasicTypeID.UNDETERMINED, CompileExceptionCode.INVALID_BRACKET_EXPRESSION, message);
        }
    
        @Override
        public boolean hasStrongType() {
            return false;
        }
    }
}
