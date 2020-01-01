package com.blamejared.crafttweaker_annotation_processors.processors.wrapper;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.file.ZipFileIndex;
import com.sun.tools.javac.file.ZipFileIndexCache;
import com.sun.tools.javac.processing.JavacFiler;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AdvancedDocCommentUtil {
    private static File sourceZip = null;
    private static boolean created = false;

    public static String safeGetDocComment(ProcessingEnvironment environment, Element element) {
        try {
            return getDocComment(environment, element);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getDocComment(ProcessingEnvironment environment, Element element) throws IOException {
        final String docComment = environment.getElementUtils().getDocComment(element);
        if (docComment != null) {
            return docComment;
        }
        getZipLocation(environment);
        if (sourceZip == null) {
            return null;
        }

        final Element enclosingElement = element.getEnclosingElement();
        if (!(enclosingElement instanceof TypeElement)) {
            //Nope, not gonna deal with nested shit.
            return null;
        }

        TypeElement typeElement = (TypeElement) enclosingElement;

        final ZipFile zipFile = new ZipFile(sourceZip);
        final ZipEntry entry = zipFile.getEntry(typeElement.getQualifiedName()
                .toString()
                .replace('.', '/')
                + ".java");

        final List<String> fileContent = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))
                .lines()
                .collect(Collectors.toList());

        //TODO: Find the line where the element is declared.
        //  Then backtrace a few lines to find a comment if present
        //  Then change the comment if possible, like links and the stuff
        //  Once all that is done, find a way to create a new sourcefile in the generated folder
        //  AND let Javac know that it is new

        if (element instanceof ExecutableElement) {
            return getExecutable(((ExecutableElement) element), typeElement, fileContent, environment);
        }


        return null;
    }

    private static String getExecutable(ExecutableElement executable, TypeElement containingType, List<String> fileContent, ProcessingEnvironment environment) {
        final StringBuilder patternBuilder = new StringBuilder("\\s*");
        final Set<Modifier> modifiers = executable.getModifiers();

        if (modifiers.contains(Modifier.PUBLIC)) {
            patternBuilder.append("public ");
        }
        if (modifiers.contains(Modifier.STATIC)) {
            patternBuilder.append("static ");
        }

        if (modifiers.contains(Modifier.ABSTRACT)) {
            patternBuilder.append("abstract ");
        }

        if (modifiers.contains(Modifier.DEFAULT)) {
            patternBuilder.append("default ");
        }

        if (executable.getKind() == ElementKind.CONSTRUCTOR) {
            patternBuilder.append(containingType.getSimpleName());
        } else {
            /*
            //TODO: Generic types
            final String[] split = executable.getReturnType().toString().split("\\.");
            for (int i = 0; i < split.length - 1; i++) {
                patternBuilder.append("(?:");
            }
            for (int i = 0; i < split.length - 1; i++) {
                patternBuilder.append(split[i]).append("\\.)?");
            }
            patternBuilder.append(split[split.length - 1]);
             */

            patternBuilder.append("[\\w.]*");
            patternBuilder.append(" ");
            patternBuilder.append(executable.getSimpleName());
        }

        patternBuilder.append("\\([\\w_ ,]*\\)\\s*\\{\\s*");
        final Pattern compile = Pattern.compile(patternBuilder.toString());
        final List<String> collect = fileContent.stream()
                .filter(compile.asPredicate())
                .collect(Collectors.toList());
        if (collect.size() == 0) {
            return null;
        }
        if (collect.size() == 1) {
            return getComment(fileContent, fileContent.indexOf(collect.get(0)));
        }

        //TODO: Multiple members matched, compare signature as well...

        return null;
    }

    private static String getComment(List<String> fileContent, int fileDeclaration) {
        //Comments are before the file.
        //So we know X .. fileDeclaration - 1 or nothing.
        int i;
        for (i = fileDeclaration - 1; i > 0; i--) {
            final String s = fileContent.get(i);

            //Closing } or ; means no javadoc and we reached the field before
            if (s.trim().matches(".*[};]\\s*")) {
                return null;
            }

            if (s.trim().matches("\\s*/\\*{2}[\\s\\w]*")) {
                //We found our start
                break;
            }
        }

        if (i == 0) {
            //Just in case -.-
            return null;
        }

        final List<String> subList = fileContent.subList(i, fileDeclaration - 1);
        if (subList.get(0).trim().equals("/**")) {
            subList.remove(0);
        }
        for (int i1 = subList.size() - 1; i1 >= 0; i1--) {
            final String trim = subList.get(i1).trim();
            if (trim.endsWith("*/")) {
                subList.set(i1, trim.substring(0, trim.lastIndexOf("*/")));
                break;
            }

            if (trim.matches("@.*")) {
                subList.remove(i1);
            }
        }

        final String collect = subList
                .stream()
                .map(String::trim)
                //Throw out all the " * " at the beginning of each line
                .map(s -> s.replaceFirst("[*\\s]*", ""))
                //We dont want empty lines
                .filter(s -> !s.isEmpty())
                //TODO: modify strings (comments et al)
                .collect(Collectors.joining(System.lineSeparator()));

        return collect;


    }

    public static void getZipLocation(ProcessingEnvironment environment) {
        if (created) {
            return;
        }
        created = true;

        try {
            final Field fileManager = JavacFiler.class.getDeclaredField("fileManager");
            fileManager.setAccessible(true);
            JavacFileManager javacFileManager = (JavacFileManager) fileManager.get(environment.getFiler());

            final Field zipFileIndexCache = JavacFileManager.class.getDeclaredField("zipFileIndexCache");
            zipFileIndexCache.setAccessible(true);
            ZipFileIndexCache cache = (ZipFileIndexCache) zipFileIndexCache.get(javacFileManager);
            sourceZip = cache.getZipFileIndexes()
                    .stream()
                    .map(ZipFileIndex::getZipFile)
                    .map(File::getAbsolutePath)
                    .distinct()
                    .filter(s -> s.endsWith("-recomp.jar"))
                    .map(s -> new File(s.substring(0, s.length() - "recomp.jar".length()) + "sources.jar"))
                    .filter(File::exists)
                    .findAny()
                    .orElse(null);

            System.out.println();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static JavacFileManager getFileManager(ProcessingEnvironment environment) {
        final Field fileManager;
        try {
            fileManager = JavacFiler.class.getDeclaredField("fileManager");
            fileManager.setAccessible(true);
            return (JavacFileManager) fileManager.get(environment.getFiler());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
