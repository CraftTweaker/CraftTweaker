package stanhebben.zenscript.definitions;

import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

/**
 * @author Stanneke
 */
public class Import {

    private final ZenPosition position;
    private final List<String> name;
    private final String rename;

    public Import(ZenPosition position, List<String> name, String rename) {
        this.position = position;
        this.name = name;
        this.rename = rename;
    }

    public ZenPosition getPosition() {
        return position;
    }

    public List<String> getName() {
        return name;
    }

    public String getRename() {
        return rename == null ? name.get(name.size() - 1) : rename;
    }
}
