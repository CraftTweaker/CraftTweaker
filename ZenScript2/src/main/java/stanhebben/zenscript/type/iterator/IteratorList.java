package stanhebben.zenscript.type.iterator;

import org.objectweb.asm.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.MethodOutput;

import java.util.Iterator;

/**
 * @author Stan
 */

public class IteratorList implements IZenIterator {

    private final MethodOutput methodOutput;
    private final ZenType iteratorType;
    private int iterator;

    public IteratorList(MethodOutput methodOutput, ZenType iteratorType) {
        this.methodOutput = methodOutput;
        this.iteratorType = iteratorType;
    }

    @Override
    public void compileStart(int[] locals) {
        iterator = methodOutput.local(Type.getType(Iterator.class));
        methodOutput.invokeInterface(Iterable.class, "iterator", Iterator.class);
        methodOutput.storeObject(iterator);
        methodOutput.iConst0();
        methodOutput.storeInt(locals[0]);
    }

    @Override
    public void compilePreIterate(int[] locals, Label exit) {
        methodOutput.loadObject(iterator);
        methodOutput.invokeInterface(Iterator.class, "hasNext", boolean.class);
        methodOutput.ifEQ(exit);

        methodOutput.loadObject(iterator);
        methodOutput.invokeInterface(Iterator.class, "next", Object.class);
        methodOutput.checkCast(iteratorType.toASMType().getInternalName());
        methodOutput.store(iteratorType.toASMType(), locals[1]);
    }

    @Override
    public void compilePostIterate(int[] locals, Label exit, Label repeat) {
        methodOutput.iinc(locals[0]);
        methodOutput.goTo(repeat);
    }

    @Override
    public void compileEnd() {
    }

    @Override
    public ZenType getType(int i) {
        return i == 0 ? ZenType.INT : iteratorType;
    }
}
