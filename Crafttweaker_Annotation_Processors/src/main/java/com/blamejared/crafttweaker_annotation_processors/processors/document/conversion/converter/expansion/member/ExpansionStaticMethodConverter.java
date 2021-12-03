package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.expansion.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ReturnTypeInfoReader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.StaticMethodMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class ExpansionStaticMethodConverter {
    private final CommentConverter commentConverter;
    private final HeaderConverter headerConverter;
    private final ReturnTypeInfoReader returnTypeInfoReader;

    public ExpansionStaticMethodConverter(CommentConverter commentConverter, HeaderConverter headerConverter, ReturnTypeInfoReader returnTypeInfoReader) {
        this.commentConverter = commentConverter;
        this.headerConverter = headerConverter;
        this.returnTypeInfoReader = returnTypeInfoReader;
    }

    public void convertAndAddTo(Element enclosingElement, DocumentedStaticMembers result, DocumentationPageInfo expansionTypeInfo, AbstractTypeInfo expandedTypeInfo) {

        for (Element enclosedElement : enclosingElement.getEnclosedElements()) {
            if (canConvert(enclosedElement)) {
                final StaticMethodMember staticMethodMember = convertStaticMethodMember(enclosedElement, expansionTypeInfo);
                result.addMethod(staticMethodMember, expandedTypeInfo);
            }
        }
    }

    private StaticMethodMember convertStaticMethodMember(Element enclosedElement, DocumentationPageInfo expandedTypeInfo) {

        ExecutableElement method = (ExecutableElement) enclosedElement;
        return convertMethodMember(method, expandedTypeInfo);
    }

    private StaticMethodMember convertMethodMember(ExecutableElement method, DocumentationPageInfo pageInfo) {

        final String name = getName(method);
        final MemberHeader header = getHeader(method);
        final DocumentationComment comment = getComment(method, pageInfo);
        final String returnTypeInfo = getReturnTypeInfo(method);

        return new StaticMethodMember(name, header, comment, returnTypeInfo);
    }

    private boolean canConvert(Element enclosedElement) {
        return enclosedElement.getAnnotation(ZenCodeType.StaticExpansionMethod.class) != null;
    }

    private MemberHeader getHeader(ExecutableElement enclosedElement) {

        final List<? extends VariableElement> parameters = getParameters(enclosedElement);
        final List<? extends TypeParameterElement> typeParameters = getTypeParameters(enclosedElement);
        final TypeMirror returnType = getReturnType(enclosedElement);
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }

    private List<? extends VariableElement> getParameters(ExecutableElement enclosedElement) {

        return enclosedElement.getParameters();
    }

    private List<? extends TypeParameterElement> getTypeParameters(ExecutableElement enclosedElement) {

        return enclosedElement.getTypeParameters();
    }

    private TypeMirror getReturnType(ExecutableElement enclosedElement) {

        return enclosedElement.getReturnType();
    }

    private DocumentationComment getComment(ExecutableElement enclosedElement, DocumentationPageInfo pageInfo) {

        return commentConverter.convertForMethod(enclosedElement, pageInfo);
    }


    @Nullable
    private String getReturnTypeInfo(ExecutableElement method) {

        return returnTypeInfoReader.readForMethod(method).orElse(null);
    }

    private String getName(ExecutableElement method) {

        String customMethodName = method.getAnnotation(ZenCodeType.StaticExpansionMethod.class).value();
        return customMethodName.isEmpty() ? method.getSimpleName().toString() : customMethodName;
    }

}
