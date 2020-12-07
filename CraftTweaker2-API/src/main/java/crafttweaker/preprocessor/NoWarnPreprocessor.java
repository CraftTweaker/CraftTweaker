package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * Putting the #nowarn preprocessor on a script file will make it that Logger warnings aren't printed to the player chat
 * @author youyihj
 */
public class NoWarnPreprocessor extends PreprocessorActionBase {
    public static final String PREPROCESSOR_NAME = "nowarn";

    public NoWarnPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }

    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.suppressWarnAndErrorFlag |= 0b01;
    }

    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
