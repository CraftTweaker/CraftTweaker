package com.blamejared.crafttweaker_annotation_processors.processors;

import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessor extends AbstractProcessor {

    private static boolean firstCall = true;

    private static final List<Class<? extends Annotation>> annotationsToCheck = new ArrayList<>();
    static {
        annotationsToCheck.add(ZenCodeType.Method.class);
        annotationsToCheck.add(ZenCodeType.Setter.class);
        annotationsToCheck.add(ZenCodeType.Getter.class);
        annotationsToCheck.add(ZenCodeType.Operator.class);
        annotationsToCheck.add(ZenCodeType.Field.class);
        annotationsToCheck.add(ZenCodeType.Caster.class);
    }

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
        } catch(IOException e) {
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
        for(TypeElement annotation : annotations) {

            //Every element that was annotated
            for(Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                //For now we only need the classes and interfaces, we'll check the members later
                if(!element.getKind().isClass() && !element.getKind().isInterface()) {
                    continue;
                }

                //At this point it is either a class or an interface
                final TypeElement typeElement = (TypeElement) element;

                if(typeElement.getAnnotation(ZenCodeType.Name.class) == null && typeElement.getAnnotation(ZenCodeType.Expansion.class) == null) {
                    messager.printMessage(Diagnostic.Kind.ERROR, String.format(Locale.ENGLISH, "@Document requires either @ZenCodeType.Name or @ZenCodeType.Expansion to be present. Class %s has neither", typeElement.getQualifiedName()), typeElement);
                }


                final Map<Class<? extends Annotation>, List<Element>> annotatedMembers = new HashMap<>();
                for(Class<? extends Annotation> zenAnnotation : annotationsToCheck) {
                    annotatedMembers.put(zenAnnotation, new ArrayList<>());
                }

                for(Element enclosedElement : typeElement.getEnclosedElements()) {
                    for(Class<? extends Annotation> zenAnnotation : annotationsToCheck) {
                        if(enclosedElement.getAnnotation(zenAnnotation) != null) {
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
        final ZenCodeType.Name nameAnnotation = typeElement.getAnnotation(ZenCodeType.Name.class);
        final boolean isClass = nameAnnotation != null;
        final boolean needsDocParam; //No script file -> @docParam are needed!
        {
            final Document annotation = typeElement.getAnnotation(Document.class);
            needsDocParam = annotation==null || annotation.scriptFile().isEmpty();

            if(needsDocParam) {
                final String docComment = this.processingEnv.getElementUtils().getDocComment(typeElement);
                if(docComment == null || !docComment.contains("@docParam this")) {
                    //We only print the message if there are nonstatic members
                    final boolean containsNonStatic = membersToWrite.values()
                            .stream()
                            .flatMap(List::stream)
                            .map(Element::getModifiers)
                            .anyMatch(e -> !e.contains(Modifier.STATIC));
                    if(containsNonStatic) {
                        //TODO: Error?
                        messager.printMessage(Diagnostic.Kind.WARNING, "Type " + typeElement.getQualifiedName() + " requires either a Script file or a '@docParam this <example>' doc comment", typeElement);
                    }
                }
            }
        }
        final Path fullPath = docsDir.resolve(getDocsDir(typeElement) + ".md");

        if(!isClass && typeElement.getAnnotation(ZenCodeType.Expansion.class) == null) {
            messager.printMessage(Diagnostic.Kind.ERROR, String.format(Locale.ENGLISH, "@Document requires either @ZenCodeType.Name or @ZenCodeType.Expansion to be present. Class %s has neither, skipping class", typeElement.getQualifiedName()), typeElement);
            return;
        }

        try {
            if(Files.notExists(fullPath.getParent())) {
                Files.createDirectories(fullPath.getParent());
            }
            messager.printMessage(Diagnostic.Kind.NOTE, "Creating " + fullPath.toAbsolutePath());

            try(final PrintWriter writer = new PrintWriter(Files.newBufferedWriter(fullPath, StandardOpenOption.CREATE))) {

                writeHeaderAndDescription(messager, typeElement, isClass, writer);

                writeGetterSetter(membersToWrite, writer);

                writeOperators(membersToWrite, writer, !isClass, needsDocParam);

                writeMethods(membersToWrite, writer, !isClass, needsDocParam);

                writeCasters(membersToWrite, writer);

                if(!needsDocParam) {
                    writeScript(typeElement, writer);
                }
            }
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.toString(), typeElement);
        }
    }

    private void writeScript(TypeElement typeElement, PrintWriter writer) {
        writer.println();
        writer.println();
        writer.println("## Example");
        writer.println();
        writer.println("```zenscript");


        //By this time we're sure that it is present so no null check after getAnnotation
        final String pathname = typeElement.getAnnotation(Document.class).scriptFile();
        try(final BufferedReader br = new BufferedReader(new FileReader(new File(pathname).getAbsoluteFile()))) {
            br.lines().forEach(writer::println);
        } catch (IOException e) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Could not read script file " + pathname + ": " + e);
        }

        writer.println("```");

    }

    private void writeHeaderAndDescription(Messager messager, TypeElement typeElement, boolean isClass, PrintWriter writer) {
        //Header
        writer.printf("# %s%n%n", typeElement.getSimpleName());

        //Description
        final String docComment = convertDocComment(typeElement, false);
        if(docComment != null) {
            writer.println(StringReplaceUtil.replaceAll(docComment, "@docParam this [^\\r\\n]*", s -> "Example: `" + s.trim().substring(15) + "`"));
        } else {
            messager.printMessage(Diagnostic.Kind.WARNING, typeElement.getQualifiedName() + " has no documentation!", typeElement);
            writer.println("No further info provided.");
        }
        writer.println();
        writer.println();

        //Import / Extended Type
        if(isClass) {
            writer.println("## Importing the class");
            writer.println();
            writer.println("It might be required for you to import the package if you encounter any issues (like casting an [Array](/AdvancedFunctions/Arrays_and_Loops/)), so better be safe than sorry and add the import.  ");
            writer.printf("`import %s;`%n%n", typeElement.getAnnotation(ZenCodeType.Name.class).value());
        } else {
            final String expandedType = typeElement.getAnnotation(ZenCodeType.Expansion.class).value();
            final String expandedTypeName = expandedType.substring(expandedType.lastIndexOf('.') + 1);
            writer.println("## Expanding " + expandedTypeName);
            writer.printf("This class is an expansion to %s.  %nThat means that instead of providing methods on its own, it adds new methods to %s.  %nThese methods can be used on any %s object, as long as you have a reference to it.  %n You can use these functions as if they were defined on %s directly.%n%n", expandedType, expandedTypeName, expandedTypeName, expandedTypeName);
        }
    }

    private void writeGetterSetter(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer) {
        //Getter and Setter
        final Set<String> getterNames = new HashSet<>();
        final Set<String> setterNames = new HashSet<>();
        final Map<String, String> descriptions = new HashMap<>();

        for(Element element : membersToWrite.get(ZenCodeType.Setter.class)) {
            final String actualName;

            final String annotatedName = element.getAnnotation(ZenCodeType.Setter.class).value();
            if(!annotatedName.isEmpty()) {
                actualName = annotatedName;
                setterNames.add(annotatedName);
            } else {
                final String memberName = element.getSimpleName().toString();
                if(memberName.startsWith("set")) {
                    actualName = memberName.substring(3, 4).toLowerCase() + memberName.substring(4);
                    setterNames.add(actualName);
                } else {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Skipping Setter " + element.toString(), element);
                    continue;
                }
            }

            mergeGetterSetterDescriptions(descriptions, element, actualName);
        }

        for(Element element : membersToWrite.get(ZenCodeType.Getter.class)) {
            final String actualName;

            final String annotatedName = element.getAnnotation(ZenCodeType.Getter.class).value();
            if(!annotatedName.isEmpty()) {
                actualName = annotatedName;
                getterNames.add(annotatedName);
            } else {

                final String memberName = element.getSimpleName().toString();
                if(memberName.startsWith("get")) {
                    actualName = memberName.substring(3, 4).toLowerCase() + memberName.substring(4);
                    getterNames.add(actualName);
                } else if(memberName.startsWith("is") && element instanceof ExecutableElement && ((ExecutableElement) element).getReturnType().getKind() == TypeKind.BOOLEAN) {
                    actualName = memberName.substring(2, 3).toLowerCase() + memberName.substring(3);
                    getterNames.add(actualName);
                } else {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Skipping Getter " + element.toString(), element);
                    continue;
                }
            }
            mergeGetterSetterDescriptions(descriptions, element, actualName);
        }

        for(Element element : membersToWrite.get(ZenCodeType.Field.class)) {
            final String annotatedName = element.getAnnotation(ZenCodeType.Field.class).value();
            final String actualName = annotatedName.isEmpty() ? element.getSimpleName().toString() : annotatedName;
            getterNames.add(actualName);
            setterNames.add(actualName);

            mergeGetterSetterDescriptions(descriptions, element, actualName);
        }

        final SortedSet<String> settersAndGetters = new TreeSet<>(Comparator.naturalOrder());
        settersAndGetters.addAll(setterNames);
        settersAndGetters.addAll(getterNames);

        if(!settersAndGetters.isEmpty()) {
            writer.println("## Getters/Setters");
            writer.println();

            writer.println("| Name | HasGetter | HasSetter | Description |");
            writer.println("|------|-----------|-----------|-------------|");

            for(String name : settersAndGetters) {
                writer.printf("| %s | %b | %b | %s |%n", name, getterNames.contains(name), setterNames.contains(name), descriptions.getOrDefault(name, "No Information given.").replaceAll("[\\n\\r]+", "<br>"));
            }
        }
        writer.println();
    }

    private void writeOperators(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer, boolean isExtension, boolean needsDocParam) {
        final List<Element> elements = membersToWrite.get(ZenCodeType.Operator.class);
        if(elements.isEmpty())
            return;
        elements.sort(Comparator.comparing(e -> e.getAnnotation(ZenCodeType.Operator.class).value().name()));

        writer.println("## Operators");
        writer.println();

        for(Element element : elements) {
            final ZenCodeType.OperatorType operatorType = element.getAnnotation(ZenCodeType.Operator.class).value();

            final int operandCount = FormattingUtils.getOperandCountFor(operatorType);
            final int requiredMethodParameters = isExtension ? operandCount : operandCount - 1;
            final String operatorFormat = FormattingUtils.getOperatorFormat(operatorType);

            //@Operator can only be applied to methods
            final ExecutableElement method = (ExecutableElement) element;
            final List<? extends VariableElement> parameters = method.getParameters();
            if(parameters.size() != requiredMethodParameters) {
                if(isExtension) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Operator %s requires %d operands as it is an extension method.", operatorType, requiredMethodParameters), method);
                } else {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Operator %s requires %d operands.", operatorType, requiredMethodParameters), method);
                }

                continue;
            }

            final OperandInfo[] operandInfo = new OperandInfo[operandCount];


            fillMethodInfo(isExtension, operandCount, method, operandInfo, needsDocParam);


            writer.println("### " + operatorType.name());
            writer.println();
            writer.println("```zenscript");

            writer.print("//");
            writer.printf(operatorFormat, Arrays.stream(operandInfo).map(OperandInfo::getOperandName).toArray());
            writer.println();
            writer.printf(operatorFormat, Arrays.stream(operandInfo).map(OperandInfo::getOperandExample).toArray());
            writer.println();
            writer.println("```");

            writer.println();
            writer.println("| Parameter Name | Parameter Type | Description |");
            writer.println("|----------------|----------------|-------------|");

            for(int i = 1; i < operandCount; i++) {
                final OperandInfo operandInfo1 = operandInfo[i];
                writer.printf("| %s | %s | %s |%n",
                        operandInfo1.getOperandName(), operandInfo1.getOperandType(), operandInfo1
                        .getOperandDescription());
            }
        }
        writer.println();
    }

    private void fillMethodInfo(boolean isExtension, int operandCount, ExecutableElement method, OperandInfo[] operandInfos, boolean needsDocParam) {
        {
            final boolean isStatic = method.getModifiers().contains(Modifier.STATIC);
            final Element argumentElement = isExtension ? method.getParameters().get(0) : method.getEnclosingElement();
            final String simpleName = argumentElement.getSimpleName().toString();

            final OperandInfo operandInfo = new OperandInfo();
            operandInfos[0] = operandInfo;
            if(!isExtension && isStatic) {
                final Element enclosingElement = method.getEnclosingElement();
                final String typeName = FormattingUtils.convertTypeName(enclosingElement.asType(), this.processingEnv.getTypeUtils());
                operandInfo.setOperandName(typeName);
                operandInfo.setOperandType(typeName);
                operandInfo.setOperandExample(typeName);
            } else {
                operandInfo.setOperandName("my" + simpleName.substring(0, 1).toUpperCase() + simpleName.substring(1));
                operandInfo.setOperandType(FormattingUtils.convertTypeName(argumentElement.asType(), this.processingEnv.getTypeUtils()));
                final StringJoiner docParamJoiner = new StringJoiner("");
                final String docComment = this.processingEnv.getElementUtils().getDocComment(argumentElement);
                if(docComment != null) {
                    joinDocAnnotation(docComment, docParamJoiner, "@docParam", "this");
                    if(docParamJoiner.length() != 0) {
                        operandInfo.setOperandExample(docParamJoiner.toString());
                    }
                }
            }
            //Description of base type will never be printed so we dont need to set it to anything.
        }

        for(int i = 1; i < operandCount; i++) {
            final OperandInfo operandInfo = new OperandInfo();
            operandInfos[i] = operandInfo;
            final VariableElement argumentElement = method.getParameters().get(isExtension ? i : i - 1);
            operandInfo.setOperandName(argumentElement.getSimpleName().toString());
            operandInfo.setOperandType(FormattingUtils.convertTypeName(argumentElement.asType(), this.processingEnv.getTypeUtils()));

            String parentDoc = convertDocComment(argumentElement.getEnclosingElement(), false);
            if(parentDoc == null) {
                parentDoc = "";
            }

            final StringJoiner paramJoiner = new StringJoiner("<br>");
            final StringJoiner docParamJoiner = new StringJoiner("<br>");
            joinDocAnnotation(parentDoc, paramJoiner, "@param", operandInfo.getOperandName());
            joinDocAnnotation(parentDoc, docParamJoiner, "@docParam", operandInfo.getOperandName());

            if(paramJoiner.length() != 0) {
                operandInfo.setOperandDescription(paramJoiner.toString());
            }
            if(docParamJoiner.length() != 0) {
                operandInfo.setOperandExample(docParamJoiner.toString());
            } else if (needsDocParam) {
                //TODO Error?
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, String.format(Locale.ENGLISH, "Parameter %s of method %s requires a '@docParam' in the method's javadoc since the type %s has no attached script.", operandInfo.getOperandName(), method.getSimpleName(), method.getEnclosingElement().getSimpleName()));
            }
        }
    }

    private void joinDocAnnotation(String parentDoc, StringJoiner paramJoiner, String annotation, String operandName) {
        boolean found = false;
        final String prefix = annotation + " " + operandName;
        for(String s : parentDoc.split("\n")) {
            s = s.trim();
            if(s.isEmpty()) {
                continue;
            }
            if(s.startsWith(prefix)) {
                found = true;
            } else if(s.startsWith("@")) {
                found = false;
            }

            if(found) {
                paramJoiner.add(s.substring(prefix.length() + 1));
            }
        }
    }

    private void writeMethods(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer, boolean isExpansion, boolean needsDocParam) {
        final List<Element> elements = membersToWrite.get(ZenCodeType.Method.class);
        if(elements.isEmpty()) {
            return;
        }

        writer.println("## Methods");
        writer.println();

        //New Map of overloaded method names -> methods
        final Map<String, List<ExecutableElement>> methodNamesToMethods = new HashMap<>();
        for(Element element : elements) {
            //@ZenCodeType.Method is only applicable to methods, so the cast is save.
            final ExecutableElement method = (ExecutableElement) element;
            String annotationValue = method.getAnnotation(ZenCodeType.Method.class).value();
            methodNamesToMethods.computeIfAbsent(annotationValue.isEmpty() ? method.getSimpleName().toString() : annotationValue, s -> new ArrayList<>()).add(method);
        }

        for(Map.Entry<String, List<ExecutableElement>> stringListEntry : methodNamesToMethods.entrySet()) {
            writer.print("### " + stringListEntry.getKey());
            writer.println();

            for(ExecutableElement method : stringListEntry.getValue()) {

                final List<? extends VariableElement> parameters = method.getParameters();
                final int totalCount = isExpansion ? parameters.size() : parameters.size() + 1;

                final OperandInfo[] operandInfos = new OperandInfo[totalCount];

                fillMethodInfo(isExpansion, totalCount, method, operandInfos, needsDocParam);


                final String docComment = convertDocComment(method, true);
                final String description;
                if(docComment != null) {
                    final StringJoiner sj = new StringJoiner("<br>");
                    for(String s : docComment.split("\n")) {
                        s = s.trim();
                        if(s.isEmpty() || s.startsWith("@param")) {
                            continue;
                        } else if(s.startsWith("@return")) {
                            s = "Returns: `" + s.substring("@return".length()) + "`";
                        }
                        sj.add(s);
                    }
                    description = sj.length() == 0 ? "No information given." : sj.toString();
                } else {
                    description = "No documentation found!";
                }
                writer.println(description);


                writer.println("```zenscript");
                final boolean hasAllExamples = Arrays.stream(operandInfos).allMatch(OperandInfo::hasOperandExample);
                final StringJoiner joiner = new StringJoiner(", ", (hasAllExamples ? "//" : "") + operandInfos[0].getOperandName() + "." + method.getSimpleName() + "(", ");");
                final StringJoiner joinerExample = new StringJoiner(", ", operandInfos[0].getOperandExample() + "." + method.getSimpleName() + "(", ");");

                for(int i = 1; i < totalCount; i++) {
                    //TODO, with or without "as type"?
                    joiner.add(operandInfos[i].getOperandName() + " as " + operandInfos[i].getOperandType());
                    if(hasAllExamples) {
                        joinerExample.add(operandInfos[i].getOperandExample());
                    }
                }

                writer.println(joiner.toString());
                if(hasAllExamples) {
                    writer.println(joinerExample.toString());
                }
                writer.println("```");
                writer.println();

                if(totalCount > 1) {
                    writer.println("| Parameter | Type | Description |");
                    writer.println("|-----------|------|-------------|");
                    for(int i = 1; i < totalCount; i++) {
                        writer.printf("| %s | %s | %s |%n", operandInfos[i].getOperandName(), operandInfos[i].getOperandType(), operandInfos[i].getOperandDescription());
                    }
                    writer.println();
                }

            }

        }
    }

    private void writeCasters(Map<Class<? extends Annotation>, List<Element>> membersToWrite, PrintWriter writer) {
        final List<Element> elements = membersToWrite.get(ZenCodeType.Caster.class);
        if(elements.isEmpty()) {
            return;
        }
        writer.println("## Casters");
        writer.println();

        writer.println("| Result type | Implicit | Description |");
        writer.println("|-------------|----------|-------------|");
        for(Element element : elements) {
            //ZenCodeType.caster can only be applied to methods so the cast should be save.
            final ExecutableElement method = (ExecutableElement) element;

            final String returnType = FormattingUtils.convertTypeName(method.getReturnType(), this.processingEnv.getTypeUtils());

            String description = convertDocComment(method, true);
            if(description == null) {
                description = "No information provided.";
            }


            final StringJoiner sj = new StringJoiner("<br>");
            for(String s : description.split("\\n")) {
                s = s.trim();
                if(s.isEmpty())
                    continue;
                sj.add(s);
            }

            writer.printf("| %s | %b | %s |%n", returnType, method.getAnnotation(ZenCodeType.Caster.class).implicit(), sj.toString());

        }
    }

    private void mergeGetterSetterDescriptions(Map<String, String> descriptions, Element element, String actualName) {
        final String docComment = convertDocComment(element, true);
        if(docComment != null) {
            descriptions.merge(actualName, docComment, (a, b) -> String.format("%s<br>%s", a, b));
        }
    }

    private String getDocsDir(TypeElement typeElement) {
        if(typeElement == null) {
            return "Unknown type!";
        }
        return getDocsDir(typeElement, typeElement.getAnnotation(Document.class), typeElement.getAnnotation(ZenCodeType.Name.class));
    }

    private String getDocsDir(TypeElement typeElement, Document docAnnotation, ZenCodeType.Name nameAnnotation) {
        final String s;
        if(docAnnotation != null && !docAnnotation.value().isEmpty()) {
            s = docAnnotation.value();
        } else if(nameAnnotation != null && !nameAnnotation.value().isEmpty()) {
            s = nameAnnotation.value();
        } else {
            s = typeElement.getQualifiedName().toString();
        }
        return s.replace('.', File.separatorChar);
    }

    private String convertDocComment(Element element, boolean replaceDocParam) {
        String docComment = this.processingEnv.getElementUtils().getDocComment(element);
        if(docComment == null)
            return null;

        //TODO: currently only catching fully qualified @links
        docComment = StringReplaceUtil.replaceAll(docComment, "\\{@link [\\w \\.]*}", s -> {
            s = s.substring("{@link ".length());
            s = s.substring(0, s.length() - 1).trim();
            final TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(s);
            if(typeElement == null) {
                return s;
            }

            final String display;
            final String link;

            final ZenCodeType.Name nameAnnotation = typeElement.getAnnotation(ZenCodeType.Name.class);
            if(nameAnnotation == null) {
                display = element.getSimpleName().toString();
            } else {
                final String value = nameAnnotation.value();
                display = value.substring(value.lastIndexOf('.') + 1);
            }

            final Document documentAnnotation = typeElement.getAnnotation(Document.class);
            if(documentAnnotation == null) {
                link = "UNKNOWN LINK!!!";
            } else {
                final String value = documentAnnotation.value().replaceAll("\\.", File.pathSeparator);
                link = value.startsWith("/") ? value : "/" + value;
            }


            return String.format(Locale.ENGLISH, "[%s](%s)", display, link);
        });

        return replaceDocParam ? docComment.replaceAll("@docParam \\w* [^\\r\\n]*", ""): docComment;
    }


}
