package com.blamejared.crafttweaker_annotation_processors.processors.wrapper;

import com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information.WrapperInfo;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;
import java.util.StringJoiner;

public class WrappedMethodInformation {


    public static void writeMethod(PrintWriter writer, ExecutableElement method, ProcessingEnvironment environment) {

        final TypeMirror returnType = method.getReturnType();


        final boolean returnsVoid = returnType.getKind() == TypeKind.VOID;
        final boolean leaveReturnType = returnType.getKind().isPrimitive();

        final WrapperInfo retTypeWrapperInfo;
        if (returnsVoid || leaveReturnType) {
            retTypeWrapperInfo = null;
        } else {
            retTypeWrapperInfo = WrapperProcessor.getWrapperInfoFor(returnType, environment);
            if (retTypeWrapperInfo == null) {
                return;
            }
        }


        final StringJoiner parameterBuilder = new StringJoiner(", ", "(", ")");
        final StringJoiner internalCallBuilder = new StringJoiner(", ", "(", ")");

        for (VariableElement parameter : method.getParameters()) {
            final TypeMirror paramTypeMirror = parameter.asType();
            if (paramTypeMirror.getKind().isPrimitive()) {
                parameterBuilder.add(paramTypeMirror + " " + parameter.getSimpleName());
                internalCallBuilder.add(parameter.getSimpleName());
                continue;
            }


            final WrapperInfo paramWrapperInfo = WrapperProcessor.getWrapperInfoFor(paramTypeMirror, environment);
            if (paramWrapperInfo == null) {
                return;
            }

            parameterBuilder.add(paramWrapperInfo.getCrTQualifiedName() + " " + parameter.getSimpleName());
            internalCallBuilder.add(paramWrapperInfo.formatUnwrapCall(parameter.getSimpleName().toString()));
        }


        final StringBuilder outBuilder = new StringBuilder();
        final String s = AdvancedDocCommentUtil.safeGetDocComment(environment, method);
        if (s != null) {
            outBuilder.append(String.format("    /**%n%s%n*/%n", s));
        }
        outBuilder.append("    @org.openzen.zencode.java.ZenCodeType.Method").append(System.lineSeparator());
        outBuilder.append("    public ");
        outBuilder.append(leaveReturnType || returnsVoid ? returnType : retTypeWrapperInfo.getCrTQualifiedName()).append(" ");
        outBuilder.append(method.getSimpleName());
        outBuilder.append(parameterBuilder.toString()).append(" {").append(System.lineSeparator());

        if (returnsVoid || leaveReturnType) {
            outBuilder.append(returnsVoid ? "        internal." : "        return internal.")
                    .append(method.getSimpleName())
                    .append(internalCallBuilder.toString())
                    .append(";")
                    .append(System.lineSeparator());
        } else {
            final String innerCall = String.format("internal.%s%s", method.getSimpleName(), internalCallBuilder.toString());
            outBuilder.append("        return ").append(retTypeWrapperInfo.formatWrapCall(innerCall)).append(";");
            outBuilder.append(System.lineSeparator());
        }

        outBuilder.append("    }").append(System.lineSeparator());

        writer.println(outBuilder.toString());
    }
}
