/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.objectweb.asm.ClassWriter;
import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import stanhebben.zenscript.ZenRuntimeException;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.IterableList;
import stanhebben.zenscript.annotations.IterableMap;
import stanhebben.zenscript.annotations.IterableSimple;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenMemberGetter;
import stanhebben.zenscript.annotations.ZenMemberSetter;
import stanhebben.zenscript.annotations.ZenSetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.compiler.EnvironmentMethod;
import stanhebben.zenscript.compiler.ITypeRegistry;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionArithmeticUnary;
import stanhebben.zenscript.expression.ExpressionAs;
import stanhebben.zenscript.expression.ExpressionCompareGeneric;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.iterator.IteratorIterable;
import stanhebben.zenscript.type.iterator.IteratorList;
import stanhebben.zenscript.type.iterator.IteratorMap;
import stanhebben.zenscript.type.iterator.IteratorMapKeys;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.type.natives.ZenNativeCaster;
import stanhebben.zenscript.type.natives.ZenNativeMember;
import stanhebben.zenscript.type.natives.ZenNativeOperator;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;
import stanhebben.zenscript.value.IAny;

/**
 *
 * @author Stanneke
 */
public class ZenTypeNative extends ZenType {
	private static final int ITERATOR_NONE = 0;
	private static final int ITERATOR_ITERABLE = 1;
	private static final int ITERATOR_LIST = 2;
	private static final int ITERATOR_MAP = 3;
	
	private final Class cls;
	private final List<ZenTypeNative> implementing;
	private final Map<String, ZenNativeMember> members;
	private final Map<String, ZenNativeMember> staticMembers;
	private final List<ZenNativeCaster> casters;
	private final List<ZenNativeOperator> trinaryOperators;
	private final List<ZenNativeOperator> binaryOperators;
	private final List<ZenNativeOperator> unaryOperators;
	
	private int iteratorType;
	private String classPkg;
	private String className;
	private Annotation iteratorAnnotation;
	private ZenType iteratorKeyType;
	private ZenType iteratorValueType;
	
	public ZenTypeNative(Class cls) {
		this.cls = cls;
		members = new HashMap<String, ZenNativeMember>();
		staticMembers = new HashMap<String, ZenNativeMember>();
		casters = new ArrayList<ZenNativeCaster>();
		trinaryOperators = new ArrayList<ZenNativeOperator>();
		binaryOperators = new ArrayList<ZenNativeOperator>();
		unaryOperators = new ArrayList<ZenNativeOperator>();
		implementing = new ArrayList<ZenTypeNative>();
	}
	
