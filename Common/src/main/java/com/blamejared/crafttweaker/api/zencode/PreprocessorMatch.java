package com.blamejared.crafttweaker.api.zencode;

public class PreprocessorMatch {
    
    private final IPreprocessor preprocessor;
    private final int line;
    private final String content;
    
    public PreprocessorMatch(IPreprocessor preprocessor, int line, String content) {
        
        this.preprocessor = preprocessor;
        this.line = line;
        this.content = content;
    }
    
    /**
     * The line in which the original Preprocessor was found.
     * May have been modified by another preprocessor
     *
     * If you need the content of the preprocessor call, {@link #getContent()}
     *
     * The line numbers are 1-Based, i.e. the first line has getLine() == 1
     */
    public int getLine() {
        
        return line;
    }
    
    /**
     * The content of the line at the time the preprocessor was found
     *
     * Careful, this starts <strong>after</strong> the preprocessor call
     * So {@code "bb#name cc dd"} would have {@code "cc dd"} as content
     */
    public String getContent() {
        
        return content;
    }
    
    /**
     * The Preprocessor this match belongs to
     * Usually not required, but you never know...
     */
    public IPreprocessor getPreprocessor() {
        
        return preprocessor;
    }
    
}
