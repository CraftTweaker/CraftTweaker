package com.blamejared.crafttweaker_annotation_processors.processors.wrapper;

import com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information.WrapperInfo;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WrappedClass {

    public static void write(PrintWriter writer, WrapperInfo info, ProcessingEnvironment environment) {
        final TypeElement typeElement = environment.getElementUtils().getTypeElement(info.getWrappedClass());
        final Set<WrappedMemberInformation> wrappedMembers = getMembers(typeElement, environment);

        writer.printf("package %s;%n", info.getCrTPackage());
        writer.println();
        final Set<String> collect = wrappedMembers.stream()
                .flatMap(in -> in.getImports().stream())
                .collect(Collectors.toSet());

        collect.add("import com.blamejared.crafttweaker.api.annotations.ZenRegister;");
        collect.add("import org.openzen.zencode.java.ZenCodeType;");
        collect.add("import com.blamejared.crafttweaker_annotations.annotations.Document;");
        collect.add("import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;");
        collect.add("import " + info.getWrappedClass() + ";");

        collect.stream()
                .sorted()
                .forEach(writer::println);
        writer.println();


        writer.println("@ZenRegister");
        writer.printf("@ZenCodeType.Name(\"%s\")%n", info.getZcName());
        writer.printf("@Document(\"%s\")%n", info.getDocsPath());
        writer.printf("@ZenWrapper(wrappedClass = \"%s\", conversionMethodFormat = \"%%s.getInternal()\", displayStringFormat = \"%%s.toString()\")%n", info
                .getWrappedClass());
        writer.printf("public class %s {%n", info.getCrTClassName());

        writer.printf("    private final %s internal;%n", info.getWrappedClassName());
        writer.println();
        writer.printf("    public %s(%s internal){%n", info.getCrTClassName(), info.getWrappedClassName());
        writer.println("        this.internal = internal;");
        writer.println("    }");


        for (WrappedMemberInformation wrappedMember : wrappedMembers) {
            String string = wrappedMember.getString();
            writer.println(string);
            writer.println();
        }

        writer.println("}");
    }


    public static Set<WrappedMemberInformation> getMembers(TypeElement typeElement, ProcessingEnvironment environment) {
        final Set<WrappedMemberInformation> out = new HashSet<>();
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (!enclosedElement.getModifiers().contains(Modifier.PUBLIC)) {
                continue;
            }

            if (enclosedElement.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }

            if (enclosedElement.getKind() == ElementKind.METHOD) {
                final ExecutableElement executableElement = (ExecutableElement) enclosedElement;
                if (!executableElement.getThrownTypes().isEmpty()) {
                    //not gonna deal with that just yet...
                    continue;
                }
                final WrappedMethodMemberInformation information = WrappedMethodMemberInformation.getInformation(executableElement, environment);
                if (information != null) {
                    out.add(information);
                }
            }
        }
        return out;
    }

}
