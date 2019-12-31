package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

public class ArrayWrapperInfo extends WrapperInfo {
    public ArrayWrapperInfo(WrapperInfo element) {
        super(element.getWrappedClass() + "[]", element.getCrTQualifiedName() + "[]", null, null);

        final String name = "myStrangeType" + element.getCrTClassName();
        this.setWrappingFormat(String.format("Arrays.stream(%%s).map(%s -> %s).toArray(%s::new);", name, element.formatWrapCall(name), getCrTQualifiedName()));
        this.setUnWrappingFormat(String.format("Arrays.stream(%%s).map(%s -> %s).toArray(%s::new);", name, element.formatUnwrapCall(name), getWrappedClass()));
    }
}