	public void complete(ITypeRegistry types) {
		int iterator = ITERATOR_NONE;
		Annotation _iteratorAnnotation = null;
		String _classPkg = cls.getPackage().getName().replace('/', '.');
		String _className = cls.getSimpleName();
		boolean fully = false;
		
		Queue<ZenTypeNative> todo = new LinkedList<ZenTypeNative>();
		todo.add(this);
		addSubtypes(todo, types);
		
		Annotation[] clsAnnotations = cls.getAnnotations();
		for (Annotation annotation : clsAnnotations) {
			if (annotation instanceof ZenClass) {
				String value = ((ZenClass) annotation).value();
				int dot = value.lastIndexOf('.');
				if (dot < 0) {
					_classPkg = null;
					_className = value;
				} else {
					_classPkg = value.substring(0, dot);
					_className = value.substring(dot + 1);
				}
			}
			if (annotation instanceof IterableSimple) {
				iterator = ITERATOR_ITERABLE;
				_iteratorAnnotation = annotation;
				if (!Iterable.class.isAssignableFrom(cls)) {
					// TODO: illegal
				}
			}
			if (annotation instanceof IterableList) {
				iterator = ITERATOR_LIST;
				_iteratorAnnotation = annotation;
				if (!List.class.isAssignableFrom(cls)) {
					// TODO: illegal
				}
			}
			if (annotation instanceof IterableMap) {
				iterator = ITERATOR_MAP;
				_iteratorAnnotation = annotation;
				if (!Map.class.isAssignableFrom(cls)) {
					// TODO: illegal
				}
			}
		}
		
		for (Method method : cls.getMethods()) {
			boolean isMethod = fully;
			String methodName = method.getName();
			
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation instanceof ZenCaster) {
					Class output = method.getReturnType();
					casters.add(new ZenNativeCaster(cls, output, method.getName()));
					isMethod = false;
				} else if (annotation instanceof ZenGetter) {
					ZenGetter getterAnnotation = (ZenGetter) annotation;
					String name = getterAnnotation.value().length() == 0 ? method.getName() : getterAnnotation.value();
					
					if (!members.containsKey(name)) {
						members.put(name, new ZenNativeMember());
					}
					members.get(name).setGetter(new JavaMethod(cls, method, types));
					isMethod = false;
				} else if (annotation instanceof ZenSetter) {
					ZenSetter setterAnnotation = (ZenSetter) annotation;
					String name = setterAnnotation.value().length() == 0 ? method.getName() : setterAnnotation.value();
					
					if (!members.containsKey(name)) {
						members.put(name, new ZenNativeMember());
					}
					members.get(name).setSetter(new JavaMethod(cls, method, types));
					isMethod = false;
				} else if (annotation instanceof ZenMemberGetter) {
					binaryOperators.add(new ZenNativeOperator(OperatorType.MEMBERGETTER, new JavaMethod(cls, method, types)));
				} else if (annotation instanceof ZenMemberSetter) {
					trinaryOperators.add(new ZenNativeOperator(OperatorType.MEMBERSETTER, new JavaMethod(cls, method, types)));
				} else if (annotation instanceof ZenOperator) {
					ZenOperator operatorAnnotation = (ZenOperator) annotation;
					switch (operatorAnnotation.value()) {
						case NEG:
						case NOT:
							if (method.getParameterTypes().length != 0) {
								// TODO: error
							} else {
								unaryOperators.add(new ZenNativeOperator(
										operatorAnnotation.value(),
										new JavaMethod(cls, method, types)));
							}
							break;
						case ADD:
						case SUB:
						case CAT:
						case MUL:
						case DIV:
						case MOD:
						case AND:
						case OR:
						case XOR:
						case INDEXGET:
						case RANGE:
						case CONTAINS:
						case COMPARE:
							if (method.getParameterTypes().length != 1) {
								// TODO: error
							} else {
								binaryOperators.add(new ZenNativeOperator(
										operatorAnnotation.value(),
										new JavaMethod(cls, method, types)));
							}
							break;
						case INDEXSET:
							if (method.getParameterTypes().length != 2) {
								// TODO: error
							} else {
								trinaryOperators.add(new ZenNativeOperator(
										operatorAnnotation.value(),
										new JavaMethod(cls, method, types)));
							}
							break;
					}
					isMethod = false;
				} else if (annotation instanceof ZenMethod) {
					isMethod = true;
					
					ZenMethod methodAnnotation = (ZenMethod) annotation;
					if (methodAnnotation.value().length() > 0) {
						methodName = methodAnnotation.value();
					}
				}
			}
			
