package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CollectionWrapperInfo extends WrapperInfo {
    public CollectionWrapperInfo(String collectionClass, String usedClass, WrapperInfo elements) {
        super(collectionClass + "<" + elements.getWrappedClass() + ">", collectionClass + "<" + elements.getCrTQualifiedName() + ">", null, null);

        final String name = "myStrangeType" + elements.getCrTClassName();
        this.setWrappingFormat(String.format("%%s.stream().map(%s -> %s).collect(java.util.stream.Collectors.toCollection(%s::new));", name, elements.formatWrapCall(name), usedClass));
        this.setUnWrappingFormat(String.format("%%s.stream().map(%s -> %s).collect(java.util.stream.Collectors.toCollection(%s::new));", name, elements.formatUnwrapCall(name), usedClass));

        //Arrays.stream(new String[]{}).collect(Collectors.toCollection(() -> new ArrayList<>()))
    }
}
