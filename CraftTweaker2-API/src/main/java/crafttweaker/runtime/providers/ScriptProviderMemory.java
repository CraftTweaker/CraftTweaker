package crafttweaker.runtime.providers;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.*;
import crafttweaker.util.FileUtil;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * @author Stan
 */
public class ScriptProviderMemory implements IScriptProvider {
    
    private final Map<String, MemoryModule> modules;
    
    public ScriptProviderMemory(byte[] scripts) {
        modules = new HashMap<>();
        
        try {
            InflaterInputStream inflater = new InflaterInputStream(new ByteArrayInputStream(scripts));
            DataInputStream inflaterData = new DataInputStream(inflater);
            
            String moduleName = inflaterData.readUTF();
            while(moduleName.length() > 0) {
                List<MemoryFile> files = new ArrayList<>();
                
                String fileName = inflaterData.readUTF();
                while(fileName.length() > 0) {
                    byte[] data = new byte[inflaterData.readInt()];
                    inflaterData.readFully(data);
                    files.add(new MemoryFile(fileName, data));
                    
                    fileName = inflaterData.readUTF();
                }
                modules.put(moduleName, new MemoryModule(moduleName, files));
                
                moduleName = inflaterData.readUTF();
            }
            
            inflaterData.close();
        } catch(IOException ex) {
            CraftTweakerAPI.logError("Could not load transmitted scripts: " + ex.getMessage());
        }
    }
    
    public static byte[] collect(IScriptProvider provider) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            DeflaterOutputStream deflater = new DeflaterOutputStream(output);
            DataOutputStream deflaterData = new DataOutputStream(deflater);
            Set<String> executed = new HashSet<>();
            
            Iterator<IScriptIterator> scripts = provider.getScripts();
            while(scripts.hasNext()) {
                IScriptIterator script = scripts.next();
                
                if(!executed.contains(script.getGroupName())) {
                    executed.add(script.getGroupName());
                    
                    deflaterData.writeUTF(script.getGroupName());
                    
                    while(script.next()) {
                        String name = script.getName();
                        byte[] data = FileUtil.read(script.open());
                        if(data.length == 0)
                            continue; // skip empty files
                        
                        deflaterData.writeUTF(name);
                        deflaterData.writeInt(data.length);
                        deflaterData.write(data);
                    }
                    
                    deflaterData.writeUTF("");
                }
            }
            
            deflaterData.writeUTF("");
            
            deflater.close();
        } catch(IOException ex) {
            CraftTweakerAPI.logError("Could not collect scripts: " + ex.getMessage());
        }
        return output.toByteArray();
    }
    
    @Override
    public Iterator<IScriptIterator> getScripts() {
        return new ProviderIterator();
    }
    
    private class ProviderIterator implements Iterator<IScriptIterator> {
        
        private final Iterator<MemoryModule> baseIterator = modules.values().iterator();
        
        @Override
        public boolean hasNext() {
            return baseIterator.hasNext();
        }
        
        @Override
        public IScriptIterator next() {
            return new ScriptIterator(baseIterator.next());
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private static class ScriptIterator implements IScriptIterator {
        
        private final MemoryModule module;
        private final Iterator<MemoryFile> files;
        private MemoryFile current;
        
        public ScriptIterator(MemoryModule module) {
            this.module = module;
            files = module.data.iterator();
            current = null;
        }
    
        private ScriptIterator(MemoryModule module, Iterator<MemoryFile> files, MemoryFile current) {
            this.module = module;
            this.files = files;
            this.current = current;
        }
    
        @Override
        public String getGroupName() {
            return module.name;
        }
        
        @Override
        public boolean next() {
            if(files.hasNext()) {
                current = files.next();
                return true;
            } else {
                return false;
            }
        }
        
        @Override
        public String getName() {
            return current.name;
        }
        
        @Override
        public InputStream open() throws IOException {
            return new ByteArrayInputStream(current.data);
        }
    
        @Override
        public IScriptIterator copyCurrent() {
            return new ScriptIterator(module, files, current);
        }
    }
    
    private static class MemoryModule {
        
        private final String name;
        private final List<MemoryFile> data;
        
        public MemoryModule(String name, List<MemoryFile> data) {
            this.name = name;
            this.data = data;
        }
    }
    
    private static class MemoryFile {
        
        private final String name;
        private final byte[] data;
        
        public MemoryFile(String name, byte[] data) {
            this.name = name;
            this.data = data;
        }
    }
}
