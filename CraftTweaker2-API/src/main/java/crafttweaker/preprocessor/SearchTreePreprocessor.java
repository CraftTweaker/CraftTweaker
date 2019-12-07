package crafttweaker.preprocessor;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.ScriptFile;

/**
 * Adding the
 * #disable_search_tree
 * Preprocessor will disable recipe tree recalculation which may speed up game loading.
 *
 */
public class SearchTreePreprocessor extends PreprocessorActionBase{
    public static final String PREPROCESSOR_NAME = "disable_search_tree";
    
    public SearchTreePreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
    }
    
    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        CraftTweakerAPI.logInfo("disable_search_tree Preprocessor found in " + scriptFile + ", disabling the search_tree");
        CraftTweakerAPI.ENABLE_SEARCH_TREE_RECALCULATION = false;
    }
    
    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
