package stanhebben.zenscript.definitions;

import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import static stanhebben.zenscript.ZenTokener.*;

public class ParsedGlobalValue {
	private final ZenPosition position;
	private final String name;
	private final ZenType type;
	private final ParsedExpression value;
	
	ParsedGlobalValue(ZenPosition position, String name, ZenType type, ParsedExpression value){
		this.position = position;
		this.name = name;
		this.type = type;
		this.value = value;
		
	}

	public String getName() {
		return name;
	}

	public ZenPosition getPosition() {
		return position;
	}
	
	public ZenType getType() {
		return type;
	}
	
	public ParsedExpression getValue() {
		return value;
	}
	
	public static ParsedGlobalValue parse(ZenTokener parser, IEnvironmentGlobal environment) {
		//Start ("GLOBAL")
		Token startingPoint = parser.next();
		
		//Name ("globalName", "test")
		String name = parser.required(T_ID, "Global value requires a name!").getValue();
		
		//Type ("as type", "as IItemStack")
		ZenType type = ZenType.ANY;
		Token nee = parser.optional(T_AS);
		if(nee!=null) {
			type = ZenType.read(parser, environment);
		}
		
		//"="
		parser.required(T_ASSIGN, "Global has to be initialized!");
		
		//"value, <minecraft:dirt>"
		ParsedExpression value = ParsedExpression.read(parser, environment);
		
		//";"
		parser.required(T_SEMICOLON, "; expected");
		
		//throw it together
		return new ParsedGlobalValue(startingPoint.getPosition(), name, type, value);
	}
}
