package com.blamejared.crafttweaker_annotation_processors.processors;

import com.blamejared.crafttweaker_annotations.annotations.AsIAction;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.AsIAction"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AsIActionProcessor extends AbstractProcessor {

	private String getNewMethodDescriptor(VariableElement variableElement) {

		if (variableElement.asType().getKind().isPrimitive())
			return variableElement.asType().toString();

		final String s = variableElement.asType().toString();
		if (s.equalsIgnoreCase("net.minecraft.item.ItemStack")) {
			return "com.blamejared.crafttweaker.api.item.IItemStack";
		} else if (s.equalsIgnoreCase("net.minecraft.item.crafting.Ingredient")) {
			return "com.blamejared.crafttweaker.api.item.IIngredient";
		}

		this.processingEnv.getMessager()
				.printMessage(Diagnostic.Kind.WARNING, "Could not find type " + s, variableElement);
		return s;
	}

	private String getConversionFormat(VariableElement variableElement) {
		final TypeMirror typeMirror = variableElement.asType();
		if (typeMirror.getKind().isPrimitive())
			return "%s";

		final String s = typeMirror.toString();
		if (s.equalsIgnoreCase("net.minecraft.item.crafting.Ingredient")) {
			return "%s.asVanillaIngredient()";
		}

		return "%s.getInternal()";
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


		if (annotations.isEmpty())
			return false;

		if (annotations.size() > 1) {
			this.processingEnv.getMessager()
					.printMessage(Diagnostic.Kind.ERROR, "More than one annotation type found?");
		}

		final Map<String, List<ExecutableElement>> classes = new HashMap<>();
		final TypeElement asActionAnnotationElement = annotations.iterator().next();
		for (Element element : roundEnv.getElementsAnnotatedWith(asActionAnnotationElement)) {
			//AsIAction is only applicable to methods so the cast is safe
			ExecutableElement method = (ExecutableElement) element;

			if(!method.getModifiers().contains(Modifier.STATIC)) {
				this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "AsIActionAnnotation requires the method to be static", method);
			}

			final Element enclosingElement = method.getEnclosingElement();
			final ZenCodeType.Name annotation = enclosingElement.getAnnotation(ZenCodeType.Name.class);
			if (annotation == null) {
				this.processingEnv.getMessager()
						.printMessage(Diagnostic.Kind.ERROR, "@Document requires a @ZenCodeType.Name on the declaring class " + enclosingElement, method);
				continue;
			}

			classes.computeIfAbsent(annotation.value(), ignored -> new ArrayList<>()).add(method);
		}

		try {
			writeClasses(classes);
		} catch (IOException e) {
			e.printStackTrace();
		}


		return false;
	}

	private void writeClasses(Map<String, List<ExecutableElement>> classes) throws IOException {
		for (Map.Entry<String, List<ExecutableElement>> stringListEntry : classes.entrySet()) {
			final String name = stringListEntry.getKey();
			final List<ExecutableElement> methods = stringListEntry.getValue();
			final String qualifiedName = ((QualifiedNameable) methods.get(0)
					.getEnclosingElement()).getQualifiedName() + "GeneratedCrt";

			final JavaFileObject sourceFile = this.processingEnv.getFiler()
					.createSourceFile(qualifiedName);

			final int lastDot = qualifiedName.lastIndexOf('.');

			try (final PrintWriter writer = new PrintWriter(sourceFile.openWriter())) {
				writer.printf("package %s;%n", qualifiedName.substring(0, lastDot));
				writer.println();

				writer.println("@com.blamejared.crafttweaker.api.annotations.ZenRegister");
				writer.println("@com.blamejared.crafttweaker_annotations.annotations.Document");
				writer.printf("@org.openzen.zencode.java.ZenCodeType.Name(\"%s\")%n", name);
				writer.printf("public class %s {", qualifiedName.substring(lastDot + 1));

				for (ExecutableElement method : methods) {
					writeMethod(method, writer);
				}

				writer.println("}");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeMethod(ExecutableElement method, PrintWriter writer) {
		final List<? extends VariableElement> parameters = method.getParameters();
		final AsIAction annotation = method.getAnnotation(AsIAction.class);


		StringBuilder sb = new StringBuilder();

		final String docComment = this.processingEnv.getElementUtils().getDocComment(method);
		if(docComment!= null) {
			sb.append("/**\n");
			sb.append(docComment);
			sb.append("*/\n");
		}

		sb.append("@org.openzen.zencode.java.ZenCodeType.Method\n");
		sb.append("public static void ");
		sb.append(annotation.methodName().isEmpty() ? method.getSimpleName() : annotation.methodName());
		sb.append("(");

		{
			final StringJoiner sj = new StringJoiner(", ");
			for (VariableElement parameter : parameters) {
				final String newMethodDescriptor = getNewMethodDescriptor(parameter);

				if (newMethodDescriptor == null) {
					this.processingEnv.getMessager()
							.printMessage(Diagnostic.Kind.ERROR, "Cannot auto-convert type " + parameter, parameter);
					return;
				}

				sj.add(newMethodDescriptor + " " + parameter.getSimpleName());
			}
			sb.append(sj.toString());
		}
		sb.append(") {\n");

		sb.append("\tcom.blamejared.crafttweaker.api.CraftTweakerAPI.apply(new com.blamejared.crafttweaker.api.actions.");
		sb.append(annotation.repeatable() ? "IRuntimeAction" : "IAction");
		sb.append("() {\n");

		sb.append("\t\t@java.lang.Override\n");
		sb.append("\t\tpublic void apply() {\n");
		sb.append("\t\t\t");
		sb.append(((QualifiedNameable) method.getEnclosingElement()).getQualifiedName().toString());
		sb.append(".").append(method.getSimpleName()).append("(");

		{
			final StringJoiner sj = new StringJoiner(", ");
			for (VariableElement parameter : parameters) {
				sj.add(String.format(getConversionFormat(parameter), parameter.getSimpleName()));
			}
			sb.append(sj.toString());
		}
		sb.append(");\n");
		sb.append("\t\t}\n\n");


		sb.append("\t\t@java.lang.Override\n");
		sb.append("\t\tpublic String describe() {\n");
		sb.append("\t\t\treturn String.format(\"");
		sb.append(annotation.logFormat()).append("\", ");
		{
			final StringJoiner sj = new StringJoiner(", ");
			for (VariableElement parameter : parameters) {
				sj.add(parameter.getSimpleName());
			}
			sb.append(sj.toString());
		}
		sb.append(");\n");
		sb.append("\t\t}\n");


		sb.append("\t});\n}");

		writer.println(sb.toString());
	}
}
