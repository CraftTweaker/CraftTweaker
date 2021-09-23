package com.blamejared.crafttweaker_annotation_processors.processors.document.native_types;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.ExistingTypeInfo;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaLangNativeTypeProvider implements NativeTypeProvider {
    
    private final Map<TypeElement, AbstractTypeInfo> typeInfos = new HashMap<>();
    private final Elements elementUtils;
    
    public JavaLangNativeTypeProvider(Elements elementUtils) {
        
        this.elementUtils = elementUtils;
        addNativeClasses();
    }
    
    @Override
    public Map<TypeElement, AbstractTypeInfo> getTypeInfos() {
        
        return typeInfos;
    }
    
    private void addNativeClasses() {
        
        addClass(Collection.class, "Collection");
        addClass(Set.class, "Set");
    }
    
    private void addClass(Class<?> cls, String stdLibName) {
        
        final TypeElement typeElement = getElementFor(cls);
        final AbstractTypeInfo typeInfo = getTypeInfoFor(stdLibName);
        typeInfos.put(typeElement, typeInfo);
    }
    
    private TypeElement getElementFor(Class<?> cls) {
        
        return elementUtils.getTypeElement(cls.getCanonicalName());
    }
    
    private AbstractTypeInfo getTypeInfoFor(String stdLibName) {
        
        return new ExistingTypeInfo(stdLibName);
    }
    
}
