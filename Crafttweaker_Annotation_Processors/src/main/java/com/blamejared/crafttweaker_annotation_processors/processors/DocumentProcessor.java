package com.blamejared.crafttweaker_annotation_processors.processors;

import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessor extends AbstractProcessor {

	private static boolean firstCall = true;

	/**
	 * Creates a directory or deletes all files if it already exists
	 */
	private static void createDir(Path docsDir, Messager messager) {
		if(Files.exists(docsDir)) {
			try {
				Files.walkFileTree(docsDir, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
			}
		}


		try {
			messager.printMessage(Diagnostic.Kind.NOTE, "Creating Folder " + docsDir);
			Files.createDirectories(docsDir);
		} catch (IOException e) {
			messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		final File absoluteFile = new File("build/mkdocs").getAbsoluteFile();
		final Path docsDir = absoluteFile.toPath();
		final Messager messager = this.processingEnv.getMessager();

		if(firstCall) {
			createDir(docsDir, messager);
			firstCall = false;
		}

		//This outer loop is kinda redundant, cause there only exists one annotation matching the supported types
		for (TypeElement annotation : annotations) {

			//Every element that was annotated
			for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
				//For now we only need the classes and interfaces, we'll check the members later
				if (!element.getKind().isClass() && !element.getKind().isInterface()) {
					continue;
				}

				//At this point it is either a class or an interface
				final TypeElement typeElement = (TypeElement) element;

				if (typeElement.getAnnotation(ZenCodeType.Name.class) == null && typeElement.getAnnotation(ZenCodeType.Expansion.class) == null) {
					messager.printMessage(Diagnostic.Kind.ERROR, String.format(Locale.ENGLISH, "@Document requires either @ZenCodeType.Name or @ZenCodeType.Expansion to be present. Class %s has neither", typeElement
							.getQualifiedName()), typeElement);
				}

				final List<Class<? extends Annotation>> annotationsToCheck = new ArrayList<>();

				annotationsToCheck.add(ZenCodeType.Method.class);
				annotationsToCheck.add(ZenCodeType.Setter.class);
				annotationsToCheck.add(ZenCodeType.Getter.class);
				annotationsToCheck.add(ZenCodeType.Operator.class);
				annotationsToCheck.add(ZenCodeType.Field.class);
				annotationsToCheck.add(ZenCodeType.Caster.class);

				final Map<Class<? extends Annotation>, List<Element>> annotatedMembers = new HashMap<>();
				for (Class<? extends Annotation> zenAnnotation : annotationsToCheck) {
					annotatedMembers.put(zenAnnotation, new ArrayList<>());
				}

				for (Element enclosedElement : typeElement.getEnclosedElements()) {
					for (Class<? extends Annotation> zenAnnotation : annotationsToCheck) {
						if (enclosedElement.getAnnotation(zenAnnotation) != null) {
							annotatedMembers.get(zenAnnotation).add(enclosedElement);
						}
					}
				}

				writeFile(docsDir, messager, typeElement, annotatedMembers);
			}
		}

		return true;
	}

	private void writeFile(Path docsDir, Messager messager, TypeElement typeElement, Map<Class<? extends Annotation>, List<Element>> membersToWrite) {
		final Path fullPath;
		final ZenCodeType.Name annotation = typeElement.getAnnotation(ZenCodeType.Name.class);
		final boolean isClass = annotation != null;
		{
			final StringBuilder sb = new StringBuilder();
			(isClass ? annotation.value() : typeElement.getQualifiedName())
					.chars()
					.map(i -> i != '.' ? i : File.separatorChar)
					.forEach(i -> sb.append((char) i));
			fullPath = docsDir.resolve(sb.append(".md").toString());
		}


		if (!isClass && typeElement.getAnnotation(ZenCodeType.Expansion.class) == null) {
			messager.printMessage(Diagnostic.Kind.ERROR, String.format(Locale.ENGLISH, "@Document requires either @ZenCodeType.Name or @ZenCodeType.Expansion to be present. Class %s has neither, skipping class", typeElement
					.getQualifiedName()), typeElement);
			return;
		}

		try {
			if (Files.notExists(fullPath.getParent()))
				Files.createDirectories(fullPath.getParent());
			messager.printMessage(Diagnostic.Kind.NOTE, "Creating " + fullPath.toAbsolutePath());
			try (final PrintWriter writer = new PrintWriter(Files.newBufferedWriter(fullPath, StandardOpenOption.CREATE))) {

				writeHeaderAndDescription(messager, typeElement, isClass, writer);

				writeGetterSetter(membersToWrite, writer);

				writeOperators(membersToWrite, writer, !isClass);

				writeMethods(membersToWrite, writer, !isClass);

				writeCasters(membersToWrite, writer);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			messager.printMessage(Diagnostic.Kind.ERROR, e.toString(), typeElement);
		}
	}

	private void writeHeaderAndDescription(Messager messager, TypeElement typeElement, boolean isClass, PrintWriter writer) {
		//Header
		writer.printf("# %s%n%n", typeElement.getSimpleName());

		//Description
		final Document documentAnnotation = typeElement.getAnnotation(Document.class);
		if (documentAnnotation != null && !documentAnnotation.value().isEmpty()) {
			writer.println(documentAnnotation.value());
		} else {
			final String docComment = processingEnv.getElementUtils().getDocComment(typeElement);
			if (docComment != null) {
				writer.println(docComment);
			} else {
				messager.printMessage(Diagnostic.Kind.WARNING, typeElement.getQualifiedName() + " has no documentation!", typeElement);
				writer.println("No further info provided.");
			}
		}
		writer.println();
		writer.println();

		//Import / Extended Type
		if (isClass) {
			writer.println("## Importing the class");
			writer.println();
			writer.println("It might be required for you to import the package if you encounter any issues (like casting an [Array](/AdvancedFunctions/Arrays_and_Loops/)), so better be safe than sorry and add the import.  ");
			writer.printf("`import %s;`%n%n", typeElement.getAnnotation(ZenCodeType.Name.class).value());
		} else {
			final String expandedType = typeElement.getAnnotation(ZenCodeType.Expansion.class).value();
			final String expandedTypeName = expandedType.substring(expandedType.lastIndexOf('.') + 1);
			writer.println("## Expanding " + expandedTypeName);
			writer.printf("This class is an expansion to %s.  %nThat means that instead of providing methods on its own, it adds new methods to %s.  %nThese methods can be used on any %s object, as long as you have a reference to it.  %n You can use these functions as if they were defined on %s directly.%n%n",
					expandedType, expandedTypeName, expandedTypeName, expandedTypeName);
		}
	}

	private void writeGetterSetter(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer) {
		//Getter and Setter
		final Set<String> getterNames = new HashSet<>();
		final Set<String> setterNames = new HashSet<>();
		final Map<String, String> descriptions = new HashMap<>();

		for (Element element : membersToWrite.get(ZenCodeType.Setter.class)) {
			final String actualName;

			final String annotatedName = element.getAnnotation(ZenCodeType.Setter.class).value();
			if (!annotatedName.isEmpty()) {
				actualName = annotatedName;
				setterNames.add(annotatedName);
			} else {
				final String memberName = element.getSimpleName().toString();
				if (memberName.startsWith("set")) {
					actualName = memberName.substring(3, 4).toLowerCase() + memberName.substring(4);
					setterNames.add(actualName);
				} else {
					this.processingEnv.getMessager()
							.printMessage(Diagnostic.Kind.WARNING, "Skipping Setter " + element.toString(), element);
					continue;
				}
			}

			mergeGetterSetterDescriptions(descriptions, element, actualName);
		}

		for (Element element : membersToWrite.get(ZenCodeType.Getter.class)) {
			final String actualName;

			final String annotatedName = element.getAnnotation(ZenCodeType.Getter.class).value();
			if (!annotatedName.isEmpty()) {
				actualName = annotatedName;
				getterNames.add(annotatedName);
			} else {

				final String memberName = element.getSimpleName().toString();
				if (memberName.startsWith("get")) {
					actualName = memberName.substring(3, 4).toLowerCase() + memberName.substring(4);
					getterNames.add(actualName);
				} else if (memberName.startsWith("is") && element instanceof ExecutableElement && ((ExecutableElement) element)
						.getReturnType()
						.getKind() == TypeKind.BOOLEAN) {
					actualName = memberName.substring(2, 3).toLowerCase() + memberName.substring(3);
					getterNames.add(actualName);
				} else {
					this.processingEnv.getMessager()
							.printMessage(Diagnostic.Kind.WARNING, "Skipping Getter " + element.toString(), element);
					continue;
				}
			}
			mergeGetterSetterDescriptions(descriptions, element, actualName);
		}

		for (Element element : membersToWrite.get(ZenCodeType.Field.class)) {
			final String annotatedName = element.getAnnotation(ZenCodeType.Field.class).value();
			final String actualName = annotatedName.isEmpty() ? element.getSimpleName().toString() : annotatedName;
			getterNames.add(actualName);
			setterNames.add(actualName);

			mergeGetterSetterDescriptions(descriptions, element, actualName);
		}

		final SortedSet<String> settersAndGetters = new TreeSet<>(Comparator.naturalOrder());
		settersAndGetters.addAll(setterNames);
		settersAndGetters.addAll(getterNames);

		if (!settersAndGetters.isEmpty()) {
			writer.println("## Getters/Setters");
			writer.println();

			writer.println("| Name | HasGetter | HasSetter | Description |");
			writer.println("|------|-----------|-----------|-------------|");

			for (String name : settersAndGetters) {
				writer.printf("| %s | %b | %b | %s |%n", name, getterNames.contains(name), setterNames.contains(name), descriptions
						.getOrDefault(name, "No Information given.")
						.replaceAll("[\\n\\r]+", "<br>"));
			}
		}
	}

	private void writeOperators(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer, boolean isExtension) {
		final List<Element> elements = membersToWrite.get(ZenCodeType.Operator.class);
		if (elements.isEmpty())
			return;
		elements.sort(Comparator.comparing(e -> e.getAnnotation(ZenCodeType.Operator.class).value().name()));

		writer.println("## Operators");
		writer.println();

		for (Element element : elements) {
			final ZenCodeType.OperatorType operatorType = element.getAnnotation(ZenCodeType.Operator.class).value();

			final int operandCount = FormattingUtils.getOperandCountFor(operatorType);
			final int requiredMethodParameters = isExtension ? operandCount : operandCount - 1;
			final String operatorFormat = FormattingUtils.getOperatorFormat(operatorType);

			//@Operator can only be applied to methods
			final ExecutableElement method = (ExecutableElement) element;
			final List<? extends VariableElement> parameters = method.getParameters();
			if (parameters.size() != requiredMethodParameters) {
				if (isExtension) {
					this.processingEnv.getMessager()
							.printMessage(Diagnostic.Kind.ERROR, String.format("Operator %s requires %d operands as it is an extension method.", operatorType, requiredMethodParameters), method);
				} else {
					this.processingEnv.getMessager()
							.printMessage(Diagnostic.Kind.ERROR, String.format("Operator %s requires %d operands.", operatorType, requiredMethodParameters), method);
				}

				continue;
			}

			final String[] operandNames = new String[operandCount];
			final String[] operandTypes = new String[operandCount];
			final String[] operandDescriptions = new String[operandCount];


			fillMethodInfo(isExtension, operandCount, method, operandNames, operandTypes, operandDescriptions);


			writer.println("### " + operatorType.name());
			writer.println();
			writer.println("```zenscript");

			//noinspection RedundantCast
			writer.printf(operatorFormat, (Object[]) operandNames);
			writer.println();
			writer.println("```");

			writer.println();
			writer.println("| Parameter Name | Parameter Type | Description |");
			writer.println("|----------------|----------------|-------------|");

			for (int i = 1; i < operandCount; i++) {
				writer.printf("| %s | %s | %s |%n", operandNames[i], operandTypes[i], operandDescriptions[i]);
			}

		}
	}

	private void fillMethodInfo(boolean isExtension, int operandCount, ExecutableElement method, String[] operandNames, String[] operandTypes, String[] operandDescriptions) {
		{
			final Element argumentElement = isExtension ? method.getParameters()
					.get(0) : method.getEnclosingElement();
			final String simpleName = argumentElement.getSimpleName().toString();
			operandNames[0] = "my" + simpleName.substring(0, 1) + simpleName.substring(1);
			operandTypes[0] = FormattingUtils.convertTypeName(argumentElement.asType(), this.processingEnv.getTypeUtils());
			//Description of base type will never be printed so we dont need to set it to anything.
		}

		for (int i = 1; i < operandCount; i++) {
			final VariableElement argumentElement = method.getParameters().get(isExtension ? i : i - 1);
			operandNames[i] = argumentElement.getSimpleName().toString();
			operandTypes[i] = FormattingUtils.convertTypeName(argumentElement.asType(), this.processingEnv.getTypeUtils());

			String parentDoc;
			final Document docAnnotation = argumentElement.getEnclosingElement().getAnnotation(Document.class);
			if (docAnnotation != null) {
				parentDoc = docAnnotation.value();
			} else {
				parentDoc = this.processingEnv.getElementUtils()
						.getDocComment(argumentElement.getEnclosingElement());
				if (parentDoc == null)
					parentDoc = "";
			}

			final StringJoiner sb = new StringJoiner("<br>");
			boolean found = false;
			final String prefix = "@param " + operandNames[i];
			for (String s : parentDoc.split("\n")) {
				s = s.trim();
				if (s.isEmpty())
					continue;
				if (s.startsWith(prefix)) {
					found = true;
				} else if (s.startsWith("@")) {
					found = false;
				}

				if (found) {
					sb.add(s.substring(prefix.length() + 1));
				}
			}

			operandDescriptions[i] = sb.length() == 0 ? "No information given" : sb.toString();
		}
	}

	private void writeMethods(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer, boolean isExpansion) {
		final List<Element> elements = membersToWrite.get(ZenCodeType.Method.class);
		if (elements.isEmpty())
			return;

		writer.println("## Methods");
		writer.println();

		//New Map of overloaded method names -> methods
		final Map<String, List<ExecutableElement>> methodNamesToMethods = new HashMap<>();
		for (Element element : elements) {
			//@ZenCodeType.Method is only applicable to methods, so the cast is save.
			final ExecutableElement method = (ExecutableElement) element;
			String annotationValue = method.getAnnotation(ZenCodeType.Method.class).value();
			methodNamesToMethods.computeIfAbsent(annotationValue.isEmpty() ? method.getSimpleName()
					.toString() : annotationValue, s -> new ArrayList<>()).add(method);
		}

		for (Map.Entry<String, List<ExecutableElement>> stringListEntry : methodNamesToMethods.entrySet()) {
			writer.print("### " + stringListEntry.getKey());
			writer.println();

			for (ExecutableElement method : stringListEntry.getValue()) {

				final List<? extends VariableElement> parameters = method.getParameters();
				final int totalCount = isExpansion ? parameters.size() : parameters.size() + 1;

				final String[] names = new String[totalCount];
				final String[] types = new String[totalCount];
				final String[] descriptions = new String[totalCount];

				fillMethodInfo(isExpansion, totalCount, method, names, types, descriptions);


				{
					final String docComment = this.processingEnv.getElementUtils().getDocComment(method);
					final String description;
					if (docComment != null) {
						final StringJoiner sj = new StringJoiner("<br>");
						for (String s : docComment.split("\n")) {
							s = s.trim();
							if (s.isEmpty() || s.startsWith("@param"))
								continue;
							sj.add(s);
						}
						description = sj.length() == 0 ? "No information given." : sj.toString();
					} else {
						description = "No information given";
					}
					writer.println(description);
				}


				writer.println("```zenscript");
				{
					final StringJoiner sj = new StringJoiner(", ", names[0] + "." + method.getSimpleName() + "(", ");");

					for (int i = 1; i < totalCount; i++) {
						//TODO, with or without "as type"?
						sj.add(names[i] + " as " + types[i]);
					}

					writer.println(sj.toString());
				}
				writer.println("```");
				writer.println();

				if (totalCount > 1) {
					writer.println("| Parameter | Type | Description |");
					writer.println("|-----------|------|-------------|");
					for (int i = 1; i < totalCount; i++) {
						writer.printf("| %s | %s | %s |%n", names[i], types[i], descriptions[i]);
					}
					writer.println();
				}

			}

		}
	}

	private void writeCasters(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer) {
		final List<Element> elements = membersToWrite.get(ZenCodeType.Caster.class);
		if (elements.isEmpty()) {
			return;
		}
		writer.println("## Casters");
		writer.println();

		writer.println("| Result type | Implicit | Description |");
		writer.println("|-------------|----------|-------------|");
		for (Element element : elements) {
			//ZenCodeType.caster can only be applied to methods so the cast should be save.
			final ExecutableElement method = (ExecutableElement) element;

			final String returnType = FormattingUtils.convertTypeName(method.getReturnType(), this.processingEnv.getTypeUtils());

			String description;
			final Document annotation = method.getAnnotation(Document.class);
			if (annotation != null) {
				description = annotation.value();
			} else {
				description = this.processingEnv.getElementUtils().getDocComment(method);
				if (description == null) {
					description = "No information provided.";
				}
			}

			final StringJoiner sj = new StringJoiner("<br>");
			for (String s : description.split("\\n")) {
				s = s.trim();
				if (s.isEmpty())
					continue;
				sj.add(s);
			}

			writer.printf("| %s | %b | %s |%n", returnType, method.getAnnotation(ZenCodeType.Caster.class)
					.implicit(), sj.toString());

		}
	}

	private void mergeGetterSetterDescriptions(Map<String, String> descriptions, Element element, String actualName) {
		final Document annotation = element.getAnnotation(Document.class);
		if (annotation != null) {
			descriptions.merge(actualName, annotation
					.value(), (a, b) -> String.format("%s<br>%s", a, b));
		} else {
			final String docComment = this.processingEnv.getElementUtils().getDocComment(element);
			if (docComment != null) {
				descriptions.merge(actualName, docComment, (a, b) -> String.format("%s<br>%s", a, b));
			}
		}
	}


}
