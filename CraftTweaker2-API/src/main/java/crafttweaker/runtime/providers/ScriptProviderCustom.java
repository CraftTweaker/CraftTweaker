package crafttweaker.runtime.providers;

import crafttweaker.runtime.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author Stan
 */
public class ScriptProviderCustom implements IScriptProvider {
    
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    private final String moduleName;
    private final List<CustomScript> scripts;
    
    public ScriptProviderCustom(String moduleName) {
        this.moduleName = moduleName;
        scripts = new ArrayList<>();
    }
    
    public void add(String name, byte[] content) {
        scripts.add(new CustomScript(name, content));
    }
    
    public void add(String name, String content) {
        add(name, content.getBytes(UTF8));
    }
    
    @Override
    public Iterator<IScriptIterator> getScripts() {
        
        return Collections.<IScriptIterator> singleton(new CustomScriptIterator()).iterator();
    }
    
    private static class CustomScript {
        
        private final String name;
        private final byte[] content;
        
        public CustomScript(String name, byte[] content) {
            this.name = name;
            this.content = content;
        }
    }
    
    private class CustomScriptIterator implements IScriptIterator {
        
        private final Iterator<CustomScript> iterator = scripts.iterator();
        private CustomScript current;
    
        public CustomScriptIterator() {}
        
        private CustomScriptIterator(CustomScript current) {
            this.current = current;
        }
    
        @Override
        public String getGroupName() {
            return moduleName;
        }
        
        @Override
        public boolean next() {
            if(iterator.hasNext()) {
                current = iterator.next();
                return true;
            } else {
                return false;
            }
        }
        
        @Override
        public String getName() {
            if(current == null) {
                return "zzNullzz";
            }
            return current.name;
        }
        
        @Override
        public InputStream open() {
            return new ByteArrayInputStream(current.content);
        }
    
        @Override
        public IScriptIterator copyCurrent() {
            return new CustomScriptIterator(current);
        }
    }
}
