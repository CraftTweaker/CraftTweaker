package stanhebben.zenscript.docs;

import com.sun.javadoc.*;

/**
 * @author Stan
 */
public class ZenParameterDoc {

    private final String name;
    private final Type type;
    private boolean optional;

    public ZenParameterDoc(Parameter parameter) {
        optional = false;
        name = parameter.name();
        type = parameter.type();

        for(AnnotationDesc annotation : parameter.annotations()) {
            if(annotation.annotationType().qualifiedName().equals("stanhebben.zenscript.annotations.Optional")) {
                optional = true;
            }
        }
    }

    public boolean isOptional() {
        return optional;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
