package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

import java.util.Collection;
import java.util.HashSet;

public class ExistingWrapperInfo extends WrapperInfo {


    private final String implementingClass;

    public ExistingWrapperInfo(String wrappedClass, String CrTClass, String implementingClass) {
        super(wrappedClass, CrTClass);
        this.implementingClass = implementingClass.isEmpty() ? CrTClass : implementingClass;
    }


    @Override
    public String formatWrapCall(String s) {
        if (wrappingFormat == null || wrappingFormat.isEmpty()) {
            return String.format("new %s(%s)", getImplementingClassName(), s);
        } else {
            return String.format(wrappingFormat, "(" + s + ")");
        }
    }

    @Override
    public Collection<String> getImport() {
        final HashSet<String> imp = new HashSet<>(super.getImport());
        imp.add("import " + implementingClass + ";");
        return imp;
    }

    public String getImplementingClassName() {
        return implementingClass.substring(implementingClass.lastIndexOf('.') + 1);
    }
}
