package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public interface ICastingRule {

    void compile(IEnvironmentMethod method);

    ZenType getInputType();

    ZenType getResultingType();
}
