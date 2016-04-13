package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialGlobal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class SymbolGlobal implements IZenSymbol {
    private final String owner;
    private final String name;
    private final ZenType type;

    public SymbolGlobal(String owner, String name, ZenType type) {
        this.owner = owner;
        this.name = name;
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public ZenType getType() {
        return type;
    }

    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new PartialGlobal(position, this);
    }
}
