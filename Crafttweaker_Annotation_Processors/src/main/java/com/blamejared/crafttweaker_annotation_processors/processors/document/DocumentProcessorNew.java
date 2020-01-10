package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotations.annotations.Document;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document", "net.minecraftforge.fml.common.Mod"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessorNew extends AbstractProcessor {
    
    private static final File docsOut = new File("docsOut");
    private static final Set<CrafttweakerDocumentationPage> pages = new TreeSet<>(Comparator.comparing(CrafttweakerDocumentationPage::getDocumentTitle));
    public static Map<String, String> modIdByPackage = new HashMap<>();
    
    public static String getModIdForPackage(Element element, ProcessingEnvironment environment) {
        final String packageName = environment.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        for(String knownPackName : modIdByPackage.keySet()) {
            if(packageName.startsWith(knownPackName)) {
                return modIdByPackage.get(knownPackName);
            }
        }
        return null;
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        fillModIdInfo(roundEnv);
        
        for(Element element : roundEnv.getElementsAnnotatedWith(Document.class)) {
            final Document document = element.getAnnotation(Document.class);
            if(document == null) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Internal error! Document annotation null", element);
                continue;
            }
            
            if(!element.getKind().isClass() && !element.getKind().isInterface()) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "How is this annotated", element);
                continue;
            }
            
            
            final TypeElement typeElement = (TypeElement) element;
            final CrafttweakerDocumentationPage documentationPage = CrafttweakerDocumentationPage.convertType(typeElement, this.processingEnv);
            if(documentationPage != null) {
                pages.add(documentationPage);
            }
        }
        
        if(roundEnv.processingOver()) {
            clearOutputDir();
            writeToFiles();
        }
        return false;
    }
    
    private void clearOutputDir() {
        if(docsOut.exists()) {
            if(!docsOut.isDirectory()) {
                throw new IllegalStateException("File " + docsOut + " exists and is not a directory!");
            }
            
            try {
                Files.walkFileTree(docsOut.getAbsoluteFile().toPath(), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return super.visitFile(file, attrs);
                    }
                    
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return super.postVisitDirectory(dir, exc);
                    }
                });
            } catch(IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
            }
        }
    }
    
    private void fillModIdInfo(RoundEnvironment roundEnv) {
        final TypeElement typeElement = processingEnv.getElementUtils().getTypeElement("net.minecraftforge.fml.common.Mod");
        outer:
        for(Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
            for(AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                if(annotationMirror.getAnnotationType().asElement().equals(typeElement)) {
                    final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
                    
                    for(ExecutableElement executableElement : elementValues.keySet()) {
                        if(executableElement.getSimpleName().toString().equals("value")) {
                            final String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
                            modIdByPackage.put(packageName, elementValues.get(executableElement).getValue().toString());
                            continue outer;
                        }
                    }
                }
            }
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not find mod-id for this element!", element);
        }
    }
    
    private void writeToFiles() {
        //Create folder
        if(!docsOut.exists() && !docsOut.mkdirs()) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Could not create folder " + docsOut.getAbsolutePath());
            return;
        }
        
        //Create files
        try {
            for(CrafttweakerDocumentationPage page : pages) {
                page.write(docsOut, processingEnv);
            }
            writeYAML();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void writeYAML() throws IOException {
        
        final List<CrafttweakerDocumentationPage> values = new ArrayList<>(CrafttweakerDocumentationPage.knownTypes.values());
        values.sort(Comparator.comparing(CrafttweakerDocumentationPage::getDocPath));
        final Map<String, YAMLFolder> files = new HashMap<>();
        for(CrafttweakerDocumentationPage value : values) {
            
            String path = value.getDocPath().substring(0, value.getDocPath().lastIndexOf("/"));
            String[] topFolders = path.split("/");
            for(int i = 0; i < topFolders.length; i++) {
                topFolders[i] = topFolders[i].toUpperCase().charAt(0) + topFolders[i].substring(1);
            }
            YAMLFolder folder = files.computeIfAbsent(topFolders[0], YAMLFolder::new);
            if(topFolders.length > 1) {
                for(int i = 1; i < topFolders.length; i++) {
                    YAMLFolder file = folder.getOrCreate(topFolders[i]);
                    folder.addFile(file);
                    folder = file;
                }
            }
            folder.addFile(new YAMLFile(value.getDocumentTitle(), value.getDocPath()));
        }
        final File mkdocsFile = new File(docsOut, "mkdocs.yml");
        try(final PrintWriter writer = new PrintWriter(new FileWriter(mkdocsFile))) {
            
            for(String s : files.keySet()) {
                writer.println(files.get(s).getOutput(0));
            }
        }
    }
    
    
    private abstract static class YAMLObject {
        
        protected final String name;
        
        public YAMLObject(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public abstract String getOutput(int subLevel);
        
        @Override
        public boolean equals(Object o) {
            if(this == o)
                return true;
            if(!(o instanceof YAMLObject))
                return false;
            
            YAMLObject that = (YAMLObject) o;
            
            return getName().equals(that.getName());
        }
        
        @Override
        public int hashCode() {
            return getName().hashCode();
        }
        
        @Override
        public String toString() {
            return getName();
        }
    }
    
    private static class YAMLFolder extends YAMLObject {
        
        private final List<YAMLObject> files;
        
        public YAMLFolder(String name) {
            super(name);
            this.files = new ArrayList<>();
        }
        
        @Override
        public String getOutput(int subLevel) {
            //TODO add layer stuff to get spacing right
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < subLevel; i++) {
                sb.append("  ");
            }
            sb.append("- ").append(name).append(":\n");
            for(YAMLObject file : getFiles()) {
                sb.append("  ").append(file.getOutput(subLevel+1));
            }
            return sb.toString();
        }
        
        public YAMLFolder getOrCreate(String name) {
            // Having this be a full stream one line method was giving me errors and wasn't working as intended
            for(YAMLFolder object : files.stream().filter(yamlObject -> yamlObject instanceof YAMLFolder).map(yamlObject -> (YAMLFolder) yamlObject).collect(Collectors.toSet())) {
                if(object.getName().equals(name)) {
                    return object;
                }
            }
            return new YAMLFolder(name);
        }
    
        public boolean contains(String name) {
        
            for(YAMLFolder object : files.stream().filter(yamlObject -> yamlObject instanceof YAMLFolder).map(yamlObject -> (YAMLFolder) yamlObject).collect(Collectors.toSet())) {
                if(object.getName().equals(name)) {
                    return true;
                }
            }
            return false;
        }
        
        public void addFile(YAMLObject file) {
            if(contains(file.getName())){
                return;
            }
            this.files.add(file);
        }
        
        public List<YAMLObject> getFiles() {
            return files;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("YAMLFolder{");
            sb.append("name='").append(name).append('\'');
            sb.append(", files=").append(files);
            sb.append('}');
            return sb.toString();
        }
        
        
    }
    
    private static class YAMLFile extends YAMLObject {
        
        private final String path;
        
        public YAMLFile(String name, String path) {
            super(name);
            this.path = path;
        }
        
        
        public String getPath() {
            return path;
        }
        
        
        @Override
        public String getOutput(int subLevel) {
            StringBuilder subBuilder = new StringBuilder();
            for(int i = 0; i < subLevel; i++) {
                subBuilder.append("  ");
            }
            return subBuilder.toString() + String.format("- %s: '%s.md'%n", name, path);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("YAMLFile{");
            sb.append("name='").append(name).append('\'');
            sb.append(", path='").append(path).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
    
}
