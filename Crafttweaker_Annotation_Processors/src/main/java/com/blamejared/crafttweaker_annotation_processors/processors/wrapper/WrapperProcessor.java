package com.blamejared.crafttweaker_annotation_processors.processors.wrapper;

import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.IDontKnowHowToNameThisUtil;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SupportedAnnotationTypes({"net.minecraftforge.fml.common.Mod", "com.blamejared.crafttweaker_annotations.annotations.ZenWrapper"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class WrapperProcessor extends AbstractProcessor {
    private static final File location = new File("wrappedClasses");
    private static Collection<WrapperInfo> wrappedInfo = new HashSet<>();
    private static Collection<WrapperInfo> knownWrappers = new HashSet<>();
    private boolean firstRun = true;

    public static WrapperInfo getWrapperInfoFor(TypeElement element) {
        return knownWrappers.stream()
                .filter(knownWrapper -> element.getQualifiedName().contentEquals(knownWrapper.getWrappedClass()))
                .findFirst()
                .orElse(null);
    }

    public static WrapperInfo getWrapperInfoFor(TypeMirror returnType) {
        return knownWrappers.stream()
                .filter(knownWrapper -> returnType.toString().contentEquals(knownWrapper.getWrappedClass()))
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


        /*
        final Element getPrecipitation = processingEnv.getElementUtils()
                .getTypeElement("net.minecraft.world.biome.Biome")
                .getEnclosedElements()
                .stream()
                .filter(e -> e.getSimpleName().contentEquals("getSkyColorByTemp"))
                .findAny()
                .orElse(null);

        if (getPrecipitation != null) {
            final String s = AdvancedDocCommentUtil.safeGetDocComment(processingEnv, getPrecipitation);

            System.out.println("-----------------------");
            System.out.println(s);
            System.out.println("-----------------------");
        }
         */

        for(final WrapperInfo next : wrappedInfo){
            System.out.printf("%n%n%n%n%n%n%n%n%n-----------------------%n");
            final StringWriter out = new StringWriter();
            WrappedClass.write(new PrintWriter(out), next, processingEnv);
            System.out.println(out.getBuffer().toString());
            System.out.printf("-----------------------%n%n%n%n%n%n%n%n%n");
        }

        return false;
    }

    private void readWrappedInfos(RoundEnvironment roundEnv) {
        if (!location.exists()) {
            //TODO: Testing only
            wrappedInfo.add(new WrapperInfo("net.minecraft.world.World", "com.blamejared.crafttweaker.impl.world.IWorld", "crafttweaker.api.world.IWorld", "vanilla/world/IWorld"));
            wrappedInfo.add(new WrapperInfo("net.minecraft.world.biome.Biome", "com.blamejared.crafttweaker.impl.IBiome", "crafttweaker.api.world.IBiome", "vanilla/world/IBiome"));
        } else {
            try (final BufferedReader reader = new BufferedReader(new FileReader(location))) {
                reader.lines().map(s -> s.split(";[ \\t]*", 3)).map(WrapperInfo::new).forEach(wrappedInfo::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        knownWrappers.addAll(wrappedInfo);

        for (Element element : roundEnv.getElementsAnnotatedWith(processingEnv.getElementUtils()
                .getTypeElement("com.blamejared.crafttweaker_annotations.annotations.ZenWrapper"))) {
            final ZenWrapper annotation = element.getAnnotation(ZenWrapper.class);

            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                knownWrappers.add(new WrapperInfo(annotation.wrappedClass(), typeElement.getQualifiedName()
                        .toString(), IDontKnowHowToNameThisUtil.getDocPath(typeElement)));
            } else {
                knownWrappers.add(new WrapperInfo(annotation.wrappedClass(), element.toString()));
            }
        }
    }
}
