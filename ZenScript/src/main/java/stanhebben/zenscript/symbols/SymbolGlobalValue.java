package stanhebben.zenscript.symbols;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import stanhebben.zenscript.compiler.EnvironmentClass;
import stanhebben.zenscript.compiler.IEnvironmentClass;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.definitions.ParsedGlobalValue;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialGlobalValue;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class SymbolGlobalValue implements IZenSymbol {

	private final ParsedGlobalValue value;
	private final PartialGlobalValue instance;

	public SymbolGlobalValue(ParsedGlobalValue value, IEnvironmentMethod environmentClass) {
		this.value = value;
		this.instance = new PartialGlobalValue(this);
		
		initField(environmentClass.getClassOutput(), getName(), getDescriptor());
		compileGlobal(environmentClass);
	}

	@Override
	public IPartialExpression instance(ZenPosition position) {
		return instance;
	}

	public String getName() {
		return value.getName();
	}
	
	
	public ZenType getType() {
		return value.getType();
	}
	
	public ParsedGlobalValue getValue() {
		return value;
	}
	
	public String getOwner() {
		return value.getOwner();
	}
	
	public String getDescriptor() {
		return getType().toASMType().getDescriptor();
	}
	
	
	private void compileGlobal(IEnvironmentMethod envo) {
		value.getValue().compile(envo, getType()).eval(envo).compile(true, envo);
		envo.getOutput().putStaticField(getOwner(), getName(), getDescriptor());
	}
	
	private static void initField(ClassVisitor classOutput, String name, String description) {
		classOutput.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, name, description, null, null).visitEnd();;
	}
}
