package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

import java.util.Collection;
import java.util.HashSet;

public class CollectionWrapperInfo extends WrapperInfo {
    private final String collectionClass;
    private final String usedClass;
    private final WrapperInfo elements;

    public CollectionWrapperInfo(String collectionClass, String usedClass, WrapperInfo elements) {
        super(collectionClass + "<" + elements.getWrappedClass() + ">", collectionClass + "<" + elements.getCrTQualifiedName() + ">", null, null);
        this.collectionClass = collectionClass;
        this.usedClass = usedClass;
        this.elements = elements;

        final String name = "myStrangeType" + elements.getCrTClassName();
        this.setWrappingFormat(String.format("%%s.stream().map(%s -> %s).collect(java.util.stream.Collectors.toCollection(%s::new));", name, elements
                .formatWrapCall(name), usedClass));
        this.setUnWrappingFormat(String.format("%%s.stream().map(%s -> %s).collect(java.util.stream.Collectors.toCollection(%s::new));", name, elements
                .formatUnwrapCall(name), usedClass));

        //Arrays.stream(new String[]{}).collect(Collectors.toCollection(() -> new ArrayList<>()))
    }

    @Override
    public Collection<String> getImport() {
        final Collection<String> anImport = new HashSet<>(elements.getImport());
        anImport.add("import " + usedClass + ";");
        anImport.add("import " + collectionClass + ";");
        return anImport;
    }
}
