package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

import java.io.Serializable;

public class WrapperInfo implements Serializable {
    private final String wrappedClass;
    private final String CrTQualifiedName;
    private final String zcName;
    private final String docsPath;
    private String wrappingFormat = null; //MC -> CrT
    private String unWrappingFormat = null; //CrT -> MC

    public WrapperInfo(String wrappedClass, String crTQualifiedName, String zcName, String docsPath) {
        this.wrappedClass = wrappedClass;
        this.CrTQualifiedName = crTQualifiedName == null ? wrappedClass : crTQualifiedName;
        this.docsPath = docsPath;
        this.zcName = zcName;
    }

    public WrapperInfo(String wrappedClass) {
        this(wrappedClass, wrappedClass);
    }

    public WrapperInfo(String wrappedClass, String CrTQualifiedName) {
        this(wrappedClass, CrTQualifiedName, null);
    }

    public WrapperInfo(String wrappedClass, String CrTQualifiedName, String docsPath) {
        this(wrappedClass, CrTQualifiedName, CrTQualifiedName, docsPath);
    }

    public WrapperInfo(String[] strings) {
        this(strings[0], strings.length > 1 ? strings[1] : null, strings.length > 2 ? strings[2] : null);
    }

    public String getCrTPackage() {
        if (!CrTQualifiedName.contains(".")) {
            return "";
        }
        return CrTQualifiedName.substring(0, CrTQualifiedName.lastIndexOf('.'));
    }

    public String getCrTClassName() {
        return CrTQualifiedName.substring(CrTQualifiedName.lastIndexOf('.') + 1);
    }

    public String getWrappedClass() {
        return wrappedClass;
    }

    public String getCrTQualifiedName() {
        return CrTQualifiedName;
    }

    public String getZcName() {
        return zcName;
    }

    public String getDocsPath() {
        return docsPath;
    }


    public String formatWrapCall(String s) {
        if (wrappingFormat == null || wrappingFormat.isEmpty()) {
            return String.format("new %s(%s)", getCrTQualifiedName(), s);
        } else {
            return String.format(wrappingFormat, "(" + s + ")");
        }
    }

    public String formatUnwrapCall(String s) {
        if (unWrappingFormat == null || unWrappingFormat.isEmpty()) {
            return String.format("(%s).getInternal()", s);
        } else {
            return String.format(unWrappingFormat, "(" + s + ")");
        }
    }

    public void setWrappingFormat(String wrappingFormat) {
        this.wrappingFormat = wrappingFormat;
    }

    public void setUnWrappingFormat(String unWrappingFormat) {
        this.unWrappingFormat = unWrappingFormat;
    }

    public String getWrappingFormat() {
        return wrappingFormat;
    }

    public String getUnWrappingFormat() {
        return unWrappingFormat;
    }
}
