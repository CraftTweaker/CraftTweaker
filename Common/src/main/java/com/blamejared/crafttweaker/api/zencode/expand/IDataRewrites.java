package com.blamejared.crafttweaker.api.zencode.expand;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.GenericName;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.ArrayExpression;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.MapExpression;
import org.openzen.zenscript.codemodel.expression.NewExpression;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.DefinitionTypeID;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionArray;
import org.openzen.zenscript.parser.expression.ParsedExpressionMap;

import java.util.Arrays;
import java.util.List;

public class IDataRewrites {
    
    
    public static final List<GenericName> IDATA_NAME = Arrays.asList(new GenericName("crafttweaker"), new GenericName("api"), new GenericName("data"), new GenericName("IData"));
    public static final List<GenericName> LIST_DATA_NAME = Arrays.asList(new GenericName("crafttweaker"), new GenericName("api"), new GenericName("data"), new GenericName("ListData"));
    public static final List<GenericName> MAP_DATA_NAME = Arrays.asList(new GenericName("crafttweaker"), new GenericName("api"), new GenericName("data"), new GenericName("MapData"));
    
    
    public static IPartialExpression rewriteArray(ParsedExpressionArray parsedExpressionArray, ExpressionScope expressionScope) {
        
        final List<TypeID> hints = expressionScope.hints;
        if(hints.size() != 1) {
            return null;
        }
        
        TypeID typeID = hints.get(0);
        if(typeID.isOptional()) {
            typeID = typeID.withoutOptional();
        }
        if(!(typeID instanceof DefinitionTypeID definitionTypeID)) {
            return null;
        }
        
        final String name = definitionTypeID.definition.name;
        final boolean isListData = name.equals("ListData");
        if(!(isListData || name.equals("IData") || name.equals("ICollectionData"))) {
            return null;
        }
        
        final CodePosition position = parsedExpressionArray.position;
        final DefinitionTypeID iDataType = (DefinitionTypeID) expressionScope.getType(position, IDATA_NAME);
        final DefinitionTypeID listType = (DefinitionTypeID) expressionScope.getType(position, LIST_DATA_NAME);
        
        final List<ParsedExpression> contents = parsedExpressionArray.contents;
        final Expression[] cContent = new Expression[contents.size()];
        for(int i = 0; i < contents.size(); i++) {
            ParsedExpression content = contents.get(i);
            try {
                cContent[i] = content.compile(expressionScope.withHint(iDataType))
                        .eval()
                        .castExplicit(position, expressionScope, iDataType, false);
            } catch(CompileException e) {
                return null;
            }
        }
        
        try {
            final ArrayExpression arrayExpression = new ArrayExpression(position, cContent, expressionScope.getTypeRegistry()
                    .getArray(iDataType, 1));
            
            final CallArguments arguments = new CallArguments(arrayExpression);
            
            if(isListData) {
                final FunctionalMemberRef constructor = expressionScope.getTypeMembers(listType)
                        .getOrCreateGroup(OperatorType.CONSTRUCTOR)
                        .selectMethod(position, expressionScope, arguments, true, true);
                return new NewExpression(position, listType, constructor, arguments);
            } else {
                return expressionScope.getTypeMembers(iDataType)
                        .getGroup("listOf")
                        .callStatic(position, iDataType, expressionScope, arguments);
            }
        } catch(CompileException e) {
            return null;
        }
    }
    
    public static IPartialExpression rewriteMap(ParsedExpressionMap parsedExpressionMap, ExpressionScope expressionScope) {
        
        final List<TypeID> hints = expressionScope.hints;
        if(hints.size() != 1) {
            return null;
        }
        
        TypeID typeID = hints.get(0);
        if(typeID.isOptional()) {
            typeID = typeID.withoutOptional();
        }
        if(!(typeID instanceof DefinitionTypeID) || !(((DefinitionTypeID) typeID).definition.name.equals("IData") || ((DefinitionTypeID) typeID).definition.name.equals("MapData"))) {
            return null;
        }
        
        final CodePosition position = parsedExpressionMap.position;
        final DefinitionTypeID mapDataType = (DefinitionTypeID) expressionScope.getType(position, MAP_DATA_NAME);
        final DefinitionTypeID iDataType = (DefinitionTypeID) expressionScope.getType(position, IDATA_NAME);
        
        final Expression[] cKeys = new Expression[parsedExpressionMap.keys.size()];
        final Expression[] cValues = new Expression[parsedExpressionMap.values.size()];
        
        for(int i = 0; i < parsedExpressionMap.keys.size(); i++) {
            if(parsedExpressionMap.keys.get(i) == null) {
                return null;
            }
            
            try {
                cKeys[i] = parsedExpressionMap.keys.get(i)
                        .compileKey(expressionScope.withHint(BasicTypeID.STRING));
                cValues[i] = parsedExpressionMap.values.get(i)
                        .compile(expressionScope.withHint(iDataType))
                        .eval()
                        .castExplicit(position, expressionScope, iDataType, false);
            } catch(CompileException e) {
                return null;
            }
        }
        
        try {
            final MapExpression mapExpression = new MapExpression(position, cKeys, cValues, expressionScope.getTypeRegistry()
                    .getAssociative(BasicTypeID.STRING, iDataType));
            
            final CallArguments arguments = new CallArguments(mapExpression);
            final FunctionalMemberRef constructor = expressionScope.getTypeMembers(mapDataType)
                    .getOrCreateGroup(OperatorType.CONSTRUCTOR)
                    .selectMethod(position, expressionScope, arguments, true, true);
            return new NewExpression(position, mapDataType, constructor, arguments);
        } catch(CompileException e) {
            return null;
        }
        
    }
    
}
