package crafttweaker.preprocessor;

public interface PreprocessorFactory<R extends PreprocessorActionBase> {
    R createPreprocessor(String fileName, String preprocessorLine, int lineIndex);
}
