package crafttweaker.preprocessor;

import crafttweaker.runtime.ScriptFile;

/**
 * @author BloodWorkXGaming
 */
public abstract class PreprocessorActionBase implements IPreprocessor {
    protected String preprocessorLine;
    protected String fileName;
    protected int lineIndex;
    
    
    public PreprocessorActionBase(String fileName, String preprocessorLine, int lineIndex){
        this.fileName = fileName;
        this.preprocessorLine = preprocessorLine;
        this.lineIndex = lineIndex;
    }
    
    /**
     * Gets executed directly on find
     */
    @Override
    public void executeActionOnFind(ScriptFile scriptFile){}
    
    /**
     * Gets executed after all preprocessor actions have been collected
     */
    @Override
    public void executeActionOnFinish(ScriptFile scriptFile){}
    
    @Override
    public String getPreprocessorLine() {
        return preprocessorLine;
    }
    
    @Override
    public String getFileName() {
        return fileName;
    }
    
    @Override
    public int getLineIndex() {
        return lineIndex;
    }
}
