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

        final boolean isEvent = environment.getTypeUtils()
                .isSubtype(typeElement.asType(), environment.getElementUtils()
                        .getTypeElement("net.minecraftforge.eventbus.api.Event")
                        .asType());

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
        if (isEvent) {
            collect.add("import net.minecraftforge.eventbus.api.Event;");
            collect.add("import com.blamejared.crafttweaker.api.events.IEvent;");
            collect.add("import java.util.function.Consumer;");
        }

        collect.stream()
                .sorted()
                .forEach(writer::println);
        writer.println();


        writer.println("@ZenRegister");
        writer.printf("@ZenCodeType.Name(\"%s\")%n", info.getZcName());
        writer.printf("@Document(\"%s\")%n", info.getDocsPath());
        writer.printf("@ZenWrapper(wrappedClass = \"%s\", conversionMethodFormat = \"%%s.getInternal()\", displayStringFormat = \"%%s.toString()\")%n", info
                .getWrappedClass());
        if (!isEvent) {
            writer.printf("public class %s {%n", info.getCrTClassName());
            writer.printf("    private final %s internal;%n", info.getWrappedClassName());
            writer.println();
            writer.printf("    public %s(%s internal){%n", info.getCrTClassName(), info.getWrappedClassName());
            writer.println("        this.internal = internal;");
            writer.println("    }");
            writer.println();
            writer.printf("    public %s getInternal() {%n", info.getWrappedClassName());
            writer.println("        return this.internal;");
            writer.println("    }");
        } else {
            writer.printf("public class %s extends IEvent<%1$s, %s> {%n", info.getCrTClassName(), info.getWrappedClassName());
            writer.println();
            writer.printf("    public %s(%s internal){%n", info.getCrTClassName(), info.getWrappedClassName());
            writer.println("        super(internal);");
            writer.println("    }");
            writer.println();

            writer.println("    @ZenCodeType.Constructor");
            writer.printf("    public %s(Consumer<%1$s> handler) {%n", info.getCrTClassName());
            writer.println("        super(handler);");
            writer.println("    }");
            writer.println();

            writer.println("    @Override");
            writer.printf("    public Consumer<%s> getConsumer() {%n", info.getWrappedClassName());
            final String name = "myStrangeType" + info.getCrTClassName();
            writer.printf("        return %s -> getHandler().accept(new %s(%1$s));%n", name, info.getCrTClassName());
            writer.println("    }");
        }

        writer.println();


        for (WrappedMemberInformation wrappedMember : wrappedMembers) {
            String string = wrappedMember.getString();
            writer.println(string);
            writer.println();
        }

        writer.println("}");
    }


    public static Set<WrappedMemberInformation> getMembers(TypeElement typeElement, ProcessingEnvironment environment) {
        final Set<WrappedMemberInformation> out = new HashSet<>();
        for (Element enclosedElement : environment.getElementUtils().getAllMembers(typeElement)) {

            if (enclosedElement.getEnclosingElement().asType().toString().startsWith("java.")) {
                continue;
            }

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
