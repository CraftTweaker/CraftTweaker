package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;

/**
 * @author BloodWorkXGaming
 */
public class NoRunPreprocessor extends PreprocessorActionBase{
    public NoRunPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public PreprocessorActionBase createPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        return new NoRunPreprocessor(fileName, preprocessorLine, lineIndex);
    }
}
