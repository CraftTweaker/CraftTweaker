package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * Adding the
 * #debug
 * Preprocessor will make this script generate the .class file which it normally just has under the hood
 *
 * @author BloodWorkXGaming
 */
public class DebugPreprocessor extends PreprocessorActionBase{
    public static final String PREPROCESSOR_NAME = "debug";
    
    public DebugPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.logInfo("Debug Preprocessor found in " + scriptFile + ", enabling debugmode");
        scriptFile.setDebugEnabled(true);
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
