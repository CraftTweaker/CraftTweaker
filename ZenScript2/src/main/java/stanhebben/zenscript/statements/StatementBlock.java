package stanhebben.zenscript.statements;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

public class StatementBlock extends Statement {

    private final List<Statement> statements;

    public StatementBlock(ZenPosition position, List<Statement> statements) {
        super(position);

        this.statements = statements;
    }

    @Override
    public void compile(IEnvironmentMethod environment) {
        IEnvironmentMethod local = new EnvironmentScope(environment);
        for(Statement statement : statements) {
            statement.compile(local);
            if(statement.isReturn()) {
                return;
            }
        }
    }
}
