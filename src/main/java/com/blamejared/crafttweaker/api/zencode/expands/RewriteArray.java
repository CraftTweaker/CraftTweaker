package com.blamejared.crafttweaker.api.zencode.expands;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.*;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.*;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionArray;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("Duplicates")
public class RewriteArray implements BiFunction<ParsedExpressionArray, ExpressionScope, IPartialExpression> {
    
    @Override
    public IPartialExpression apply(ParsedExpressionArray parsedExpressionArray, ExpressionScope expressionScope) {
        final List<StoredType> hints = expressionScope.hints;
        if(hints.size() != 1) {
            return null;
        }
    
        TypeID typeID = hints.get(0).type;
        if(!(typeID instanceof DefinitionTypeID) || !((DefinitionTypeID) typeID).definition.name.equals("IData") && ((DefinitionTypeID) typeID).definition.name.equals("ListData")) {
                    return null;
        }
    
        final CodePosition position = parsedExpressionArray.position;
        final DefinitionTypeID listType = (DefinitionTypeID) expressionScope.getType(position, Arrays.asList(new GenericName("crafttweaker"), new GenericName("api"), new GenericName("data"), new GenericName("ListData")));
        final DefinitionTypeID iDataType = (DefinitionTypeID) expressionScope.getType(position, Arrays.asList(new GenericName("crafttweaker"), new GenericName("api"), new GenericName("data"), new GenericName("IData")));
    
        final List<ParsedExpression> contents = parsedExpressionArray.contents;
        final Expression[] cContent = new Expression[contents.size()];
        for(int i = 0; i < contents.size(); i++) {
            ParsedExpression content = contents.get(i);
            try {
                cContent[i] = content.compile(expressionScope.withHint(iDataType.stored())).eval().castExplicit(position, expressionScope, iDataType.stored(), false);
            } catch(CompileException e) {
                return null;
            }
        }
    
        try {
            final ArrayExpression arrayExpression = new ArrayExpression(position, cContent, expressionScope.getTypeRegistry()
                    .getArray(iDataType.stored(), 1)
                    .stored());
        
            final CallArguments arguments = new CallArguments(arrayExpression);
            final FunctionalMemberRef constructor = expressionScope.getTypeMembers(listType.stored())
                    .getOrCreateGroup(OperatorType.CONSTRUCTOR)
                    .selectMethod(position, expressionScope, arguments, true, true);
            return new NewExpression(position, listType.stored(), constructor, arguments);
        } catch(CompileException e) {
            return null;
        }
    
    }
}
