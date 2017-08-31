package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;

/**
 * @author BloodWorkXGaming
 */
public class DebugPreprocessor extends PreprocessorActionBase{
    public DebugPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind() {
        CraftTweakerAPI.logInfo("Debug Preprocessor found in " + fileName + ", enabling debugmode");
        CraftTweakerAPI.tweaker.enableDebug();
    }
}
