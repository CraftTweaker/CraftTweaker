package crafttweaker.preprocessor;

import crafttweaker.runtime.ScriptFile;

/**
 * Preprocessor can be used as follows:
 * #priority number
 * The higher the number, the earlier it is getting executed
 * Scripts with the same priority get sorted alphabetically
 *
 * @author BloodWorkXGaming
 */
public class PriorityPreprocessor extends PreprocessorActionBase{
    private static final String PREPROCESSOR_NAME = "priority";
    private Integer priority;
    
    public PriorityPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        
        String s = preprocessorLine.substring(9).trim();
        
        try {
            priority = Integer.parseInt(s);
        }catch(NumberFormatException e){
            priority = null;
        }
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        if (priority != null) scriptFile.setPriority(priority);
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
