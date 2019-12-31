package com.blamejared.crafttweaker_annotation_processors.processors.wrapper;

import com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information.WrapperInfo;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import java.io.PrintWriter;

public class WrappedClass {

    public static void write(PrintWriter writer, WrapperInfo info, ProcessingEnvironment environment) {
        final TypeElement typeElement = environment.getElementUtils().getTypeElement(info.getWrappedClass());

        writer.printf("package %s;%n", info.getCrTPackage());
        writer.println();
        writer.println("@com.blamejared.crafttweaker.api.annotations.ZenRegister");
        writer.printf("@org.openzen.zencode.java.ZenCodeType.Name(\"%s\")%n", info.getZcName());
        writer.printf("@com.blamejared.crafttweaker_annotations.annotations.Document(\"%s\")%n", info.getDocsPath());
        writer.printf("@ZenWrapper(wrappedClass = \"%s\", conversionMethodFormat = \"%%s.getInternal()\", displayStringFormat = \"%%s.toString()\")%n", info
                .getWrappedClass());
        writer.printf("public class %s {%n", info.getCrTClassName());

        writer.printf("    private final %s internal;%n", info.getWrappedClass());
        writer.println();
        writer.printf("    public %s(%s internal){%n", info.getCrTClassName(), info.getWrappedClass());
        writer.println("        this.internal = internal;");
        writer.println("    }");
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (!enclosedElement.getModifiers().contains(Modifier.PUBLIC)) {
                continue;
            }

            if(enclosedElement.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }

            if (enclosedElement.getKind() == ElementKind.METHOD) {
                WrappedMethodInformation.writeMethod(writer, (ExecutableElement) enclosedElement, environment);
            }
        }


        writer.println("}");
    }

}
