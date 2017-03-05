package minetweaker.runtime.providers;

import minetweaker.runtime.IScriptIterator;

import java.io.*;

/**
 * @author Stan
 */
public class ScriptIteratorSingle implements IScriptIterator {

    private final File file;
    private boolean first = true;

    public ScriptIteratorSingle(File file) {
        this.file = file;
    }

    @Override
    public String getGroupName() {
        return file.getName();
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
}
