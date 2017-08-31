package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * Adding the
 * #ignoreBracketError
 * will make the script ignore any bracket errors which happen at compile time
 *
 * @author BloodWorkXGaming
 */
public class IgnoreBracketErrorPreprocessor extends PreprocessorActionBase{
    public static final String PREPROCESSOR_NAME = "ignoreBracketError";
    
    
    public IgnoreBracketErrorPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.logInfo("IgnoreBracketErrorPreprocessor found in " + scriptFile + ", ignoring errors in that file");
        scriptFile.setIgnoreBracketErrors(true);
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
