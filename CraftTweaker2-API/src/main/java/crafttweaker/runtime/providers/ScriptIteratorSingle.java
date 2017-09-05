package crafttweaker.runtime.providers;

import crafttweaker.runtime.IScriptIterator;

import java.io.*;

/**
 * @author Stan
 */
public class ScriptIteratorSingle implements IScriptIterator {
    
    private final File file;
    private File directory;
    private boolean first = true;
    
    public ScriptIteratorSingle(File file) {
        this.file = file;
    }
    
    public ScriptIteratorSingle(File file, File directory) {
        this.file = file;
        this.directory = directory;
    }
    
    @Override
    public String getGroupName() {
        if(file != null && directory != null) {
            return file.getAbsolutePath().substring(directory.getAbsolutePath().length() + 1);
        } else if(file != null && file.getAbsolutePath().contains("scripts")) {
            return file.getAbsolutePath().split("scripts")[1];
        } else if (file != null){
            return file.getAbsolutePath();
        } else {
            return "invalid_group_name";
        }
    }
    
    @Override
    public boolean next() {
        if(first) {
            first = false;
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String getName() {
        return file.getName();
    }
    
    @Override
    public InputStream open() throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }
    
    @Override
    public IScriptIterator copyCurrent() {
        return new ScriptIteratorSingle(file, directory);
    }
}
