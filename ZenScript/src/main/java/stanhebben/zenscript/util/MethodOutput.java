/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.util;

import java.lang.reflect.Field;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;
import stanhebben.zenscript.type.natives.JavaMethod;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 *
 * @author Stanneke
 */
public class MethodOutput {
	private final LocalVariablesSorter visitor;
	
	public MethodOutput(ClassVisitor cls, int access, String name, String descriptor, String signature, String[] exceptions) {
		MethodVisitor methodVisitor = cls.visitMethod(access, name, descriptor, signature, exceptions);
		visitor = new LocalVariablesSorter(access, descriptor, methodVisitor);
	}
	
	public MethodOutput(LocalVariablesSorter visitor) {
		this.visitor = visitor;
	}
	
	public void start() {
		visitor.visitCode();
	}
	
	public void end() {
		visitor.visitMaxs(0, 0);
		visitor.visitEnd();
	}
	
	public void label(Label label) {
		visitor.visitLabel(label);
	}
	
	public int local(Type type) {
		return visitor.newLocal(type);
	}
	
	public int local(Class cls) {
		return visitor.newLocal(Type.getType(cls));
	}
	
	public void iConst0() {
		visitor.visitInsn(ICONST_0);
	}
	
	public void iConst1() {
		visitor.visitInsn(ICONST_1);
	}
	
	public void biPush(byte value) {
		visitor.visitIntInsn(BIPUSH, value);
	}
	
	public void siPush(short value) {
		visitor.visitIntInsn(SIPUSH, value);
	}
	
	public void aConstNull() {
		visitor.visitInsn(ACONST_NULL);
	}
	
	public void constant(Object value) {
		visitor.visitLdcInsn(value);
	}
	
	public void pop() {
		visitor.visitInsn(POP);
	}
	
	public void pop(boolean large) {
		visitor.visitInsn(large ? POP2 : POP);
	}
	
	public void dup() {
		visitor.visitInsn(DUP);
	}
	
	public void dup(boolean large) {
		visitor.visitInsn(large ? DUP2 : DUP);
	}
	
	public void dup2() {
		visitor.visitInsn(DUP2);
	}
	
	public void dupX1() {
		visitor.visitInsn(DUP_X1);
	}
	
	public void dupX2() {
		visitor.visitInsn(DUP_X2);
	}
	
	public void dup2X1() {
		visitor.visitInsn(DUP2_X1);
	}
	
	public void dup2X2() {
		visitor.visitInsn(DUP2_X2);
	}
	
	public void store(Type type, int local) {
		visitor.visitVarInsn(type.getOpcode(ISTORE), local);
	}
	
	public void load(Type type, int local) {
		visitor.visitVarInsn(type.getOpcode(ILOAD), local);
	}
	
	public void storeInt(int local) {
		visitor.visitVarInsn(ISTORE, local);
	}
	
	public void loadInt(int local) {
		visitor.visitVarInsn(ILOAD, local);
	}
	
	public void storeObject(int local) {
		visitor.visitVarInsn(ASTORE, local);
	}
	
	public void loadObject(int local) {
		visitor.visitVarInsn(ALOAD, local);
	}
	
	public void arrayLength() {
		visitor.visitInsn(ARRAYLENGTH);
	}
	
	public void arrayLoad(Type type) {
		visitor.visitInsn(type.getOpcode(IALOAD));
	}
	
	public void arrayStore(Type type) {
		visitor.visitInsn(type.getOpcode(IASTORE));
	}
	
	public void newArray(Type componentType) {
		int sort = componentType.getSort();
		if (sort == Type.METHOD) {
			throw new RuntimeException("Unsupported array type: " + componentType);
		} else if (sort == Type.OBJECT || sort == Type.ARRAY) {
			visitor.visitTypeInsn(ANEWARRAY, componentType.getInternalName());
		} else {
			int type = 0;
			switch (sort) {
				case Type.BOOLEAN: type = Opcodes.T_BOOLEAN; break;
				case Type.BYTE: type = Opcodes.T_BYTE; break;
				case Type.SHORT: type = Opcodes.T_SHORT; break;
				case Type.INT: type = Opcodes.T_INT; break;
				case Type.LONG: type = Opcodes.T_LONG; break;
				case Type.FLOAT: type = Opcodes.T_FLOAT; break;
				case Type.DOUBLE: type = Opcodes.T_DOUBLE; break;
				default:
					throw new RuntimeException("Unsupported array type: " + componentType);
			}
			visitor.visitIntInsn(NEWARRAY, type);
		}
	}
	
