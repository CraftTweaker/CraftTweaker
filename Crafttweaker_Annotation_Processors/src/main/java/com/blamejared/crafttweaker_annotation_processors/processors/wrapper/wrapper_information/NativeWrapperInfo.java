package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

public class NativeWrapperInfo extends WrapperInfo{

    public NativeWrapperInfo(String wrappedClass) {
        super(wrappedClass, wrappedClass, wrappedClass, null);

        this.setWrappingFormat("%s");
        this.setUnWrappingFormat("%s");
    }
}
