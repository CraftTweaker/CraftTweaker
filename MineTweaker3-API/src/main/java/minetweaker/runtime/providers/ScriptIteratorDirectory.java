package minetweaker.runtime.providers;

import minetweaker.runtime.IScriptIterator;

import java.io.*;
import java.util.*;

/**
 * @author Stan
 */
public class ScriptIteratorDirectory implements IScriptIterator {

    private final File directory;
    private Iterator<File> contents;
    private File current;

    public ScriptIteratorDirectory(File directory) {
        this.directory = directory;
        List<File> contentsList = new ArrayList<>();
        if(directory.exists()) {
            iterate(directory, contentsList);
        }
        contents = contentsList.iterator();
    }

    private static void iterate(File directory, List<File> contentsList) {
        for(File file : directory.listFiles()) {
        	if(file.isDirectory()) {
                iterate(file, contentsList);
            } else if(file.isFile() && file.getName().endsWith(".zs")) {
                contentsList.add(file);
            }
        }
    }

    @Override
    public String getGroupName() {
        return directory.getName();
    }

    @Override
    public boolean next() {
        if(contents.hasNext()) {
            current = contents.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        if(current == null || directory == null) {
            return "zzNullzz";
        }
        return current.getName();
        //return current.getAbsolutePath().substring(directory.getAbsolutePath().length());
    }

    @Override
    public InputStream open() throws IOException {
        return new BufferedInputStream(new FileInputStream(current));
    }
}
