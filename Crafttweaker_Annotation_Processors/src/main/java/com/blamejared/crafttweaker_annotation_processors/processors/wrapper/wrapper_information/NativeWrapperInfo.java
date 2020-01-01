package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

import java.util.Collection;
import java.util.Collections;

public class NativeWrapperInfo extends WrapperInfo {

    public NativeWrapperInfo(String wrappedClass) {
        super(wrappedClass, wrappedClass, wrappedClass, null);

        this.setWrappingFormat("%s");
        this.setUnWrappingFormat("%s");
    }

    @Override
    public Collection<String> getImport() {
        return Collections.emptySet();
    }
}
