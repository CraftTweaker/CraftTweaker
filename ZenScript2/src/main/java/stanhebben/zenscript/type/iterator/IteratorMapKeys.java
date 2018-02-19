package stanhebben.zenscript.type.iterator;

import org.objectweb.asm.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenTypeUtil;

import java.util.*;

/**
 * @author Stan
 */
public class IteratorMapKeys implements IZenIterator {

    private final MethodOutput methodOutput;
    private final ZenTypeAssociative type;
    private int iterator;

    public IteratorMapKeys(MethodOutput methodOutput, ZenTypeAssociative type) {
        this.methodOutput = methodOutput;
        this.type = type;
    }

    @Override
    public void compileStart(int[] locals) {
        methodOutput.invokeInterface(Map.class, "keySet", Set.class);

        iterator = methodOutput.local(Type.getType(Iterator.class));
        methodOutput.invokeInterface(Set.class, "iterator", Iterator.class);
        methodOutput.storeObject(iterator);
    }

    @Override
    public void compilePreIterate(int[] locals, Label exit) {
        methodOutput.loadObject(iterator);
        methodOutput.invokeInterface(Iterator.class, "hasNext", boolean.class);
        methodOutput.ifEQ(exit);

        methodOutput.loadObject(iterator);
        methodOutput.invokeInterface(Iterator.class, "next", Object.class);
        methodOutput.checkCast(ZenTypeUtil.internal(type.getKeyType().toJavaClass()));
        methodOutput.store(type.getKeyType().toASMType(), locals[0]);
    }

    @Override
    public void compilePostIterate(int[] locals, Label exit, Label repeat) {
        methodOutput.goTo(repeat);
    }

    @Override
    public void compileEnd() {

    }

    @Override
    public ZenType getType(int i) {
        return type.getKeyType();
    }
}
