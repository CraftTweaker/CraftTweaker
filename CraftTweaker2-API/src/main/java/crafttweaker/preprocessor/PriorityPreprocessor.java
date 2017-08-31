package crafttweaker.preprocessor;

import crafttweaker.runtime.ScriptFile;

/**
 * @author BloodWorkXGaming
 */
public class PriorityPreprocessor extends PreprocessorActionBase{
    public static final String PREPROCESSOR_NAME = "priority";
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
