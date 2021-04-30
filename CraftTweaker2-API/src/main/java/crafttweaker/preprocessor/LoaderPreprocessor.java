package crafttweaker.preprocessor;

import java.util.HashSet;
import java.util.Set;

import crafttweaker.runtime.ScriptFile;

/**
 * Preprocessor can be used as follows:
 * #loader loadername1 loadername2 ...
 * 
 * Example:
 * #loader contenttweaker
 * This will make scripts only being loaded when the loader is specified to load
 * contenttweaker scripts
 * 
 * Example:
 * #loader preinit gregtech
 * This will make scripts only being loaded when the loader is specified to load
 * preinit or gregtech scripts
 *
 * this defaults to being solely "crafttweaker" which is being called by CraftTweaker
 *
 * @author BloodWorkXGaming
 */
public class LoaderPreprocessor extends PreprocessorActionBase{
    private static final String PREPROCESSOR_NAME = "loader";
    private String[] loaderNames;
    
    public LoaderPreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        
        // Regex magic to split substring
        String substring = preprocessorLine.substring(7).trim();
        String[] arr = substring.split("\\s+");
        
        // Remove duplicates and empty strings
        Set<String> set = new HashSet<>();
        for (String s : arr) {
            String trim = s.trim();
            if (!trim.isEmpty()) {
                set.add(trim);
            }
        }
        loaderNames = set.toArray(new String[set.size()]);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        scriptFile.setLoaderNames(loaderNames);
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
