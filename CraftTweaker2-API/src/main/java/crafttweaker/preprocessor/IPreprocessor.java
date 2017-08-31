package crafttweaker.preprocessor;

import crafttweaker.runtime.ScriptFile;

public interface IPreprocessor {
    String getPreprocessorName();

    /**
     * Gets executed directly on find
     */
    void executeActionOnFind(ScriptFile scriptFile);
    
    /**
     * Gets executed after all preprocessor actions have been collected
     */
    void executeActionOnFinish(ScriptFile scriptFile);
    
    String getPreprocessorLine();
    
    String getFileName();
    
    int getLineIndex();
}
