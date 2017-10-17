package crafttweaker.preprocessor;

import crafttweaker.runtime.ScriptFile;

/**
 * Scripts with the
 * #norun
 * Preprocessor are getting totally ignored and are not being run
 * Syntax command bypasses the not loading of the script
 *
 * @author BloodWorkXGaming
 */
public class NoRunPreprocessor extends PreprocessorActionBase{
    private static final String PREPROCESSOR_NAME = "norun";
    
    public NoRunPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        scriptFile.setParsingBlocked(true);
        scriptFile.setCompileBlocked(true);
        scriptFile.setExecutionBlocked(true);
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
