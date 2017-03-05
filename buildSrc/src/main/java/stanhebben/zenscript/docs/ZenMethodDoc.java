package stanhebben.zenscript.docs;

import com.sun.javadoc.MethodDoc;

import java.util.List;

/**
 * @author Stan
 */
public class ZenMethodDoc {

    private final String name;
    private final MethodDoc method;
    private final List<ZenParameterDoc> parameters;

    public ZenMethodDoc(String name, MethodDoc method, List<ZenParameterDoc> parameters) {
        this.name = name;
        this.method = method;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public MethodDoc getDoc() {
        return method;
    }

    public List<ZenParameterDoc> getParameters() {
        return parameters;
    }
}
