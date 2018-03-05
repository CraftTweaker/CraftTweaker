package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * Putting the #ikwid preprocessor on a script file will make it that Logger warnings aren't printed to the player chat
 * @author BloodWorkXGaming
 */
public class NoWarnPreprocessor extends PreprocessorActionBase{
    private static final String PREPROCESSOR_NAME = "ikwid";
    
    public NoWarnPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.noWarn = true;
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
