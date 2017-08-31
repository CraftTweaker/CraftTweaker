package stanhebben.zenscript.compiler;

import stanhebben.zenscript.type.ZenType;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Stan
 */
public interface ITypeRegistry {
    
    ZenType getType(Type type);
}
