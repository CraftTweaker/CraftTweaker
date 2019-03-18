package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * Putting the #profile preprocessor on a script file will profile the script actions
 *
 * @author Jared
 */
public class ProfilePreprocessor extends PreprocessorActionBase {
    
    private static final String PREPROCESSOR_NAME = "profile";
    
    public ProfilePreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.profile = true;
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
