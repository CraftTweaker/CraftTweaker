package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;

public interface NameConversionRule {
    @Nullable
    TypeMirror convertZenCodeName(String zenCodeName);
}
