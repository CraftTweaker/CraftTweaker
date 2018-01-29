package crafttweaker.runtime.providers;

import crafttweaker.runtime.IScriptIterator;

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
    private final File directory;
    private ZipEntry current;
    
    private ScriptIteratorZip(File file, ZipFile zipFile, Iterator<ZipEntry> entries, File directory, ZipEntry current) {
        this.file = file;
        this.zipFile = zipFile;
        this.entries = entries;
        this.directory = directory;
        this.current = current;
    }
    
    public ScriptIteratorZip(File file) throws IOException {
        this.file = file;
        this.directory = null;
        
        zipFile = new ZipFile(file);
        List<ZipEntry> entriesList = new ArrayList<>();
        Enumeration<? extends ZipEntry> original = zipFile.entries();
        while(original.hasMoreElements()) {
            ZipEntry entry = original.nextElement();
            if(entry.getName().toLowerCase().endsWith(".zs")) {
                entriesList.add(entry);
            }
        }
        entries = entriesList.iterator();
    }
    
    public ScriptIteratorZip(File file, File directory) throws IOException {
        this.file = file;
        this.directory = directory;
        
        zipFile = new ZipFile(file);
        List<ZipEntry> entriesList = new ArrayList<>();
        Enumeration<? extends ZipEntry> original = zipFile.entries();
        while(original.hasMoreElements()) {
            ZipEntry entry = original.nextElement();
            if(entry.getName().toLowerCase().endsWith(".zs")) {
                entriesList.add(entry);
            }
        }
        entries = entriesList.iterator();
    }
    
    public String getCurrentName() {
        return current.getName();
    }
    
    @Override
    public String getGroupName() {
        if(file != null && directory != null) {
            return file.getAbsolutePath().substring(directory.getAbsolutePath().length() + 1);
        }
        return file != null ? file.getName() : "invalid_group_name";
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
        return current.getName().split("/")[current.getName().split("/").length - 1];
    }
    
    @Override
    public InputStream open() throws IOException {
        return zipFile.getInputStream(current);
    }
    
    @Override
    public IScriptIterator copyCurrent() {
        return new ScriptIteratorZip(file, zipFile, entries, directory, current);
    }
}
