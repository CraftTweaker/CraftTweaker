package crafttweaker.preprocessor;

import crafttweaker.runtime.ScriptFile;

/**
 * Preprocessor can be used as follows:
 * #loader loadername
 * Example:
 * #loader contenttweaker
 * This will make scripts only being loaded when the loader is specified to load
 * contenttweaker scripts
 *
 * this defaults to being "crafttweaker" which is being called by CraftTweaker
 *
 * @author BloodWorkXGaming
 */
public class LoaderPreprocessor extends PreprocessorActionBase{
    private static final String PREPROCESSOR_NAME = "loader";
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
