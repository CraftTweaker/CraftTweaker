package crafttweaker.preprocessor;

@FunctionalInterface
public interface PreprocessorFactory<R extends IPreprocessor> {
    R createPreprocessor(String fileName, String preprocessorLine, int lineIndex);
}
