package crafttweaker.mc1120.preprocessors;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.preprocessor.PreprocessorActionBase;
import crafttweaker.runtime.ScriptFile;
import net.minecraftforge.fml.common.Loader;

import java.util.*;

/**
 * Preprocessor can be used as follows, ! inverts the mod
 * #modloaded modid1 modid2 !modid3
 * example:
 * #modloaded minecraft tconstruct !railcraft
 * only gets loaded when tconstruct AND minecraft (which is always :P) are loaded BUT NOT railcraft
 *
 * @author BloodWorkXGaming
 */
public class ModLoadedPreprocessor extends PreprocessorActionBase {
    private static final String PREPROCESSOR_NAME = "modloaded";
    
    private List<LoadedMod> modNames = new ArrayList<>();
    
    public ModLoadedPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        String s = preprocessorLine.substring(10);
        String[] names = s.split(" ");
        
        for(String name : names) {
            String n = name.trim();
            if (n.length() > 0){
                boolean shouldBeLoaded = true;
                if (n.startsWith("!")) {
                    n = n.substring(1);
                    shouldBeLoaded = false;
                }
                
                modNames.add(new LoadedMod(n, shouldBeLoaded));
            }
        }
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        if (!checkAreLoaded(modNames)){
            CraftTweakerAPI.logInfo("Ignoring script " + scriptFile + " due to the following #modloaded preprocessor settings: " + modNames);
            
            scriptFile.setParsingBlocked(true);
            scriptFile.setCompileBlocked(true);
            scriptFile.setExecutionBlocked(true);
    
        }
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
    
    private boolean checkAreLoaded(List<LoadedMod> modNames){
        for(LoadedMod mod : modNames) {
            if (Loader.isModLoaded(mod.modName) != mod.shouldBeLoaded) return false;
        }
        
        return true;
    }
    
    private static class LoadedMod {
        String modName;
        boolean shouldBeLoaded;
    
        LoadedMod(String modName, boolean shouldBeLoaded) {
            this.modName = modName;
            this.shouldBeLoaded = shouldBeLoaded;
        }
    
        @Override
        public String toString() {
            return "{" + (shouldBeLoaded ? "" : "!") + modName + "}";
        }
    }
}