	public void newArray(Class componentType) {
		visitor.visitTypeInsn(NEWARRAY, internal(componentType));
	}
	
	public void checkCast(Class newClass) {
		visitor.visitTypeInsn(CHECKCAST, signature(newClass));
	}
	
	public void checkCast(String newClass) {
		visitor.visitTypeInsn(CHECKCAST, newClass.substring(1, newClass.length() - 1));
	}
	
	public void iNeg() {
		visitor.visitInsn(INEG);
	}
	
	public void iAdd() {
		visitor.visitInsn(IADD);
	}
	
	public void iSub() {
		visitor.visitInsn(ISUB);
	}
	
	public void iMul() {
		visitor.visitInsn(IMUL);
	}
	
	public void iDiv() {
		visitor.visitInsn(IDIV);
	}
	
	public void iRem() {
		visitor.visitInsn(IREM);
	}
	
	public void iAnd() {
		visitor.visitInsn(IAND);
	}
	
	public void iOr() {
		visitor.visitInsn(IOR);
	}
	
	public void iXor() {
		visitor.visitInsn(IXOR);
	}
	
	public void iNot() {
		visitor.visitInsn(ICONST_M1);
		visitor.visitInsn(IXOR);
	}
	
	public void lNeg() {
		visitor.visitInsn(LNEG);
	}
	
	public void lAdd() {
		visitor.visitInsn(LADD);
	}
	
	public void lSub() {
		visitor.visitInsn(LSUB);
	}
	
	public void lMul() {
		visitor.visitInsn(LMUL);
	}
	
	public void lDiv() {
		visitor.visitInsn(LDIV);
	}
	
	public void lRem() {
		visitor.visitInsn(LREM);
	}
	
	public void lAnd() {
		visitor.visitInsn(LAND);
	}
	
	public void lOr() {
		visitor.visitInsn(LOR);
	}
	
	public void lXor() {
		visitor.visitInsn(LXOR);
	}
	
	public void lNot() {
		constant((long) -1);
		lXor();
	}
	
	public void fNeg() {
		visitor.visitInsn(FNEG);
	}
	
	public void fAdd() {
		visitor.visitInsn(FADD);
	}
	
	public void fSub() {
		visitor.visitInsn(FSUB);
	}
	
	public void fMul() {
		visitor.visitInsn(FMUL);
	}
	
	public void fDiv() {
		visitor.visitInsn(FDIV);
	}
	
	public void fRem() {
		visitor.visitInsn(FREM);
	}
	
	public void dNeg() {
		visitor.visitInsn(DNEG);
	}
	
	public void dAdd() {
		visitor.visitInsn(DADD);
	}
	
	public void dSub() {
		visitor.visitInsn(DSUB);
	}
	
	public void dMul() {
		visitor.visitInsn(DMUL);
	}
	
	public void dDiv() {
		visitor.visitInsn(DDIV);
	}
	
	public void dRem() {
		visitor.visitInsn(DREM);
	}
	
	public void iinc(int local) {
		visitor.visitIincInsn(local, 1);
	}
	
	public void iinc(int local, int increment) {
		visitor.visitIincInsn(local, increment);
	}
	
	public void i2b() {
		visitor.visitInsn(I2B);
	}
	
	public void i2s() {
		visitor.visitInsn(I2S);
	}
	
	public void i2l() {
		visitor.visitInsn(I2L);
	}
	
	public void i2f() {
		visitor.visitInsn(I2F);
	}
	
	public void i2d() {
		visitor.visitInsn(I2D);
	}
	
	public void l2i() {
		visitor.visitInsn(L2I);
	}
	
	public void l2f() {
		visitor.visitInsn(L2F);
	}
	
	public void l2d() {
		visitor.visitInsn(L2D);
	}
	
