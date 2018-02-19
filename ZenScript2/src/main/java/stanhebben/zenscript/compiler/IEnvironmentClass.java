package stanhebben.zenscript.compiler;

import org.objectweb.asm.ClassVisitor;

/**
 * @author Stan
 */
public interface IEnvironmentClass extends IEnvironmentGlobal {
    
    ClassVisitor getClassOutput();
}
