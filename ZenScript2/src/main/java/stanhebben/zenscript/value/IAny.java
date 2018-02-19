package stanhebben.zenscript.value;

import java.util.Iterator;

/**
 * @author Stan Hebben
 */
public interface IAny {

    String NAME = "stanhebben/zenscript/value/IAny";

    int NUM_BYTE = 1;
    int NUM_SHORT = 2;
    int NUM_INT = 3;
    int NUM_LONG = 4;
    int NUM_FLOAT = 5;
    int NUM_DOUBLE = 6;

    IAny not();

    IAny neg();

    IAny add(IAny value);

    IAny sub(IAny value);

    IAny cat(IAny value);

    IAny mul(IAny value);

    IAny div(IAny value);

    IAny mod(IAny value);

    IAny and(IAny value);

    IAny or(IAny value);

    IAny xor(IAny value);

    IAny range(IAny value);

    int compareTo(IAny value);

    boolean contains(IAny value);

    IAny memberGet(String member);

    void memberSet(String member, IAny value);

    IAny memberCall(String member, IAny... values);

    IAny indexGet(IAny key);

    void indexSet(IAny key, IAny value);

    IAny call(IAny... values);

    boolean asBool();

    byte asByte();

    short asShort();

    int asInt();

    long asLong();

    float asFloat();

    double asDouble();

    String asString();

    <T> T as(Class<T> cls);

    boolean is(Class<?> cls);

    boolean canCastImplicit(Class<?> cls);

    int getNumberType();

    Iterator<IAny> iteratorSingle();

    Iterator<IAny[]> iteratorMulti(int n);
}
