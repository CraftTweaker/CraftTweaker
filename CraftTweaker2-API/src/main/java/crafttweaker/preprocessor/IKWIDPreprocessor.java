package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * Putting the #ikwid preprocessor on a script file will make it that Logger warnings and errors aren't printed to the player chat
 * @author BloodWorkXGaming
 */
public class IKWIDPreprocessor extends PreprocessorActionBase{
    private static final String PREPROCESSOR_NAME = "ikwid";
    
    public IKWIDPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.suppressWarnAndErrorFlag |= 0b11;
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
