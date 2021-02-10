package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.event;

import com.blamejared.crafttweaker_annotation_processors.processors.document.NativeConversionRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.GenericParameterConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.static_member.StaticMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type.ImplementationConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type.SuperTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.native_registration.NativeRegistrationConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.native_registration.member.NativeTypeVirtualMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.ClassTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.DocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.EventPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.TypePage;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.EventHasResult;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

public class EventClassConverter extends NativeRegistrationConverter {
    
    public EventClassConverter(KnownModList knownModList, CommentConverter commentConverter, StaticMemberConverter staticMemberConverter, NativeTypeVirtualMemberConverter virtualMemberConverter, SuperTypeConverter superTypeConverter, ImplementationConverter implementationConverter, GenericParameterConverter genericParameterConverter, NativeConversionRegistry nativeConversionRegistry, Types typeUtils, ClassTypeConverter classTypeConverter) {
        
        super(knownModList, commentConverter, staticMemberConverter, virtualMemberConverter, superTypeConverter, implementationConverter, genericParameterConverter, nativeConversionRegistry, typeUtils, classTypeConverter);
    }
    
    @Override
    public boolean canConvert(TypeElement typeElement) {
        
        return super.canConvert(typeElement) && typeElement.getSimpleName().toString().endsWith("Event");
    }
    
    @Override
    public DocumentationPage convert(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        
        return new EventPage((TypePage) super.convert(typeElement, pageInfo), typeElement.getAnnotation(EventCancelable.class), typeElement
                .getAnnotation(EventHasResult.class));
    }
    
}
