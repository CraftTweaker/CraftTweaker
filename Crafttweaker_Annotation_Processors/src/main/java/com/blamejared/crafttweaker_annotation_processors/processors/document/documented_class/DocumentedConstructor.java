package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DocumentedConstructor {
    public static Comparator<? super DocumentedConstructor> compareByParameterCount = Comparator.comparingInt(value -> value.arguments
            .size());

    private final List<?> arguments = new ArrayList<>();

    public List<?> getArguments() {
        return arguments;
    }
}
