package stanhebben.zenscript.util;

import org.objectweb.asm.ClassVisitor;

/**
 * @author Stan
 */
public interface IAnyDefinition {
    
    void defineMembers(ClassVisitor output);
    
    void defineStaticCanCastImplicit(MethodOutput output);
    
    void defineStaticAs(MethodOutput output);
    
    void defineNot(MethodOutput output);
    
    void defineNeg(MethodOutput output);
    
    void defineAdd(MethodOutput output);
    
    void defineSub(MethodOutput output);
    
    void defineCat(MethodOutput output);
    
    void defineMul(MethodOutput output);
    
    void defineDiv(MethodOutput output);
    
    void defineMod(MethodOutput output);
    
    void defineAnd(MethodOutput output);
    
    void defineOr(MethodOutput output);
    
    void defineXor(MethodOutput output);
    
    void defineRange(MethodOutput output);
    
    void defineCompareTo(MethodOutput output);
    
    void defineContains(MethodOutput output);
    
    void defineMemberGet(MethodOutput output);
    
    void defineMemberSet(MethodOutput output);
    
    void defineMemberCall(MethodOutput output);
    
    void defineIndexGet(MethodOutput output);
    
    void defineIndexSet(MethodOutput output);
    
    void defineCall(MethodOutput output);
    
    void defineAsBool(MethodOutput output);
    
    void defineAsByte(MethodOutput output);
    
    void defineAsShort(MethodOutput output);
    
    void defineAsInt(MethodOutput output);
    
    void defineAsLong(MethodOutput output);
    
    void defineAsFloat(MethodOutput output);
    
    void defineAsDouble(MethodOutput output);
    
    void defineAsString(MethodOutput output);
    
    void defineAs(MethodOutput output);
    
    void defineIs(MethodOutput output);
    
    void defineGetNumberType(MethodOutput output);
    
    void defineIteratorSingle(MethodOutput output);
    
    void defineIteratorMulti(MethodOutput output);
    
    void defineEquals(MethodOutput output);
    
    void defineHashCode(MethodOutput output);
}
