package stanhebben.zenscript;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.ZenType;

import java.util.*;

import static stanhebben.zenscript.ZenTokener.*;

/**
 * Contains a parsed file.
 * <p>
 * A parsed file contains:
 * <ul>
 * <li>A set of imports</li>
 * <li>A set of parsed functions</li>
 * <li>A set of statuments</li>
 * </ul>
 * <p>
 * This parsed file cannot be executed by itself, but it can be compiled into a
 * module, possibly together with other files.
 *
 * @author Stan Hebben
 */
public class ZenParsedFile {
    
    private final String filename;
    private final String classname;
    private final List<Import> imports;
    private final Map<String, ParsedFunction> functions;
    private final Map<String, ParsedGlobalValue> globals = new HashMap<>();
    private final List<Statement> statements;
    private final IEnvironmentGlobal environmentScript;
    
    /**
     * Constructs and parses a given file.
     *
     * @param filename    parsed filename
     * @param classname   output class name
     * @param tokener     input tokener
     * @param environment compile environment
     */
    public ZenParsedFile(String filename, String classname, ZenTokener tokener, IEnvironmentGlobal environment) {
        this.filename = filename;
        this.classname = classname;
        
        imports = new ArrayList<>();
        functions = new HashMap<>();
        statements = new ArrayList<>();
        environmentScript = new EnvironmentScript(environment);
        
        tokener.setFile(this);
        
        while(tokener.peek() != null && tokener.peek().getType() == T_IMPORT) {
            Token start = tokener.next();
            
            List<String> importName = new ArrayList<>();
            Token tName = tokener.required(T_ID, "identifier expected");
            importName.add(tName.getValue());
            
            while(tokener.optional(T_DOT) != null) {
                Token tNamePart = tokener.required(T_ID, "identifier expected");
                importName.add(tNamePart.getValue());
            }
            
            String rename = null;
            if(tokener.optional(T_AS) != null) {
                Token tRename = tokener.required(T_ID, "identifier expected");
                rename = tRename.getValue();
            }
            
            tokener.required(T_SEMICOLON, "; expected");
            
            imports.add(new Import(start.getPosition(), importName, rename));
        }
        
        for(Import imprt : imports) {
            List<String> name = imprt.getName();
            IPartialExpression type = null;
            
            StringBuilder nameSoFar = new StringBuilder();
            
            for(String part : name) {
                if(type == null) {
                    nameSoFar.append(part);
                    type = environment.getValue(part, imprt.getPosition());
                    if(type == null) {
                        environment.error(imprt.getPosition(), "could not find package " + type);
                        break;
                    }
                } else {
                    nameSoFar.append('.').append(part);
                    type = type.getMember(imprt.getPosition(), environment, part);
                    if(type == null) {
                        environment.error(imprt.getPosition(), "could not find type or package " + nameSoFar);
                        break;
                    }
                }
            }
            
            if(type != null) {
                IZenSymbol symbol = type.toSymbol();
                if(symbol == null) {
                    environmentScript.error(imprt.getPosition(), "Not a valid type");
                } else {
                    environmentScript.putValue(imprt.getRename(), type.toSymbol(), imprt.getPosition());
                }
            } else {
                environmentScript.putValue(imprt.getRename(), new SymbolType(ZenType.ANY), imprt.getPosition());
            }
        }
        
        while(tokener.hasNext()) {
            Token next = tokener.peek();
            if (next.getType() == T_GLOBAL) {
            	ParsedGlobalValue value = ParsedGlobalValue.parse(tokener, environmentScript);
            	if(globals.containsKey(value.getName())) {
            		environment.warning(value.getPosition(), "Global already defined: " + value.getName());
            	}
            	globals.put(value.getName(), value);
            } else if(next.getType() == T_FUNCTION) {
                ParsedFunction function = ParsedFunction.parse(tokener, environmentScript);
                if(functions.containsKey(function.getName())) {
                    environment.error(function.getPosition(), "function " + function.getName() + " already exists");
                }
                functions.put(function.getName(), function);
            } else {
                statements.add(Statement.read(tokener, environmentScript, null));
            }
        }
    }
    
    public IEnvironmentGlobal getEnvironment() {
        return environmentScript;
    }
    
    /**
     * Gets the output classname for this file.
     *
     * @return output classname
     */
    public String getClassName() {
        return classname;
    }
    
    /**
     * Gets the input filename for this file.
     *
     * @return input filename
     */
    public String getFileName() {
        return filename;
    }
    
    /**
     * Gets the imports list.
     *
     * @return imports list
     */
    public List<Import> getImports() {
        return imports;
    }
    
    /**
     * Gets this file's script statements.
     *
     * @return script statement list
     */
    public List<Statement> getStatements() {
        return statements;
    }
    
    /**
     * Gets the functions defined inside this file.
     *
     * @return script functions
     */
    public Map<String, ParsedFunction> getFunctions() {
        return functions;
    }
    
    public Map<String, ParsedGlobalValue> getGlobals() {
    	return globals;
    }
    
    @Override
    public String toString() {
        return filename;
    }
}
