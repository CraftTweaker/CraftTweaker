package stanhebben.zenscript.docs;

import com.sun.javadoc.ClassDoc;

/**
 * @author Stan
 */
public class ZenExpansionDoc {

    private final ClassDoc cls;
    private final String type;

    public ZenExpansionDoc(ClassDoc cls, String type) {
        this.cls = cls;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ClassDoc getDoc() {
        return cls;
    }
}
