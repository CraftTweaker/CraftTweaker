package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * @author BloodWorkXGaming
 */
public class IgnoreBracketErrorPreprocessor extends PreprocessorActionBase{
    public IgnoreBracketErrorPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.logInfo("IgnoreBracketErrorPreprocessor found in " + fileName + ", ignoring errors in that file");
        CraftTweakerAPI.tweaker.addFileToIgnoreBracketErrors(fileName);
    }
}
