package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotation_processors.processors.AbstractCraftTweakerProcessor;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.ElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.KnownElementList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document.file.DocsJsonWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageWriter;
import com.sun.source.util.Trees;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DocumentProcessor extends AbstractCraftTweakerProcessor {
    
    private static final String defaultOutputDirectory = "docsOut";
    private static final String outputDirectoryOptionName = "crafttweaker.processor.document.output_directory";
    private File outputDirectory;
    
    private KnownElementList knownElementList;
    private KnownModList knownModList;
    
    @Override
    public Set<String> getSupportedOptions() {
        
        final Set<String> supportedOptions = new HashSet<>(super.getSupportedOptions());
        supportedOptions.add(outputDirectoryOptionName);
        return supportedOptions;
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        
        final HashSet<String> result = new HashSet<>(2);
        result.add("com.blamejared.crafttweaker_annotations.annotations.Document");
        result.add("net.minecraftforge.fml.common.Mod");
        return result;
    }
    
    @Override
    public Collection<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        //Ignored since we need the other method here, due to @Mod
        throw new IllegalStateException("Should not be called!");
    }
    
    @Override
    protected void setupDependencyContainer() {
        
        super.setupDependencyContainer();
        setupTrees(processingEnv);
        setupReflections();
    }
    
    @Override
    protected void performInitialization() {
        
        knownElementList = dependencyContainer.getInstanceOfClass(KnownElementList.class);
        knownModList = dependencyContainer.getInstanceOfClass(KnownModList.class);
        final String docsOut = processingEnv.getOptions()
                .getOrDefault(outputDirectoryOptionName, defaultOutputDirectory);
        outputDirectory = new File(docsOut);
    }
    
    @Override
    protected boolean performProcessing(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        if(roundEnv.processingOver()) {
            handleLastRound();
        } else {
            handleIntermediateRound(roundEnv);
        }
        
        return false;
    }
    
    private void setupReflections() {
        
        final ConfigurationBuilder configuration = new ConfigurationBuilder().addUrls(ClasspathHelper
                        .forJavaClassPath())
                .addClassLoaders(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader(), getClass()
                        .getClassLoader())
                .addUrls(ClasspathHelper.forClassLoader());
        
        final Reflections reflections = new Reflections(configuration);
        dependencyContainer.addInstanceAs(reflections, Reflections.class);
    }
    
    private void setupTrees(ProcessingEnvironment processingEnv) {
        
        final String environmentClassName = processingEnv.getClass().getName();
        
        if(!environmentClassName.equals("com.sun.tools.javac.processing.JavacProcessingEnvironment")) {
            //Let's throw the exception ourselves, since Trees.instance just throws an empty IllegalArgumentException which is harder to trace down
            throw new IllegalArgumentException("Processing environment must be JavacProcessingEnvironment, but is " + environmentClassName + "! Make sure you use gradle for compilation.");
        }
        
        final Trees instance = Trees.instance(processingEnv);
        dependencyContainer.addInstanceAs(instance, Trees.class);
    }
    
    public void handleIntermediateRound(RoundEnvironment roundEnvironment) {
        
        knownModList.fillModIdInfo(roundEnvironment);
        knownElementList.addAllForIntermediateRound(roundEnvironment);
    }
    
    public void handleLastRound() {
        
        convertPages();
        writePages();
        writeDocsJsonFile();
    }
    
    private void writeDocsJsonFile() {
        
        final DocumentRegistry documentRegistry = dependencyContainer.getInstanceOfClass(DocumentRegistry.class);
        final DocsJsonWriter docsJsonWriter = new DocsJsonWriter(outputDirectory, documentRegistry);
        try {
            docsJsonWriter.write();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    
    private void convertPages() {
        
        final ElementConverter elementConverter = dependencyContainer.getInstanceOfClass(ElementConverter.class);
        elementConverter.handleElements(knownElementList);
    }
    
    private void writePages() {
        
        final DocumentRegistry documentRegistry = dependencyContainer.getInstanceOfClass(DocumentRegistry.class);
        final PageWriter pageWriter = new PageWriter(documentRegistry, new File(outputDirectory, "docs"));
        try {
            pageWriter.write();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    
}
