package com.blamejared.crafttweaker_annotation_processors.processors.wrapper;

import com.blamejared.crafttweaker_annotation_processors.processors.*;
import com.blamejared.crafttweaker_annotation_processors.processors.wrapper.wrapper_information.*;
import com.blamejared.crafttweaker_annotations.annotations.*;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.tools.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

@SupportedAnnotationTypes({"net.minecraftforge.fml.common.Mod", "com.blamejared.crafttweaker_annotations.annotations.ZenWrapper"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(WrapperProcessor.LOCATION_OPTION_NAME)
public class WrapperProcessor extends AbstractProcessor {
    
    public static final String LOCATION_OPTION_NAME = "wrappedClassesCsvLocation";
    
    private static final String defaultLocation = "wrappedClasses.csv";
    private static final Collection<WrapperInfo> wrappedInfo = new HashSet<>();
    private static final Collection<WrapperInfo> knownWrappers = new HashSet<>();
    private File location;
    private boolean firstRun = true;
    
    public static WrapperInfo getWrapperInfoFor(TypeMirror returnType, ProcessingEnvironment environment) {
        final String retTypeString = returnType.toString();
        //Iterable<E> is java.lang?
        if(retTypeString.startsWith("java.lang") && !retTypeString.contains("<")) {
            return new NativeWrapperInfo(retTypeString);
        }
        
        
        if(returnType.getKind() == TypeKind.ARRAY) {
            final ArrayType arrayType = (ArrayType) returnType;
            final WrapperInfo wrapperInfoFor = getWrapperInfoFor(arrayType.getComponentType(), environment);
            return wrapperInfoFor == null ? null : new ArrayWrapperInfo(wrapperInfoFor);
        }
        
        final Pattern compile = Pattern.compile("(?<collectionClass>java[.]util[.](?:Collection|Set|List|HashSet|ArrayList|LinkedList))<(?<innerType>[\\w.]*)>");
        final Matcher matcher = compile.matcher(retTypeString);
        if(matcher.matches()) {
            final String innerType = matcher.group("innerType");
            final String collectionClass = matcher.group("collectionClass");
            final TypeElement innerTypeElement = environment.getElementUtils()
                    .getTypeElement(innerType);
            if(innerTypeElement == null) {
                return null;
            }
            final WrapperInfo wrapperInfoFor = getWrapperInfoFor(innerTypeElement.asType(), environment);
            
            if(wrapperInfoFor == null) {
                return null;
            }
            final String usedType;
            final Element element = environment.getTypeUtils()
                    .asElement(environment.getTypeUtils().erasure(returnType));
            if(element.getKind() == ElementKind.INTERFACE) {
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
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        final Map<String, String> options = processingEnv.getOptions();
        final String orDefault = options.getOrDefault(LOCATION_OPTION_NAME, defaultLocation);
        
        location = new File(orDefault);
        if(!location.exists()) {
            final Messager messager = processingEnv.getMessager();
            final String format = "Could not find wrappedClasses.csv. This is only an error if you wanted to use it. Looked at %s";
            final String message = String.format(format, location.getAbsolutePath());
            messager.printMessage(Diagnostic.Kind.NOTE, message);
        }
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        if(firstRun) {
            firstRun = false;
        } else {
            return false;
        }
        
        readWrappedInfos(roundEnv);
        AdvancedDocCommentUtil.getZipLocation(processingEnv);
        
        for(final WrapperInfo next : wrappedInfo) {
            final String crTQualifiedName = next.getCrTQualifiedName();
            if(processingEnv.getElementUtils().getTypeElement(crTQualifiedName) != null) {
                continue;
            }
            
            final Filer filer = processingEnv.getFiler();
            try(final PrintWriter writer = new PrintWriter(filer.createSourceFile(crTQualifiedName)
                    .openWriter())) {
                WrappedClass.write(writer, next, processingEnv);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    private void readWrappedInfos(RoundEnvironment roundEnv) {
        if(location.exists()) {
            try(final BufferedReader reader = new BufferedReader(new FileReader(location))) {
                reader.lines()
                        .filter(s -> !s.isEmpty() && !s.startsWith("//"))
                        .map(s -> s.split(";[ \\t]*"))
                        .map(WrapperInfo::create)
                        .filter(Objects::nonNull)
                        .forEach(wrappedInfo::add);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        knownWrappers.addAll(wrappedInfo);
        
        for(Element element : roundEnv.getElementsAnnotatedWith(ZenWrapper.class)) {
            final ZenWrapper annotation = element.getAnnotation(ZenWrapper.class);
            final ExistingWrapperInfo wrapperInfo;
            if(element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                wrapperInfo = new ExistingWrapperInfo(annotation.wrappedClass(), typeElement.getQualifiedName()
                        .toString(), annotation.implementingClass());
            } else {
                wrapperInfo = new ExistingWrapperInfo(annotation.wrappedClass(), element.toString(), annotation
                        .implementingClass());
            }
            if(!annotation.creationMethodFormat().isEmpty()) {
                wrapperInfo.setWrappingFormat(annotation.creationMethodFormat());
            }
            knownWrappers.add(wrapperInfo);
        }
        
        //Also scan the classPath (needed when CrT is added as dependency)
        for(Class<?> aClass : ReflectionReader.getClassesWithZenWrapper(getClass().getClassLoader())) {
            final ZenWrapper annotation = aClass.getAnnotation(ZenWrapper.class);
            final ExistingWrapperInfo wrapperInfo = new ExistingWrapperInfo(annotation.wrappedClass(), aClass
                    .getCanonicalName(), annotation.implementingClass());
            if(!annotation.conversionMethodFormat().isEmpty()) {
                wrapperInfo.setUnWrappingFormat(annotation.conversionMethodFormat());
            }
            
            if(!annotation.creationMethodFormat().isEmpty()) {
                wrapperInfo.setWrappingFormat(annotation.creationMethodFormat());
            }
            
            knownWrappers.add(wrapperInfo);
        }
    }
}
