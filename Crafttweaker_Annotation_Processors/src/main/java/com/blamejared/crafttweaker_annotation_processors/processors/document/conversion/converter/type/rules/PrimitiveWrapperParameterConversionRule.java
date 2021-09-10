package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.PrimitiveTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.IHasPostCreationCall;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author youyihj
 */
public class PrimitiveWrapperParameterConversionRule implements TypeConversionRule, IHasPostCreationCall {
    private final Map<String, String> primitiveWrappers = new HashMap<>();

    @Override
    public boolean canConvert(TypeMirror mirror) {
        return getPrimitiveType(mirror).isPresent();
    }

    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        return new PrimitiveTypeInfo(getPrimitiveType(mirror).orElse("") + "?");
    }

    @Override
    public void afterCreation() {
        addPrimitiveWrapper(Integer.class, "int");
        addPrimitiveWrapper(Short.class, "short");
        addPrimitiveWrapper(Byte.class, "byte");
        addPrimitiveWrapper(Long.class, "long");
        addPrimitiveWrapper(Boolean.class, "bool");
        addPrimitiveWrapper(Float.class, "float");
        addPrimitiveWrapper(Double.class, "double");
        addPrimitiveWrapper(Character.class, "char");
    }

    private void addPrimitiveWrapper(Class<?> clazz, String primitiveTypeName) {
        primitiveWrappers.put(clazz.getCanonicalName(), primitiveTypeName);
    }

    private Optional<String> getPrimitiveType(TypeMirror mirror) {
        return Optional.ofNullable(primitiveWrappers.get(mirror.toString()));
    }
}