	public void f2i() {
		visitor.visitInsn(F2I);
	}
	
	public void f2l() {
		visitor.visitInsn(F2L);
	}
	
	public void f2d() {
		visitor.visitInsn(F2D);
	}
	
	public void d2i() {
		visitor.visitInsn(D2I);
	}
	
	public void d2l() {
		visitor.visitInsn(D2L);
	}
	
	public void d2f() {
		visitor.visitInsn(D2F);
	}
	
	public void lCmp() {
		visitor.visitInsn(LCMP);
	}
	
	public void fCmp() {
		visitor.visitInsn(FCMPL);
	}
	
	public void dCmp() {
		visitor.visitInsn(DCMPL);
	}
	
	public void invokeStatic(String owner, String name, String descriptor) {
		if (owner == null)
			throw new IllegalArgumentException("owner cannot be null");
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		if (descriptor == null)
			throw new IllegalArgumentException("descriptor cannot be null");
		
		visitor.visitMethodInsn(INVOKESTATIC, owner, name, descriptor);
	}
	
	public void invokeStatic(Class owner, String name, Class result, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(')');
		descriptor.append(result == null ? 'V' : signature(result));
		visitor.visitMethodInsn(INVOKESTATIC, internal(owner), name, descriptor.toString());
	}
	
	public void invokeStatic(JavaMethod method) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : method.getMethod().getParameterTypes()) {
			descriptor.append(signature(argument));
		}
		descriptor.append(')');
		descriptor.append(signature(method.getMethod().getReturnType()));
		visitor.visitMethodInsn(INVOKESTATIC, internal(method.getOwner()), method.getMethod().getName(), descriptor.toString());
	}
	
	public void invokeSpecial(String owner, String name, String descriptor) {
		visitor.visitMethodInsn(INVOKESPECIAL, owner, name, descriptor);
	}
	
	public void invoke(Class owner, String name, Class result, Class... arguments) {
		if (owner.isInterface()) {
			invokeInterface(owner, name, result, arguments);
		} else {
			invokeVirtual(owner, name, result, arguments);
		}
	}
	
	public void invoke(JavaMethod method) {
		if (method.getOwner().isInterface()) {
			invokeInterface(
					method.getOwner(),
					method.getMethod().getName(),
					method.getMethod().getReturnType(),
					method.getMethod().getParameterTypes());
		} else {
			invokeVirtual(
					method.getOwner(),
					method.getMethod().getName(),
					method.getMethod().getReturnType(),
					method.getMethod().getParameterTypes());
		}
	}
	
	public void invokeVirtual(String owner, String name, String descriptor) {
		visitor.visitMethodInsn(INVOKEVIRTUAL, owner, name, descriptor);
	}
	
	public void invokeVirtual(Class owner, String name, Class result, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(')');
		descriptor.append(result == null ? 'V' : signature(result));
		visitor.visitMethodInsn(INVOKEVIRTUAL, internal(owner), name, descriptor.toString());
	}
	
	public void invokeInterface(String owner, String name, String descriptor) {
		visitor.visitMethodInsn(INVOKEINTERFACE, owner, name, descriptor);
	}
	
	public void invokeInterface(Class owner, String name, Class result, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(')');
		descriptor.append(result == null ? 'V' : signature(result));
		visitor.visitMethodInsn(INVOKEINTERFACE, internal(owner), name, descriptor.toString());
	}
	
	public void newObject(Class type) {
		visitor.visitTypeInsn(NEW, internal(type));
	}
	
	public void newObject(String type) {
		visitor.visitTypeInsn(NEW, type);
	}
	
	public void construct(Class type, Class... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (Class argument : arguments) {
			descriptor.append(signature(argument));
		}
		descriptor.append(")V");
		visitor.visitMethodInsn(INVOKESPECIAL, internal(type), "<init>", descriptor.toString());
	}
	
	public void construct(String type, String... arguments) {
		StringBuilder descriptor = new StringBuilder();
		descriptor.append('(');
		for (String argument : arguments) {
			descriptor.append(argument);
		}
		descriptor.append(")V");
		visitor.visitMethodInsn(INVOKESPECIAL, type, "<init>", descriptor.toString());
	}
	
	public void goTo(Label lbl) {
		visitor.visitJumpInsn(GOTO, lbl);
	}
	
	/**
	 * Jump if TOS == 0.
	 * 
	 * @param lbl target label
	 */
	public void ifEQ(Label lbl) {
		visitor.visitJumpInsn(IFEQ, lbl);
	}
	
	public void ifNE(Label lbl) {
		visitor.visitJumpInsn(IFNE, lbl);
	}
	
	public void ifLT(Label lbl) {
		visitor.visitJumpInsn(IFLT, lbl);
	}
	
	public void ifGT(Label lbl) {
		visitor.visitJumpInsn(IFGT, lbl);
	}
	
	public void ifGE(Label lbl) {
		visitor.visitJumpInsn(IFGE, lbl);
	}
	
	public void ifLE(Label lbl) {
		visitor.visitJumpInsn(IFLE, lbl);
	}
	
	public void ifICmpLE(Label lbl) {
		visitor.visitJumpInsn(IF_ICMPLE, lbl);
	}
	
	public void ifICmpGE(Label lbl) {
		visitor.visitJumpInsn(IF_ICMPGE, lbl);
	}
	
	public void ifICmpEQ(Label lbl) {
		visitor.visitJumpInsn(IF_ICMPEQ, lbl);
	}
	
	public void ifICmpNE(Label lbl) {
		visitor.visitJumpInsn(IF_ICMPNE, lbl);
	}
	
	public void ifICmpGT(Label lbl) {
		visitor.visitJumpInsn(IF_ICMPGT, lbl);
	}
	
	public void ifICmpLT(Label lbl) {
		visitor.visitJumpInsn(IF_ICMPLT, lbl);
	}
	
	public void ifACmpEq(Label lbl) {
		visitor.visitJumpInsn(IF_ACMPEQ, lbl);
	}
	
	public void ifACmpNe(Label lbl) {
		visitor.visitJumpInsn(IF_ACMPNE, lbl);
	}
	
	public void ifNull(Label lbl) {
		visitor.visitJumpInsn(IFNULL, lbl);
	}
	
	public void ifNonNull(Label lbl) {
		visitor.visitJumpInsn(IFNONNULL, lbl);
	}
	
	public void ret() {
		visitor.visitInsn(RETURN);
	}
	
	public void returnType(Type type) {
		visitor.visitInsn(type.getOpcode(IRETURN));
	}
	
	public void returnInt() {
		visitor.visitInsn(IRETURN);
	}
	
	public void returnObject() {
		visitor.visitInsn(ARETURN);
	}
	
	public void getField(String owner, String name, String descriptor) {
		visitor.visitFieldInsn(GETFIELD, owner, name, descriptor);
	}
	
	public void getField(Class owner, String name, Class descriptor) {
		visitor.visitFieldInsn(GETFIELD, internal(owner), name, signature(descriptor));
	}
	
	public void putField(String owner, String name, String descriptor) {
		visitor.visitFieldInsn(PUTFIELD, owner, name, descriptor);
	}
	
	public void putField(Class owner, String name, Class descriptor) {
		visitor.visitFieldInsn(PUTFIELD, internal(owner), name, signature(descriptor));
	}
	
	public void getStaticField(String owner, String name, String descriptor) {
		visitor.visitFieldInsn(GETSTATIC, owner, name, descriptor);
	}
	
	public void getStaticField(Class owner, Field field) {
		visitor.visitFieldInsn(GETSTATIC, internal(owner), field.getName(), signature(field.getType()));
	}
	
	public void putStaticField(String owner, String name, String descriptor) {
		visitor.visitFieldInsn(PUTSTATIC, owner, name, descriptor);
	}
	
	public void putStaticField(Class owner, Field field) {
		visitor.visitFieldInsn(PUTSTATIC, internal(owner), field.getName(), signature(field.getType()));
	}
	
	public void aThrow() {
		visitor.visitInsn(ATHROW);
	}
	
	public void position(ZenPosition position) {
		Label label = new Label();
		visitor.visitLabel(label);
		visitor.visitLineNumber(position.getLine(), label);
	}
}
