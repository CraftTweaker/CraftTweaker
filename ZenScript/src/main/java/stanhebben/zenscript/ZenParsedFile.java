/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static stanhebben.zenscript.ZenParser.*;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.definitions.Import;
import stanhebben.zenscript.definitions.ParsedFunction;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.statements.Statement;

/**
 *
 * @author Stanneke
 */
public class ZenParsedFile {
	private final String filename;
	private final String classname;
	private final List<Import> imports;
	private final Map<String, ParsedFunction> functions;
	private final List<Statement> statements;
	
	public ZenParsedFile(String filename, String classname, ZenParser parser, IEnvironmentGlobal environment) {
		this.filename = filename;
		this.classname = classname;
		
		imports = new ArrayList<Import>();
		functions = new HashMap<String, ParsedFunction>();
		statements = new ArrayList<Statement>();
		
		parser.setFile(this);
		
		while (parser.peek() != null && parser.peek().getType() == T_IMPORT) {
			Token start = parser.next();
			
			List<String> importName = new ArrayList<String>();
			Token tName = parser.required(T_ID, "identifier expected");
			importName.add(tName.getValue());
			
			while (parser.optional(T_DOT) != null) {
				Token tNamePart = parser.required(T_ID, "identifier expected");
				importName.add(tNamePart.getValue());
			}
			
			String rename = null;
			if (parser.optional(T_AS) != null) {
				Token tRename = parser.required(T_ID, "identifier expected");
				rename = tRename.getValue();
			}
			
			parser.required(T_SEMICOLON, "; expected");
			
			imports.add(new Import(start.getPosition(), importName, rename));
		}
		
		while (parser.hasNext()) {
			Token next = parser.peek();
			if (next.getType() == T_FUNCTION) {
				ParsedFunction function = ParsedFunction.parse(parser, environment);
				if (functions.containsKey(function.getName())) {
					environment.error(function.getPosition(), "function " + function.getName() + " already exists");
				}
				functions.put(function.getName(), function);
			} else {
				statements.add(Statement.read(parser, environment));
			}
		}
	}
	
	public String getClassName() {
		return classname;
	}
	
	public String getFileName() {
		return filename;
	}
	
	public List<Import> getImports() {
		return imports;
	}
	
	public List<Statement> getStatements() {
		return statements;
	}
	
	public Map<String, ParsedFunction> getFunctions() {
		return functions;
	}
}
