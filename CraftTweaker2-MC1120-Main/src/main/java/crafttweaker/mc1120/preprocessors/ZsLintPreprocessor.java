package crafttweaker.mc1120.preprocessors;

import crafttweaker.preprocessor.PreprocessorActionBase;
import crafttweaker.runtime.ScriptFile;
import crafttweaker.socket.CrTSocketHandler;

public class ZsLintPreprocessor extends PreprocessorActionBase {
    private static final String PREPROCESSOR_NAME = "zslint";
    
    public ZsLintPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFinish(ScriptFile scriptFile) {
        CrTSocketHandler.enableSocket();
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
