package crafttweaker.runtime.providers;

import crafttweaker.runtime.*;

import java.util.*;

/**
 * @author Stan Hebben
 */
public class ScriptProviderCascade implements IScriptProvider {
    
    private final IScriptProvider[] providers;
    
    public ScriptProviderCascade(IScriptProvider... providers) {
        this.providers = providers;
    }
    
    @Override
    public Iterator<IScriptIterator> getScripts() {
        return new MyScripterator();
    }
    
    private class MyScripterator implements Iterator<IScriptIterator> {
        
        private final Set<String> executed = new HashSet<>();
        
        private int currentIndex = providers.length - 1;
        private Iterator<IScriptIterator> current;
        private IScriptIterator currentValue;
        
        public MyScripterator() {
            currentIndex = providers.length - 1;
            current = providers[currentIndex].getScripts();
            
            advance();
        }
        
        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }
        
        @Override
        public IScriptIterator next() {
            IScriptIterator result = currentValue;
            executed.add(result.getGroupName());
            
            advance();
            
            return result;
        }
        
        private void advance() {
            do {
                while(!current.hasNext()) {
                    currentIndex--;
                    if(currentIndex < 0)
                        return;
                    
                    current = providers[currentIndex].getScripts();
                }
                if(currentIndex < 0)
                    break;
                
                currentValue = current.next();
            } while(executed.contains(currentValue.getGroupName()) && hasNext());
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