			if (isMethod) {
				if ((method.getModifiers() & Modifier.STATIC) > 0) {
					if (!staticMembers.containsKey(methodName)) {
						staticMembers.put(methodName, new ZenNativeMember());
					}
					staticMembers.get(methodName).addMethod(new JavaMethod(cls, method, types));
				} else {
					if (!members.containsKey(methodName)) {
						members.put(methodName, new ZenNativeMember());
					}
					members.get(methodName).addMethod(new JavaMethod(cls, method, types));
				}
			}
		}
		
		this.iteratorType = iterator;
		this.iteratorAnnotation = _iteratorAnnotation;
		this.classPkg = _classPkg;
		this.className = _className;
	}
	
	public Class getNativeClass() {
		return cls;
	}
	
	public void complete(IEnvironmentGlobal environment) {
		if (iteratorAnnotation instanceof IterableSimple) {
			IterableSimple annotation = (IterableSimple) iteratorAnnotation;
			iteratorValueType = ZenType.parse(annotation.value(), environment);
		}
		if (iteratorAnnotation instanceof IterableList) {
			IterableList annotation = (IterableList) iteratorAnnotation;
			iteratorKeyType = ZenTypeInt.INSTANCE;
			iteratorValueType = ZenType.parse(annotation.value(), environment);
		}
		if (iteratorAnnotation instanceof IterableMap) {
			IterableMap annotation = (IterableMap) iteratorAnnotation;
			iteratorKeyType = ZenType.parse(annotation.key(), environment);
			iteratorValueType = ZenType.parse(annotation.value(), environment);
		}
	}
	
	@Override
	public String getAnyClassName(IEnvironmentGlobal global) {
		return null;
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		ZenNativeMember member = members.get(name);
		if (member == null) {
			for (ZenTypeNative type : implementing) {
				if (type.members.containsKey(name)) {
					member = type.members.get(name);
					break;
				}
			}
		}
		if (member == null) {
			Expression evalue = value.eval(environment);
			IPartialExpression member2 = memberExpansion(position, environment, evalue, name);
			if (member2 == null) {
				for (ZenTypeNative type : implementing) {
					member2 = type.memberExpansion(position, environment, evalue, name);
					if (member2 != null) break;
				}
			}
			if (member2 == null) {
				if (hasBinary(STRING, OperatorType.MEMBERGETTER)) {
					return binary(
							position,
							environment,
							value.eval(environment),
							new ExpressionString(position, name),
							OperatorType.MEMBERGETTER);
				} else {
					environment.error(position, "No such member in " + getName() + ": " + name);
					return new ExpressionInvalid(position);
				}
			} else {
				return member2;
			}
		} else {
			return member.instance(position, environment, value);
		}
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		ZenNativeMember member = staticMembers.get(name);
		if (member == null) {
			for (ZenTypeNative type : implementing) {
				if (type.staticMembers.containsKey(name)) {
					member = type.staticMembers.get(name);
					break;
				}
			}
		}
		if (member == null) {
			IPartialExpression member2 = staticMemberExpansion(position, environment, name);
			if (member2 == null) {
				for (ZenTypeNative type : implementing) {
					member2 = type.staticMemberExpansion(position, environment, name);
					if (member2 != null) break;
				}
			}
			if (member2 == null) {
				environment.error(position, "No such static member in " + getName() + ": " + name);
				return new ExpressionInvalid(position);
			} else {
				return member2;
			}
		} else {
			return member.instance(position, environment);
		}
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		switch (iteratorType) {
			case ITERATOR_NONE:
				break;
			case ITERATOR_ITERABLE:
				if (numValues == 1) {
					return new IteratorIterable(methodOutput.getOutput(), iteratorValueType);
				} else if (numValues == 2) {
					return new IteratorList(methodOutput.getOutput(), iteratorValueType);
				}
				break;
			case ITERATOR_MAP:
				if (numValues == 1) {
					return new IteratorMapKeys(methodOutput.getOutput(), new ZenTypeAssociative(iteratorValueType, iteratorKeyType));
				} else if (numValues == 2) {
					return new IteratorMap(methodOutput.getOutput(), new ZenTypeAssociative(iteratorValueType, iteratorKeyType));
				}
				break;
			case ITERATOR_LIST:
				if (numValues == 1) {
					// list is also iterable
					return new IteratorIterable(methodOutput.getOutput(), iteratorValueType);
				} else if (numValues == 2) {
					return new IteratorList(methodOutput.getOutput(), iteratorValueType);
				}
				break;
		}
		return null;
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this
				|| canCastImplicit(cls, type)
				|| canCastExpansion(environment, type);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this
				|| canCastImplicit(cls, type)
				|| canCastImplicit(type, cls)
				|| canCastExpansion(environment, type);
	}
	
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type == this || canCastImplicit(cls, type)) {
			return value;
		}
		
		if (canCastExpansion(environment, type)) {
			return castExpansion(position, environment, value, type);
		} else {
			return new ExpressionAs(position, value, type);
		}
	}
	
	@Override
	public Class toJavaClass() {
		return cls;
	}

	@Override
	public Type toASMType() {
		return Type.getType(cls);
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public String getSignature() {
		return signature(cls);
	}

	@Override
	public boolean isPointer() {
		return true;
	}
	
	/**
	 * Can we cast the given class to the given type implicitly?
	 * 
	 * @param cls class to be casted
	 * @param type type to cast to
	 * @return true if casting is possible
	 */
	private static boolean canCastImplicit(Class cls, ZenType type) {
		if (isEqual(cls, type)) return true;
		if (type == BOOL) return true;
		if (cls.getSuperclass() != null) {
			if (canCastImplicit(cls.getSuperclass(), type)) return true;
		}
		for (Class iface : cls.getInterfaces()) {
			if (canCastImplicit(iface, type)) return true;
		}
		return false;
	}
	
	/**
	 * Can we cast the given type to the given class implicitly?
	 * 
	 * @param type type to be casted
	 * @param cls type to cast to
	 * @return true if casting is possible
	 */
	private static boolean canCastImplicit(ZenType type, Class cls) {
		if (isEqual(cls, type)) return true;
		if (type == BOOL) return true;
		if (type instanceof ZenTypeNative) {
			Class clsFrom = ((ZenTypeNative) type).cls;
			return cls.isAssignableFrom(clsFrom);
		}
		return false;
	}
	
	private static boolean isEqual(Class cls, ZenType type) {
		String signature = getSignature(cls);
		return type.getSignature().equals(signature);
	}
	
	private static String getSignature(Class cls) {
		String signature = signature(cls);
		if (Number.class.isAssignableFrom(cls)) {
			if (cls == Byte.class) {
				signature = "B";
			} else if (cls == Short.class) {
				signature = "S";
			} else if (cls == Integer.class) {
				signature = "I";
			} else if (cls == Long.class) {
				signature = "J";
			} else if (cls == Float.class) {
				signature = "F";
			} else if (cls == Double.class) {
				signature = "D";
			}
		}
		return signature;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType toType) {
		if (toType == ZenTypeAny.INSTANCE) {
			String anyClassName = this.cls.getName() + "__Any";
			String anySignature = signature(IAny.class);
			String toSignature = "L" + anyClassName + "";
			
			if (!environment.containsClass(anyClassName)) {
				environment.putClass(anyClassName, new byte[0]); // prevent recursive issues
				
				ClassWriter newClass = new ClassWriter(COMPUTE_FRAMES);
				newClass.visit(
						Opcodes.V1_6,
						Opcodes.ACC_PUBLIC,
						anyClassName,
						"L" + anyClassName + ";",
						signature(Object.class),
						new String[] { anySignature });
				
				newClass.visitField(Opcodes.ACC_PRIVATE, "value", null, className, null);
				
				// static valueOf method
				MethodOutput valueOfMethod = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
						"valueOf",
						"(" + getSignature() + ")L" + anyClassName + ";",
						null,
						null);
				valueOfMethod.start();
				valueOfMethod.newObject(anyClassName);
				valueOfMethod.loadObject(0);
				valueOfMethod.construct(anyClassName, getSignature());
				valueOfMethod.returnObject();
				valueOfMethod.end();
				
				// single constructor
				MethodOutput constructorMethod = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"<init>",
						"(" + getSignature() + ")V",
						null,
						null);
				constructorMethod.start();
				constructorMethod.loadObject(1);
				constructorMethod.loadObject(0);
				constructorMethod.putField(toSignature, "value", null);
				constructorMethod.end();
				
				// implementation for each of the interface methods
				MethodOutput methodNot = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"not",
						"()" + anySignature,
						null,
						null);
				methodNot.start();
				compileAnyUnary(
						anySignature,
						OperatorType.NOT,
						new EnvironmentMethod(methodNot, environment));
				methodNot.end();
				
				MethodOutput methodNeg = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"neg",
						"()" + anySignature,
						null,
						null);
				methodNeg.start();
				compileAnyUnary(
						anySignature,
						OperatorType.NEG,
						new EnvironmentMethod(methodNeg, environment));
				methodNeg.end();
				
				MethodOutput methodAdd = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"add",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodAdd.start();
				compileAnyBinary(
						anySignature,
						OperatorType.ADD,
						new EnvironmentMethod(methodAdd, environment));
				methodAdd.end();
				
				MethodOutput methodSub = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"sub",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodSub.start();
				compileAnyBinary(
						anySignature,
						OperatorType.SUB,
						new EnvironmentMethod(methodSub, environment));
				methodSub.end();
				
				MethodOutput methodCat = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"cat",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodCat.start();
				compileAnyBinary(
						anySignature,
						OperatorType.CAT,
						new EnvironmentMethod(methodCat, environment));
				methodCat.end();
				
				MethodOutput methodMul = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"mul",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodMul.start();
				compileAnyBinary(
						anySignature,
						OperatorType.ADD,
						new EnvironmentMethod(methodMul, environment));
				methodMul.end();
				
				MethodOutput methodDiv = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"div",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodDiv.start();
				compileAnyBinary(
						anySignature,
						OperatorType.DIV,
						new EnvironmentMethod(methodDiv, environment));
				methodDiv.end();
				
				MethodOutput methodMod = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"mod",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodMod.start();
				compileAnyBinary(
						anySignature,
						OperatorType.MOD,
						new EnvironmentMethod(methodMod, environment));
				methodMod.end();
				
				MethodOutput methodAnd = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"and",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodAnd.start();
				compileAnyBinary(
						anySignature,
						OperatorType.AND,
						new EnvironmentMethod(methodAnd, environment));
				methodAnd.end();
				
				MethodOutput methodOr = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"or",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodOr.start();
				compileAnyBinary(
						anySignature,
						OperatorType.OR,
						new EnvironmentMethod(methodOr, environment));
				methodOr.end();
				
				MethodOutput methodXor = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"xor",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodXor.start();
				compileAnyBinary(
						anySignature,
						OperatorType.XOR,
						new EnvironmentMethod(methodXor, environment));
				methodXor.end();
				
				MethodOutput methodRange = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"range",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodRange.start();
				compileAnyBinary(
						anySignature,
						OperatorType.RANGE,
						new EnvironmentMethod(methodRange, environment));
				methodRange.end();
				
				MethodOutput methodContains = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"contains",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodContains.start();
				compileAnyBinary(
						anySignature,
						OperatorType.CONTAINS,
						new EnvironmentMethod(methodContains, environment));
				methodContains.end();
				
				MethodOutput methodIndexSet = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"indexSet",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodIndexSet.start();
				compileAnyBinary(
						anySignature,
						OperatorType.INDEXSET,
						new EnvironmentMethod(methodIndexSet, environment));
				methodIndexSet.end();
				
				MethodOutput methodCompare = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"compareTo",
						"(" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodCompare.start();
				compileAnyBinary(
						anySignature,
						OperatorType.COMPARE,
						new EnvironmentMethod(methodCompare, environment));
				methodCompare.end();
				
				MethodOutput methodMember = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"method",
						"(" + signature(String.class) + ")" + anySignature,
						null,
						null);
				methodMember.start();
				compileAnyMember(anySignature, new EnvironmentMethod(methodMember, environment));
				methodMember.end();
				
				MethodOutput methodCall = new MethodOutput(
						newClass,
						Opcodes.ACC_PUBLIC,
						"call",
						"([" + signature(IAny.class) + ")" + anySignature,
						null,
						null);
				methodCall.start();
				// TODO: complete
				methodCall.aConstNull();
				methodCall.returnObject();
				methodCall.end();
				
				// TODO: asXXX, iterators
				
				environment.putClass(anyClassName, newClass.toByteArray());
			}
			return;
		} else if (toType instanceof ZenTypeNative) {
			Class toCls = toType.toJavaClass();
			if (toCls.isAssignableFrom(cls)) {
				// nothing to do
				return;
			}
		}
		
		MethodOutput output = environment.getOutput();
		if (toType == BOOL) {
			Label lbl = new Label();
			output.iConst0();
			output.ifNull(lbl);
			output.iConst1();
			output.label(lbl);
			return;
		}
		for (ZenNativeCaster caster : casters) {
			if (caster.getCasterClass() == toType.toJavaClass()) {
				caster.compile(output);
				return;
			}
		}
		for (ZenNativeCaster caster : casters) {
			if (toType.toJavaClass().isAssignableFrom(caster.getCasterClass())) {
				caster.compile(output);
				return;
			}
		}
		if (compileCastExpansion(position, environment, toType)) {
			return;
		}
		
		output.checkCast(toType.toJavaClass());
	}
	
	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		for (ZenNativeOperator unaryOperator : unaryOperators) {
			if (unaryOperator.getOperator() == operator) {
				return unaryOperator
						.getMethod()
						.callVirtual(position, environment, value);
			}
		}
		
		environment.error(position, "operator not supported");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		for (ZenNativeOperator binaryOperator : binaryOperators) {
			if (binaryOperator.getOperator() == operator) {
				return binaryOperator
						.getMethod()
						.callVirtual(position, environment, left, right);
			}
		}
		
		environment.error(position, "operator not supported");
		return new ExpressionInvalid(position);
	}
	
	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		for (ZenNativeOperator trinaryOperator : trinaryOperators) {
			if (trinaryOperator.getOperator() == operator) {
				return trinaryOperator
						.getMethod()
						.callVirtual(position, environment, first, second, third);
			}
		}
		
		environment.error(position, "operator not supported");
		return new ExpressionInvalid(position);
	}
	
	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		if (type == CompareType.EQ || type == CompareType.NE) {
			for (ZenNativeOperator binaryOperator : binaryOperators) {
				if (binaryOperator.getOperator() == OperatorType.EQUALS) {
					Expression result = binaryOperator
							.getMethod()
							.callVirtual(position, environment, left, right);
					if (type == CompareType.EQ) {
						return result;
					} else {
						return new ExpressionArithmeticUnary(position, OperatorType.NOT, result);
					}
				}
			}
		}
		
		return new ExpressionCompareGeneric(position, binary(position, environment, left, right, OperatorType.COMPARE), type);
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	private boolean hasBinary(ZenType type, OperatorType operator) {
		for (ZenNativeOperator binaryOperator : binaryOperators) {
			if (binaryOperator.getOperator() == operator) {
				return true;
			}
		}
		
		return false;
	}
	
	private void compileAnyUnary(String anySignature, OperatorType operator, IEnvironmentMethod environment) {
		List<ZenNativeOperator> operators = new ArrayList<ZenNativeOperator>();
		for (ZenNativeOperator unary : this.unaryOperators) {
			if (unary.getOperator() == operator) {
				operators.add(unary);
			}
		}
		
		MethodOutput output = environment.getOutput();
		if (operators.isEmpty()) {
			output.newObject(ZenRuntimeException.class);
			output.dup();
			output.constant("Cannot " + operator + " " + classPkg + '.' + className);
			output.construct(ZenRuntimeException.class, String.class);
			output.aThrow();
		} else {
			if (operators.size() > 1) {
				environment.error(null, "Multiple " + operator + " operators for " + cls);
			}
			
			ZenNativeOperator unary = operators.get(0);
			
			output.loadObject(0);
			output.getField(anySignature, "value", signature(cls));
			
			output.invoke(unary.getMethod());
			environment.getType(unary.getClass()).compileCast(null, environment, ANY);
			output.returnObject();
		}
	}
	
	private void compileAnyBinary(String anySignature, OperatorType operator, IEnvironmentMethod environment) {
		List<ZenNativeOperator> operators = new ArrayList<ZenNativeOperator>();
		for (ZenNativeOperator binary : binaryOperators) {
			if (binary.getOperator() == operator) {
				operators.add(binary);
			}
		}
		
		MethodOutput output = environment.getOutput();
		if (operators.isEmpty()) {
			output.newObject(ZenRuntimeException.class);
			output.dup();
			output.constant("Cannot " + operator + " on " + classPkg + '.' + className);
			output.construct(ZenRuntimeException.class, String.class);
			output.aThrow();
		} else if (operators.size() == 1) {
			ZenNativeOperator binary = operators.get(0);
			
			output.loadObject(0);
			output.getField(anySignature, "value", signature(cls));
			output.loadObject(1);
			output.invoke(binary.getMethod());
			environment.getType(binary.getClass()).compileCast(null, environment, ANY);
			output.returnObject();
		} else {
			environment.error(null, "Multiple " + operator + " operators for " + cls + " (which is not yet supported)");
		}
	}
	
	public void compileAnyMember(String anySignature, EnvironmentMethod environment) {
		// TODO: complete
		MethodOutput output = environment.getOutput();
		output.aConstNull();
		output.returnObject();
	}

	@Override
	public String getName() {
		return classPkg + '.' + className;
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
	
	private void addSubtypes(Queue<ZenTypeNative> todo, ITypeRegistry types) {
		while (!todo.isEmpty()) {
			ZenTypeNative current = todo.poll();
			if (current.cls.getSuperclass() != Object.class) {
				ZenType type = types.getType(current.cls.getSuperclass());
				if (type instanceof ZenTypeNative) {
					todo.offer((ZenTypeNative) type);
					implementing.add((ZenTypeNative) type);
				}
			}
			for (Class iface : current.cls.getInterfaces()) {
				ZenType type = types.getType(iface);
				if (type instanceof ZenTypeNative) {
					todo.offer((ZenTypeNative) type);
					implementing.add((ZenTypeNative) type);
				}
			}
		}
	}
}
