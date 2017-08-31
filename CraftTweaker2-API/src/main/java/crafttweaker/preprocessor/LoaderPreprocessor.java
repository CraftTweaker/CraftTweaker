package crafttweaker.preprocessor;

import crafttweaker.runtime.ScriptFile;

/**
 * @author BloodWorkXGaming
 */
public class LoaderPreprocessor extends PreprocessorActionBase{
    public static final String PREPROCESSOR_NAME = "loader";
    private String loaderName;
    
    public LoaderPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        
        String s = preprocessorLine.substring(7);
        loaderName = s.trim();
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        scriptFile.setLoaderName(loaderName);
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
