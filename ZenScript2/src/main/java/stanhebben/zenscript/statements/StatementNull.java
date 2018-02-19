package stanhebben.zenscript.statements;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class StatementNull extends Statement {

    public StatementNull(ZenPosition position) {
        super(position);
    }

    @Override
    public void compile(IEnvironmentMethod environment) {

    }
}
