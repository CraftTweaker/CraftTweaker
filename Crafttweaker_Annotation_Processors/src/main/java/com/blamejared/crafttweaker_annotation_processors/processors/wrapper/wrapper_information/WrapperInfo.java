package com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class WrapperInfo implements Serializable {
    private final String wrappedClass;
    private final String CrTQualifiedName;
    private final String zcName;
    private final String docsPath;
    protected String wrappingFormat = null; //MC -> CrT
    protected String unWrappingFormat = null; //CrT -> MC

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

    public static WrapperInfo create(String[] strings) {
        final String wrappedClass, CrTName, ZCName, docPath;

        if (strings.length == 0) {
            return null;
        }

        wrappedClass = strings[0];
        if (wrappedClass.isEmpty()) {
            return null;
        }

        if (strings.length > 1 && !strings[1].isEmpty()) {
            CrTName = strings[1];
        } else {
            final String crTName = wrappedClass.replaceFirst("net\\.minecraft(?:forge)?", "com.blamejared.crafttweaker.impl");
            CrTName = crTName.substring(0, crTName.lastIndexOf('.') + 1) + "MC" + crTName.substring(crTName.lastIndexOf('.') + 1);
        }

        if (strings.length > 2 && !strings[2].isEmpty()) {
            ZCName = strings[2];
        } else {
            ZCName = CrTName.replaceFirst("com\\.blamejared\\.crafttweaker\\.(?:impl|api)", "crafttweaker.api");
        }

        if (strings.length > 3 && !strings[3].isEmpty()) {
            docPath = strings[3];
        } else {
            docPath = ZCName.replaceFirst("crafttweaker\\.api\\.", "vanilla/api/").replace('.', '/');
        }

        return new WrapperInfo(wrappedClass, CrTName, ZCName, docPath);
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

    public String getWrappedClassName() {
        return wrappedClass.substring(wrappedClass.lastIndexOf('.') + 1);
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
            return String.format("new %s(%s)", getCrTClassName(), s);
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

    public String getWrappingFormat() {
        return wrappingFormat;
    }

    public void setWrappingFormat(String wrappingFormat) {
        this.wrappingFormat = wrappingFormat;
    }

    public String getUnWrappingFormat() {
        return unWrappingFormat;
    }

    public void setUnWrappingFormat(String unWrappingFormat) {
        this.unWrappingFormat = unWrappingFormat;
    }

    public Collection<String> getImport() {
        return Arrays.asList(
                "import " + getCrTQualifiedName() + ";",
                "import " + getWrappedClass() + ";"
        );
    }

    public File getFile(File parent) {
        return new File(parent, CrTQualifiedName.replace('.', File.separatorChar) + ".java");
    }
}
