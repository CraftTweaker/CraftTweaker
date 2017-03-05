package stanhebben.zenscript.type.casting;

import org.objectweb.asm.*;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.MethodOutput;

/**
 * @author Stan
 */
public class CastingRuleArrayArray implements ICastingRule {

    private final ICastingRule base;
    private final ZenTypeArrayBasic from;
    private final ZenTypeArrayBasic to;

    public CastingRuleArrayArray(ICastingRule base, ZenTypeArrayBasic from, ZenTypeArrayBasic to) {
        this.base = base;
        this.from = from;
        this.to = to;
    }

    @Override
    public void compile(IEnvironmentMethod method) {
        MethodOutput output = method.getOutput();

        Type fromType = from.getBaseType().toASMType();
        Type toType = to.getBaseType().toASMType();

        int result = output.local(to.toASMType());

        output.dup();
        output.arrayLength();
        output.newArray(toType);
        output.storeObject(result);

        output.iConst0();

        Label lbl = new Label();
        output.label(lbl);

        // stack: original index
        output.dupX1();
        output.dupX1();
        output.arrayLoad(fromType);

        // stack: original index value
        if(base != null)
            base.compile(method);

        output.loadObject(result);
        output.dupX2();
        output.dupX2();
        output.arrayStore(toType);
        output.pop();

        // stack: original index
        output.iConst1();
        output.iAdd();
        output.dupX1();
        output.arrayLength();
        output.ifICmpGE(lbl);

        output.pop();
        output.pop();

        output.loadObject(result);
    }

    @Override
    public ZenType getInputType() {
        return from;
    }

    @Override
    public ZenType getResultingType() {
        return to;
    }
}
