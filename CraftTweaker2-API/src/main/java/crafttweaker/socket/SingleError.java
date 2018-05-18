package crafttweaker.socket;

public class SingleError {
    public String fileName;
    public String line;
    public int offset;
    public String explanation;
    
    public SingleError(String fileName, String line, int offset, String explanation) {
        this.fileName = fileName;
        this.line = line;
        this.offset = offset;
        this.explanation = explanation;
    }
    
    @Override
    public String toString() {
        return "SingleError{" + "fileName='" + fileName + '\'' + ", line='" + line + '\'' + ", offset=" + offset + ", explanation='" + explanation + '\'' + '}';
    }
}
