package com.blamejared.crafttweaker_annotation_processors.processors.document_again.native_types.dependency_rule;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.*;

import javax.lang.model.element.*;
import java.util.*;

public interface ModDependencyConversionRule {
    Map<TypeElement, AbstractTypeInfo> getAll();
}
