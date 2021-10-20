package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.rules;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccessibleTypesFinder {
    
    private final Trees trees;
    private final Elements elementUtils;
    
    public AccessibleTypesFinder(Trees trees, Elements elementUtils) {
        
        this.trees = trees;
        this.elementUtils = elementUtils;
    }
    
    
    public List<String> getAllAccessibleTypesAt(Element element) {
        
        final List<String> typesInSamePackage = getTypesInSamePackageAs(element);
        final List<String> importedTypes = getImportedTypes(element);
        
        return combineTypes(typesInSamePackage, importedTypes);
    }
    
    private List<String> getTypesInSamePackageAs(Element element) {
        
        final PackageElement packageElement = getPackageOf(element);
        return getTypeNamesInPackage(packageElement).collect(Collectors.toList());
    }
    
    private PackageElement getPackageOf(Element element) {
        
        return elementUtils.getPackageOf(element);
    }
    
    private List<String> combineTypes(List<String> typesInSamePackage, List<String> importedTypes) {
        
        final List<String> result = new ArrayList<>(typesInSamePackage);
        result.addAll(importedTypes);
        return result;
    }
    
    private List<String> getImportedTypes(Element element) {
        
        final List<? extends ImportTree> importTreeList = getImportTreeList(element);
        
        return convertImportTreeList(importTreeList);
    }
    
    private List<? extends ImportTree> getImportTreeList(Element element) {
        
        final TreePath path = trees.getPath(element);
        final CompilationUnitTree compilationUnit = path.getCompilationUnit();
        return compilationUnit.getImports();
    }
    
    private List<String> convertImportTreeList(List<? extends ImportTree> importTreeList) {
        
        return importTreeList.stream()
                .map(this::qualifyImportStatement)
                .sorted(wildcardImportsLast())
                .flatMap(this::getAllImportedTypesFor)
                .collect(Collectors.toList());
    }
    
    private Comparator<? super String> wildcardImportsLast() {
        
        return Comparator.comparing(this::isWildcardImport);
    }
    
    private boolean isWildcardImport(String name) {
        
        return name.endsWith("*");
    }
    
    private String qualifyImportStatement(ImportTree tree) {
        
        final Tree qualifiedIdentifier = tree.getQualifiedIdentifier();
        return qualifiedIdentifier.toString();
    }
    
    private Stream<? extends String> getAllImportedTypesFor(String name) {
        
        if(isWildcardImport(name)) {
            return getAllImportedTypesForWildcard(name);
        } else {
            return Stream.of(name);
        }
    }
    
    private Stream<? extends String> getAllImportedTypesForWildcard(String name) {
        
        final PackageElement packageElement = getImportedPackageForWildcard(name);
        return getTypeNamesInPackage(packageElement);
    }
    
    private PackageElement getImportedPackageForWildcard(String name) {
        
        final String packageName = name.substring(0, name.lastIndexOf('.'));
        final PackageElement packageElement = getPackageWithName(packageName);
        validatePackageExists(packageName);
        return packageElement;
    }
    
    private PackageElement getPackageWithName(String packageName) {
        
        return elementUtils.getPackageElement(packageName);
    }
    
    private void validatePackageExists(String name) {
        
        if(getPackageWithName(name) == null) {
            throw new IllegalArgumentException("Cannot find package " + name);
        }
    }
    
    private Stream<? extends String> getTypeNamesInPackage(PackageElement packageElement) {
        
        final List<? extends Element> enclosedElements = packageElement.getEnclosedElements();
        return enclosedElements.stream().map(Objects::toString);
    }
    
}
