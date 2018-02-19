package stanhebben.zenscript.compiler;

/**
 * @author Stanneke
 */
public class ClassNameGenerator {
    
    private int counter = 0;
    
    public ClassNameGenerator() {
        
    }
    
    public String generate() {
        return "ZenClass" + counter++;
    }
    
    public String generate(String prefix) {
        return prefix + (counter++);
    }
}
