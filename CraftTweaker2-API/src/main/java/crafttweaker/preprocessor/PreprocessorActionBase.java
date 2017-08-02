package crafttweaker.preprocessor;

/**
 * @author BloodWorkXGaming
 */
public abstract class PreprocessorActionBase {
    protected String preprocessorLine;
    protected String fileName;
    protected int lineIndex;
    
    
    public PreprocessorActionBase(String fileName, String preprocessorLine, int lineIndex){
        this.fileName = fileName;
        this.preprocessorLine = preprocessorLine;
        this.lineIndex = lineIndex;
    }
    
    /**
     * The only function that is called on the dummy Preprocessor Action,
     * must work without having to access the variables, mostly just a clean pass into the constructor
     */
    public abstract PreprocessorActionBase createPreprocessor(String fileName, String preprocessorLine, int lineIndex);
    
    /**
     * Gets executed directly on find
     */
    public void executeActionOnFind(){}
    
    /**
     * Gets executed after all preprocessor actions have been collected
     */
    public void executeActionOnFinish(){}
    
    
}
