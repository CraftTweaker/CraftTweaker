package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;

/**
 * @author BloodWorkXGaming
 */
public class IgnoreBracketErrorPreprocessor extends PreprocessorActionBase{
    public IgnoreBracketErrorPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind() {
        CraftTweakerAPI.logInfo("IgnoreBracketErrorPreprocessor found in " + fileName + ", ignoring errors in that file");
        CraftTweakerAPI.tweaker.addFileToIgnoreBracketErrors(fileName);
    }
}
