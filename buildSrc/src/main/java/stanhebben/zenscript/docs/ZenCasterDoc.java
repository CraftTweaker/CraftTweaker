package stanhebben.zenscript.docs;

import com.sun.javadoc.MethodDoc;

/**
 * @author Stan
 */
public class ZenCasterDoc {

    private final MethodDoc method;

    public ZenCasterDoc(MethodDoc method) {
        this.method = method;
    }

    public MethodDoc getMethod() {
        return method;
    }
}
