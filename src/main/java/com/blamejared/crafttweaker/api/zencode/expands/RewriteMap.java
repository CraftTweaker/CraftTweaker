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
import org.openzen.zenscript.parser.expression.ParsedExpressionMap;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("Duplicates")
public class RewriteMap implements BiFunction<ParsedExpressionMap, ExpressionScope, IPartialExpression> {
    
    @Override
    public IPartialExpression apply(ParsedExpressionMap parsedExpressionMap, ExpressionScope expressionScope) {
        final List<StoredType> hints = expressionScope.hints;
        if(hints.size() != 1) {
            return null;
        }
        
        TypeID typeID = hints.get(0).type;
        if(!(typeID instanceof DefinitionTypeID) || !((DefinitionTypeID) typeID).definition.name.equals("IData") && ((DefinitionTypeID) typeID).definition.name
                .equals("MapData")) {
            return null;
        }
        
        
        final CodePosition position = parsedExpressionMap.position;
        final DefinitionTypeID mapDataType = (DefinitionTypeID) expressionScope.getType(position, Arrays.asList(new GenericName("crafttweaker"), new GenericName("api"), new GenericName("data"), new GenericName("MapData")));
        final DefinitionTypeID iDataType = (DefinitionTypeID) expressionScope.getType(position, Arrays.asList(new GenericName("crafttweaker"), new GenericName("api"), new GenericName("data"), new GenericName("IData")));
        
        final Expression[] cKeys = new Expression[parsedExpressionMap.keys.size()];
        final Expression[] cValues = new Expression[parsedExpressionMap.values.size()];
        
        for(int i = 0; i < parsedExpressionMap.keys.size(); i++) {
            if(parsedExpressionMap.keys.get(i) == null)
                return null;
            
            try {
                cKeys[i] = parsedExpressionMap.keys.get(i)
                        .compileKey(expressionScope.withHint(StringTypeID.STATIC));
                cValues[i] = parsedExpressionMap.values.get(i).compile(expressionScope.withHint(iDataType.stored())).eval().castExplicit(position, expressionScope, iDataType.stored(), false);
            } catch(CompileException e) {
                return null;
            }
        }
        
        
        try {
            final MapExpression mapExpression = new MapExpression(position, cKeys, cValues, expressionScope.getTypeRegistry()
                    .getAssociative(StringTypeID.STATIC, iDataType.stored())
                    .stored());
            
            final CallArguments arguments = new CallArguments(mapExpression);
            final FunctionalMemberRef constructor = expressionScope.getTypeMembers(mapDataType.stored())
                    .getOrCreateGroup(OperatorType.CONSTRUCTOR)
                    .selectMethod(position, expressionScope, arguments, true, true);
            return new NewExpression(position, mapDataType.stored(), constructor, arguments);
        } catch(CompileException e) {
            return null;
        }
        
    }
}
