package crafttweaker.socket;

public class SingleError {
    public String fileName;
    public int line;
    public int offset;
    public String explanation;
    public Level level;
    
    public SingleError(String fileName, int line, int offset, String explanation, Level level) {
        this.fileName = fileName;
        this.line = line;
        this.offset = offset;
        this.explanation = explanation == null ? "NO MESSAGE PROVIDED" : explanation;
        this.level = level;
    }
    
    @Override
    public String toString() {
        return "SingleError{" + "fileName='" + fileName + '\'' + ", line=" + line + ", offset=" + offset + ", explanation='" + explanation + '\'' + ", level=" + level + '}';
    }
    
    public enum Level {
        INFO,
        WARN,
        ERROR
    }
}
