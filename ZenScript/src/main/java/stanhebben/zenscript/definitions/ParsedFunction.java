/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.definitions;

import static stanhebben.zenscript.ZenTokener.*;

import java.util.ArrayList;
import java.util.List;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedFunction {
	public static ParsedFunction parse(ZenTokener parser, IEnvironmentGlobal environment) {
		parser.next();
		Token tName = parser.required(ZenTokener.T_ID, "identifier expected");

		// function (argname [as type], argname [as type], ...) [as type] { ...contents... }
		parser.required(T_BROPEN, "( expected");

		List<ParsedFunctionArgument> arguments = new ArrayList<ParsedFunctionArgument>();
		if (parser.optional(T_BRCLOSE) == null) {
			Token argName = parser.required(T_ID, "identifier expected");
			ZenType type = ZenTypeAny.INSTANCE;
			if (parser.optional(T_AS) != null) {
				type = ZenType.read(parser, environment);
			}
			
			arguments.add(new ParsedFunctionArgument(argName.getValue(), type));

			while (parser.optional(T_COMMA) != null) {
				Token argName2 = parser.required(T_ID, "identifier expected");
				ZenType type2 = ZenTypeAny.INSTANCE;
				if (parser.optional(T_AS) != null) {
					type2 = ZenType.read(parser, environment);
				}
				
				arguments.add(new ParsedFunctionArgument(argName2.getValue(), type2));
			}
			
			parser.required(T_BRCLOSE, ") expected");
		}
		
		ZenType type = ZenTypeAny.INSTANCE;
		if (parser.optional(T_AS) != null) {
			type = ZenType.read(parser, environment);
		}
		
		parser.required(T_AOPEN, "{ expected");
		
		Statement[] statements;
		if (parser.optional(T_ACLOSE) != null) {
			statements = new Statement[0];
		} else {
			ArrayList<Statement> statementsAL = new ArrayList<Statement>();

			while (parser.optional(T_ACLOSE) == null) {
				statementsAL.add(Statement.read(parser, environment));
			}
			statements = statementsAL.toArray(new Statement[statementsAL.size()]);
		}
		
		return new ParsedFunction(tName.getPosition(), tName.getValue(), arguments, type, statements);
	}
	
	private final ZenPosition position;
	private final String name;
	private final List<ParsedFunctionArgument> arguments;
	private final ZenType returnType;
	private final Statement[] statements;
	
	private final String signature;
	
	private ParsedFunction(ZenPosition position, String name, List<ParsedFunctionArgument> arguments, ZenType returnType, Statement[] statements) {
		this.position = position;
		this.name = name;
		this.arguments = arguments;
		this.returnType = returnType;
		this.statements = statements;
		
		StringBuilder sig = new StringBuilder();
		sig.append("(");
		for (ParsedFunctionArgument argument : arguments) {
			sig.append(argument.getType().getSignature());
		}
		sig.append(")");
		sig.append(returnType.getSignature());
		signature = sig.toString();
	}
	
	public ZenPosition getPosition() {
		return position;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public ZenType getReturnType() {
		return returnType;
	}
	
	public List<ParsedFunctionArgument> getArguments() {
		return arguments;
	}
	
	public ZenType[] getArgumentTypes() {
		ZenType[] result = new ZenType[arguments.size()];
		for (int i = 0; i < arguments.size(); i++) {
			result[i] = arguments.get(i).getType();
		}
		return result;
	}
	
	public Statement[] getStatements() {
		return statements;
	}
}
