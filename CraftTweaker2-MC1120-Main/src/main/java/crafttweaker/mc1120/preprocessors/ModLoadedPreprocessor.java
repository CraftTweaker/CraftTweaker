package crafttweaker.mc1120.preprocessors;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.preprocessor.PreprocessorActionBase;
import crafttweaker.runtime.ScriptFile;
import net.minecraftforge.fml.common.Loader;

import java.util.*;

/**
 * @author BloodWorkXGaming
 */
public class ModLoadedPreprocessor extends PreprocessorActionBase {
    public static final String PREPROCESSOR_NAME = "modloaded";
    
    private List<String> modNames = new ArrayList<>();
    
    
    public ModLoadedPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        String s = preprocessorLine.substring(10);
        String[] names = s.split(" ");
        
        for(String name : names) {
            String n = name.trim();
            if (n.length() > 0)
                modNames.add(name.trim());
        }
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        if (!checkAreLoaded(modNames)){
            CraftTweakerAPI.logInfo("Ignoring script " + scriptFile + " as the following mods weren't loaded " + Arrays.toString(modNames.toArray()));
            
            scriptFile.setParsingBlocked(true);
            scriptFile.setCompileBlocked(true);
            scriptFile.setExecutionBlocked(true);
    
        }
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
    
    private boolean checkAreLoaded(List<String> modNames){
        for(String modName : modNames) {
            if (!Loader.isModLoaded(modName)) return false;
        }
        
        return true;
    }
}
