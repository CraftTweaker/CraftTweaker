package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

import java.util.*;

public class ArrayWrapperInfo extends WrapperInfo {
    private final WrapperInfo element;

    public ArrayWrapperInfo(WrapperInfo element) {
        super(element.getWrappedClass() + "[]", element.getCrTQualifiedName() + "[]", null, null);
        this.element = element;

        final String name = "myStrangeType" + element.getCrTClassName();
        this.setWrappingFormat(String.format("Arrays.stream(%%s).map(%s -> %s).toArray(%s::new)", name, element.formatWrapCall(name), getCrTQualifiedName()));
        this.setUnWrappingFormat(String.format("Arrays.stream(%%s).map(%s -> %s).toArray(%s::new)", name, element.formatUnwrapCall(name), getWrappedClass()));
    }

    @Override
    public Collection<String> getImport() {
        final List<String> imports = new ArrayList<>(element.getImport());
        imports.add("import java.util.Arrays;");
        return imports;
    }
}
