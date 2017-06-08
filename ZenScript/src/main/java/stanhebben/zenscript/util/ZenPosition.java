package stanhebben.zenscript.util;

import stanhebben.zenscript.ZenParsedFile;

/**
 * @author Stanneke
 */
public class ZenPosition {
    
    private final ZenParsedFile file;
    private final int line;
    private final int offset;
    private final String fileNameFallback;
    public ZenPosition(ZenParsedFile file, int line, int offset, String fileNameFallback) {
        if(file != null && line <= 0)
            throw new IllegalArgumentException("Line must be positive");
        
        this.file = file;
        this.line = line;
        this.offset = offset;
        this.fileNameFallback = fileNameFallback;
    }
    
    public ZenParsedFile getFile() {
        return file;
    }
    
    public int getLine() {
        return line;
    }
    
    public int getLineOffset() {
        return offset;
    }
    
    @Override
    public String toString() {
        return (file == null ? (fileNameFallback == null ? "?" : fileNameFallback) : file.getFileName()) + ":" + Integer.toString(line);
    }
}
