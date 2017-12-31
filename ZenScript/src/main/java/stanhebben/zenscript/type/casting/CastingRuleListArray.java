package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeArrayBasic;
import stanhebben.zenscript.type.ZenTypeArrayList;

/**
 * @author Stan
 */
public class CastingRuleListArray implements ICastingRule {

    private final ICastingRule base;
    private final ZenTypeArrayList from;
    private final ZenTypeArrayBasic to;

    public CastingRuleListArray(ICastingRule base, ZenTypeArrayList from, ZenTypeArrayBasic to) {
        this.base = base;
        this.from = from;
        this.to = to;
    }

    @Override
    public void compile(IEnvironmentMethod method) {

        // TODO: implement this
        throw new UnsupportedOperationException("Not yet implemented");

        /*
         * Type fromType = componentFrom.toASMType(); Type toType =
         * componentTo.toASMType();
         *
         * int result = output.local(List.class); int iterator =
         * output.local(Iterator.class);
         *
         * // construct new list output.dup();
         *
         *
         * output.invoke(List.class, "iterator", void.class);
         * output.storeObject(iterator);
         *
         * Label loop = new Label(); output.label(loop);
         * output.loadObject(iterator);
         *
         *
         * if (base != null) base.compile(method);
         *
         *
         *
         * output.pop();
         *
         * output.loadObject(result);
         */
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
