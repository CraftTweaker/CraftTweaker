/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.util;

import java.util.Iterator;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import stanhebben.zenscript.type.natives.JavaMethod;
import static stanhebben.zenscript.util.ZenTypeUtil.EMPTY_REGISTRY;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;
import stanhebben.zenscript.value.IAny;

/**
 *
 * @author Stan
 */
public class AnyClassWriter {
	public static final JavaMethod METHOD_NOT = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "not");
	public static final JavaMethod METHOD_NEG = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "neg");
	public static final JavaMethod METHOD_ADD = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "add", IAny.class);
	public static final JavaMethod METHOD_CAT = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "cat", IAny.class);
	public static final JavaMethod METHOD_SUB = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "sub", IAny.class);
	public static final JavaMethod METHOD_MUL = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "mul", IAny.class);
	public static final JavaMethod METHOD_DIV = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "div", IAny.class);
	public static final JavaMethod METHOD_MOD = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "mod", IAny.class);
	public static final JavaMethod METHOD_AND = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "and", IAny.class);
	public static final JavaMethod METHOD_OR = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "or", IAny.class);
	public static final JavaMethod METHOD_XOR = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "xor", IAny.class);
	public static final JavaMethod METHOD_RANGE = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "range", IAny.class);
	public static final JavaMethod METHOD_COMPARETO = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "compareTo", IAny.class);
	public static final JavaMethod METHOD_CONTAINS = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "contains", IAny.class);
	public static final JavaMethod METHOD_MEMBERGET = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "memberGet", String.class);
	public static final JavaMethod METHOD_MEMBERSET = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "memberSet", String.class, IAny.class);
	public static final JavaMethod METHOD_MEMBERCALL = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "memberCall", String.class, IAny[].class);
	public static final JavaMethod METHOD_INDEXGET = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "indexGet", IAny.class);
	public static final JavaMethod METHOD_INDEXSET = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "indexSet", IAny.class, IAny.class);
	public static final JavaMethod METHOD_CALL = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "call", IAny[].class);
	public static final JavaMethod METHOD_ASBOOL = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asBool");
	public static final JavaMethod METHOD_ASBYTE = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asByte");
	public static final JavaMethod METHOD_ASSHORT = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asShort");
	public static final JavaMethod METHOD_ASINT = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asInt");
	public static final JavaMethod METHOD_ASLONG = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asLong");
	public static final JavaMethod METHOD_ASFLOAT = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asFloat");
	public static final JavaMethod METHOD_ASDOUBLE = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asDouble");
	public static final JavaMethod METHOD_ASSTRING = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "asString");
	public static final JavaMethod METHOD_AS = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "as", Class.class);
	public static final JavaMethod METHOD_IS = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "is", Class.class);
	public static final JavaMethod METHOD_CANCASTIMPLICIT = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "canCastImplicit", Class.class);
	public static final JavaMethod METHOD_GETNUMBERTYPE = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "getNumberType");
	public static final JavaMethod METHOD_ITERATORSINGLE = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "iteratorSingle");
	public static final JavaMethod METHOD_ITERATORMULTI = JavaMethod.get(EMPTY_REGISTRY, IAny.class, "iteratorMulti", int.class);
	
	private static final String SIG_ANY = "()" + signature(IAny.class);
	private static final String SIG_ANY_ANY = "(" + signature(IAny.class) + ")" + signature(IAny.class);
	private static final String SIG_ANY_INT = "(" + signature(IAny.class) + ")I";
	private static final String SIG_ANY_BOOL = "(" + signature(IAny.class) + ")Z";
	private static final String SIG_STRING_ANY = "(" + signature(String.class) + ")" + signature(IAny.class);
	private static final String SIG_STRING_ANY_VOID = "(" + signature(String.class) + signature(IAny.class) + ")V";
	private static final String SIG_STRING_ANYARRAY_ANY = "(" + signature(String.class) + signature(IAny[].class) + ")" + signature(IAny.class);
	private static final String SIG_ANY_ANY_VOID = "(" + signature(IAny.class) + signature(IAny.class) + ")V";
	private static final String SIG_ANYARRAY_ANY = "(" + signature(IAny[].class) + ")" + signature(IAny.class);
	
	private AnyClassWriter() {}
	
	public static byte[] construct(IAnyDefinition definition, String name, Type asmType) {
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		writer.visit(
				Opcodes.V1_6,
				Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
				name,
				null,
				internal(Object.class),
				new String[] { internal(IAny.class) });
		
		definition.defineMembers(writer);
		
		MethodOutput outputStaticCanCastImplicit = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
				"rtCanCastImplicit",
				"(Ljava/lang/Class;)Z",
				"(Ljava/lang/Class<*>;)Z",
				null);
		outputStaticCanCastImplicit.start();
		definition.defineStaticCanCastImplicit(outputStaticCanCastImplicit);
		outputStaticCanCastImplicit.end();
		
		MethodOutput outputStaticAs = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
				"rtAs",
				"(ILjava/lang/Class;)Ljava/lang/Object;",
				"(ILjava/lang/Class<*>;)Ljava/lang/Object;",
				null);
		outputStaticAs.start();
		definition.defineStaticAs(outputStaticAs);
		outputStaticAs.end();
		
		MethodOutput outputNot = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"not",
				SIG_ANY,
				null,
				null);
		outputNot.start();
		definition.defineNot(outputNot);
		outputNot.end();
		
		MethodOutput outputNeg = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"neg",
				SIG_ANY,
				null,
				null);
		outputNeg.start();
		definition.defineNeg(outputNeg);
		outputNeg.end();
		
		MethodOutput outputAdd = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"add",
				SIG_ANY_ANY,
				null,
				null);
		outputAdd.start();
		definition.defineAdd(outputAdd);
		outputAdd.end();
		
		MethodOutput outputSub = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"sub",
				SIG_ANY_ANY,
				null,
				null);
		outputSub.start();
		definition.defineSub(outputSub);
		outputSub.end();
		
		MethodOutput outputCat = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"cat",
				SIG_ANY_ANY,
				null,
				null);
		outputCat.start();
		definition.defineCat(outputCat);
		outputCat.end();
		
		MethodOutput outputMul = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"mul",
				SIG_ANY_ANY,
				null,
				null);
		outputMul.start();
		definition.defineMul(outputMul);
		outputMul.end();
		
		MethodOutput outputDiv = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"div",
				SIG_ANY_ANY,
				null,
				null);
		outputDiv.start();
		definition.defineDiv(outputDiv);
		outputDiv.end();
		
		MethodOutput outputMod = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"mod",
				SIG_ANY_ANY,
				null,
				null);
		outputMod.start();
		definition.defineMod(outputMod);
		outputMod.end();
		
		MethodOutput outputAnd = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"and",
				SIG_ANY_ANY,
				null,
				null);
		outputAnd.start();
		definition.defineAnd(outputAnd);
		outputAnd.end();
		
		MethodOutput outputOr = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"or",
				SIG_ANY_ANY,
				null,
				null);
		outputOr.start();
		definition.defineOr(outputOr);
		outputOr.end();
		
		MethodOutput outputXor = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"xor",
				SIG_ANY_ANY,
				null,
				null);
		outputXor.start();
		definition.defineXor(outputXor);
		outputXor.end();
		
		MethodOutput outputRange = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"range",
				SIG_ANY_ANY,
				null,
				null);
		outputRange.start();
		definition.defineRange(outputRange);
		outputRange.end();
		
		MethodOutput outputCompare = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"compareTo",
				SIG_ANY_INT,
				null,
				null);
		outputCompare.start();
		definition.defineCompareTo(outputCompare);
		outputCompare.end();
		
		MethodOutput outputContains = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"contains",
				SIG_ANY_BOOL,
				null,
				null);
		outputContains.start();
		definition.defineContains(outputContains);
		outputContains.end();
		
		MethodOutput outputMemberGet = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "memberGet", SIG_STRING_ANY, null, null);
		outputMemberGet.start();
		definition.defineMemberGet(outputMemberGet);
		outputMemberGet.end();
		
		MethodOutput outputMemberSet = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "memberSet", SIG_STRING_ANY_VOID, null, null);
		outputMemberSet.start();
		definition.defineMemberSet(outputMemberSet);
		outputMemberSet.end();
		
		MethodOutput outputMemberCall = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "memberCall", SIG_STRING_ANYARRAY_ANY, null, null);
		outputMemberCall.start();
		definition.defineMemberCall(outputMemberCall);
		outputMemberCall.end();
		
		MethodOutput outputIndexGet = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "indexGet", SIG_ANY_ANY, null, null);
		outputIndexGet.start();
		definition.defineIndexGet(outputIndexGet);
		outputIndexGet.end();
		
		MethodOutput outputIndexSet = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "indexSet", SIG_ANY_ANY_VOID, null, null);
		outputIndexSet.start();
		definition.defineIndexSet(outputIndexSet);
		outputIndexSet.end();
		
		MethodOutput outputCall = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "call", SIG_ANYARRAY_ANY, null, null);
		outputCall.start();
		definition.defineCall(outputCall);
		outputCall.end();
		
		MethodOutput outputAsBool = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asBool", "()Z", null, null);
		outputAsBool.start();
		definition.defineAsBool(outputAsBool);
		outputAsBool.end();
		
		MethodOutput outputAsByte = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asByte", "()B", null, null);
		outputAsByte.start();
		definition.defineAsByte(outputAsByte);
		outputAsByte.end();
		
		MethodOutput outputAsShort = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asShort", "()S", null, null);
		outputAsShort.start();
		definition.defineAsShort(outputAsShort);
		outputAsShort.end();
		
		MethodOutput outputAsInt = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asInt", "()I", null, null);
		outputAsInt.start();
		definition.defineAsInt(outputAsInt);
		outputAsInt.end();
		
		MethodOutput outputAsLong = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asLong", "()J", null, null);
		outputAsLong.start();
		definition.defineAsLong(outputAsLong);
		outputAsLong.end();
		
		MethodOutput outputAsFloat = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asFloat", "()F", null, null);
		outputAsFloat.start();
		definition.defineAsFloat(outputAsFloat);
		outputAsFloat.end();
		
		MethodOutput outputAsDouble = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asDouble", "()D", null, null);
		outputAsDouble.start();
		definition.defineAsDouble(outputAsDouble);
		outputAsDouble.end();
		
		MethodOutput outputAsString = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "asString", "()Ljava/lang/String;", null, null);
		outputAsString.start();
		definition.defineAsString(outputAsString);
		outputAsString.end();
		
		MethodOutput outputAs = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"as",
				"(Ljava/lang/Class;)" + signature(IAny.class),
				"<T:Ljava/lang/Object;>(Ljava/lang/Class<TT;>;)TT;",
				null);
		outputAs.start();
		definition.defineAs(outputAs);
		outputAs.end();
		
		MethodOutput outputIs = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"is",
				"(Ljava/lang/Class;)Z",
				"(Ljava/lang/Class<*>;)Z",
				null);
		outputIs.start();
		definition.defineIs(outputIs);
		outputIs.end();
		
		MethodOutput outputCanCastImplicit = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "canCastImplicit", "(" + signature(Class.class) + ")Z", "(Ljava/lang/Class<*>;)Z", null);
		outputCanCastImplicit.start();
		outputCanCastImplicit.loadObject(1);
		outputCanCastImplicit.invokeStatic(name, "rtCanCastImplicit", "(Ljava/lang/Class;)Z");
		outputCanCastImplicit.returnInt();
		outputCanCastImplicit.end();
		
		MethodOutput outputGetNumberType = new MethodOutput(writer, Opcodes.ACC_PUBLIC, "getNumberType", "()I", null, null);
		outputGetNumberType.start();
		definition.defineGetNumberType(outputGetNumberType);
		outputGetNumberType.end();
		
		MethodOutput outputIteratorSingle = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"iteratorSingle",
				"()" + signature(Iterator.class), 
				"()Ljava/util/Iterator<Lstanhebben/zenscript/value/IAny;>;",
				null);
		outputIteratorSingle.start();
		definition.defineIteratorSingle(outputIteratorSingle);
		outputIteratorSingle.end();
		
		MethodOutput outputIteratorMulti = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"iteratorMulti",
				"(I)Ljava/util/Iterator;",
				"(I)Ljava/util/Iterator<[Lstanhebben/zenscript/value/IAny;>;",
				null);
		outputIteratorMulti.start();
		definition.defineIteratorMulti(outputIteratorMulti);
		outputIteratorMulti.end();
		
		MethodOutput outputHashCode = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"hashCode",
				"()I",
				null,
				null);
		outputHashCode.start();
		definition.defineHashCode(outputHashCode);
		outputHashCode.end();
		
		MethodOutput outputEquals = new MethodOutput(
				writer,
				Opcodes.ACC_PUBLIC,
				"equals",
				"(Ljava/lang/Object;)Z",
				null,
				null);
		outputEquals.start();
		definition.defineEquals(outputEquals);
		outputEquals.end();
		
		writer.visitEnd();
		return writer.toByteArray();
	}
	
	public static void throwUnsupportedException(MethodOutput output, String fromType, String operation) {
		// throw new UnsupportedOperationException(fromType + " does not support the " + operation + " operator");
		output.newObject(UnsupportedOperationException.class);
		output.dup();
		output.constant(fromType + " does not support the " + operation + " operator");
		output.construct(UnsupportedOperationException.class, String.class);
		output.aThrow();
	}
	
	public static void throwCastException(MethodOutput output, String fromType, String toType) {
		// throw new ClassCastException("Cannot cast " + fromType + " to " + [local:Class].getName())
		output.newObject(ClassCastException.class);
		output.dup();
		output.constant("Cannot cast " + fromType + " to " + toType);
		output.construct(ClassCastException.class, String.class);
		output.aThrow();
	}
	
	public static void throwCastException(MethodOutput output, String fromType, int local) {
		// throw new ClassCastException("Cannot cast " + fromType + " to " + [local:Class].getName())
		output.newObject(ClassCastException.class);
		output.dup();
		
		output.newObject(StringBuilder.class);
		output.dup();
		output.construct(StringBuilder.class);
		output.constant("Cannot cast " + fromType + " to ");
		output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, String.class);
		output.loadObject(local);
		output.invokeVirtual(Class.class, "getName", String.class);
		output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, String.class);
		output.invokeVirtual(StringBuilder.class, "toString", String.class);
		
		output.construct(ClassCastException.class, String.class);
		output.aThrow();
	}
}
