package com.blamejared.crafttweaker_annotation_processors.processors.wrapper;

import com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information.*;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.processing.JavacFiler;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SupportedAnnotationTypes({"net.minecraftforge.fml.common.Mod", "com.blamejared.crafttweaker_annotations.annotations.ZenWrapper"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class WrapperProcessor extends AbstractProcessor {
    private static final File location = new File("wrappedClasses.csv");
    private static Collection<WrapperInfo> wrappedInfo = new HashSet<>();
    private static Collection<WrapperInfo> knownWrappers = new HashSet<>();
    private boolean firstRun = true;

    public static WrapperInfo getWrapperInfoFor(TypeMirror returnType, ProcessingEnvironment environment) {
        final String retTypeString = returnType.toString();
        //Iterable<E> is java.lang?
        if (retTypeString.startsWith("java.lang") && !retTypeString.contains("<")) {
            return new NativeWrapperInfo(retTypeString);
        }


        if (returnType.getKind() == TypeKind.ARRAY) {
            final ArrayType arrayType = (ArrayType) returnType;
            final WrapperInfo wrapperInfoFor = getWrapperInfoFor(arrayType.getComponentType(), environment);
            return wrapperInfoFor == null ? null : new ArrayWrapperInfo(wrapperInfoFor);
        }

        final Pattern compile = Pattern.compile("(?<collectionClass>java[.]util[.](?:Collection|Set|List|HashSet|ArrayList|LinkedList))<(?<innerType>[\\w.]*)>");
        final Matcher matcher = compile.matcher(retTypeString);
        if (matcher.matches()) {
            final String innerType = matcher.group("innerType");
            final String collectionClass = matcher.group("collectionClass");
            final TypeElement innerTypeElement = environment.getElementUtils()
                    .getTypeElement(innerType);
            if (innerTypeElement == null) {
                return null;
            }
            final WrapperInfo wrapperInfoFor = getWrapperInfoFor(innerTypeElement
                    .asType(), environment);

            if (wrapperInfoFor == null) {
                return null;
            }
            final String usedType;
            final Element element = environment.getTypeUtils().asElement(environment.getTypeUtils().erasure(returnType));
            if (element.getKind() == ElementKind.INTERFACE) {
                usedType = retTypeString.contains("Set") ? "java.util.HashSet" : "java.util.ArrayList";
            } else {
                usedType = retTypeString;
            }

            return new CollectionWrapperInfo(collectionClass, usedType, wrapperInfoFor);

        }


        return knownWrappers.stream()
                .filter(knownWrapper -> retTypeString.contentEquals(knownWrapper.getWrappedClass()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (firstRun) {
            firstRun = false;
        } else {
            return false;
        }

        readWrappedInfos(roundEnv);
        AdvancedDocCommentUtil.getZipLocation(processingEnv);

        final File absoluteFile = new File("src/generated/java").getAbsoluteFile();
        final JavacFileManager fileManager = AdvancedDocCommentUtil.getFileManager(processingEnv);
        for (final WrapperInfo next : wrappedInfo) {
            if (processingEnv.getElementUtils().getTypeElement(next.getCrTQualifiedName()) != null) {
                continue;
            }

            final File file = next.getFile(absoluteFile);

            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Could not create folder " + file.getParentFile());
            }

            if (fileManager == null) {
                return false;
            }


            final JavaFileObject regularFile = fileManager.getRegularFile(file);
            try (final PrintWriter writer = new PrintWriter(regularFile.openWriter())) {
                WrappedClass.write(writer, next, processingEnv);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((JavacFiler) processingEnv.getFiler()).getGeneratedSourceFileObjects().add(regularFile);

            /*
            System.out.printf("%n%n%n%n%n%n%n%n%n-----------------------%n");
            final StringWriter out = new StringWriter();
            WrappedClass.write(new PrintWriter(out), next, processingEnv);
            System.out.println(out.getBuffer().toString());
            System.out.printf("-----------------------%n%n%n%n%n%n%n%n%n");

             */
        }

        return false;
    }

    private void readWrappedInfos(RoundEnvironment roundEnv) {
        if (location.exists()) {
            try (final BufferedReader reader = new BufferedReader(new FileReader(location))) {
                reader.lines()
                        .filter(s -> !s.isEmpty() && !s.startsWith("//"))
                        .map(s -> s.split(";[ \\t]*"))
                        .map(WrapperInfo::create)
                        .filter(Objects::nonNull)
                        .forEach(wrappedInfo::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        knownWrappers.addAll(wrappedInfo);

        for (Element element : roundEnv.getElementsAnnotatedWith(ZenWrapper.class)) {
            final ZenWrapper annotation = element.getAnnotation(ZenWrapper.class);

            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                knownWrappers.add(new ExistingWrapperInfo(annotation.wrappedClass(), typeElement.getQualifiedName()
                        .toString(), annotation.implementingClass()));
            } else {
                knownWrappers.add(new ExistingWrapperInfo(annotation.wrappedClass(), element.toString(), annotation.implementingClass()));
            }
        }

        final Set<Class<?>> typesAnnotatedWith = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()))
                .getTypesAnnotatedWith(ZenWrapper.class);

        //Also scan the classPath (needed when CrT is added as dependency)
        for (Class<?> aClass : typesAnnotatedWith) {
            final ZenWrapper annotation = aClass.getAnnotation(ZenWrapper.class);
            final ExistingWrapperInfo wrapperInfo = new ExistingWrapperInfo(annotation.wrappedClass(), aClass.getCanonicalName(), annotation
                    .implementingClass());
            if (!annotation.conversionMethodFormat().isEmpty()) {
                wrapperInfo.setUnWrappingFormat(annotation.conversionMethodFormat());
            }

            if (!annotation.creationMethodFormat().isEmpty()) {
                wrapperInfo.setWrappingFormat(annotation.creationMethodFormat());
            }

            knownWrappers.add(wrapperInfo);
        }
    }
}
