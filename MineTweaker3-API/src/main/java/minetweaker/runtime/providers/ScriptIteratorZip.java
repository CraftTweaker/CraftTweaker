package minetweaker.runtime.providers;

import minetweaker.runtime.IScriptIterator;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * @author Stan
 */
public class ScriptIteratorZip implements IScriptIterator {

    private final File file;
    private final ZipFile zipFile;
    private final Iterator<ZipEntry> entries;
    private ZipEntry current;
    private final File directory;

    public ScriptIteratorZip(File file) throws IOException {
        this.file = file;
        this.directory = null;

        zipFile = new ZipFile(file);
        List<ZipEntry> entriesList = new ArrayList<ZipEntry>();
        Enumeration<? extends ZipEntry> original = zipFile.entries();
        while(original.hasMoreElements()) {
            ZipEntry entry = original.nextElement();
            if (entry.getName().endsWith(".zs")) {
                entriesList.add(entry);
            }
        }
        entries = entriesList.iterator();
    }
    
    public ScriptIteratorZip(File file, File directory) throws IOException {
        this.file = file;
        this.directory = directory;

        zipFile = new ZipFile(file);
        List<ZipEntry> entriesList = new ArrayList<ZipEntry>();
        Enumeration<? extends ZipEntry> original = zipFile.entries();
        while(original.hasMoreElements()) {
            ZipEntry entry = original.nextElement();
            if (entry.getName().endsWith(".zs")) {
                entriesList.add(entry);
            }
        }
        entries = entriesList.iterator();
    }

    @Override
    public String getGroupName() {
        if (file!=null && directory != null){
        	return file.getAbsolutePath().substring(directory.getAbsolutePath().length()+1);
        }
    	return file.getName();
    }

    @Override
    public boolean next() {
        if(entries.hasNext()) {
            current = entries.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        if(current == null || zipFile == null) {
            return "zzNullzz";
        }
    	return current.getName().split("/")[current.getName().split("/").length-1];
    }

    @Override
    public InputStream open() throws IOException {
        return zipFile.getInputStream(current);
    }
}
