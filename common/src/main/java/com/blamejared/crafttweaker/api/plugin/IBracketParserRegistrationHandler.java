package com.blamejared.crafttweaker.api.plugin;

import net.minecraft.resources.ResourceLocation;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Handles registration of parsers for bracket expressions.
 *
 * <p>A bracket expression in ZenCode is defined as anything starting with a {@code <} character. It is up to the
 * {@link BracketExpressionParser} to parse the code and define what needs to be done.</p>
 *
 * <p>This definition is slightly restricted in CraftTweaker to allow for easier parsing and better cooperation. Each
 * bracket parser has a name which uniquely identifies it in the context of a
 * {@linkplain com.blamejared.crafttweaker.api.zencode.IScriptLoader loader}. When a {@code <} sign is encountered, the
 * first set of tokens until a colon ({@code :}) is read. The tokens are used to create a string which forms the name of
 * the parser. The current loader and the name are then used to identify the parser to invoke, which is then handed the
 * rest of the bracket expression.</p>
 *
 * <p>As an example, when an expression like {@code <}{@code item:minecraft:dirt>} is found, {@code item} is used to
 * build a string (namely, {@code "item"}) which is then used to identify the "item" bracket parser for the current
 * loader. If a bracket is found, then it is invoked with the remaining token stream (simplifying, with
 * {@code minecraft:dirt>}). It is up to the bracket expression parser to then figure out what to do.</p>
 *
 * @since 9.1.0
 */
public interface IBracketParserRegistrationHandler {
    
    /**
     * Holds information relative to a dumper for a specific bracket expression.
     *
     * <p>A dumper for a bracket expression, usually known as a <em>bracket dumper</em>, is defined as something that
     * can provide a list of all valid expressions for a specific bracket expression. An example could be a list of all
     * valid items for an "item" bracket expression. If compiling a complete list is not possible, then a partial list
     * containing a representative subset of valid expressions can also be provided.</p>
     *
     * @param subCommandName The name of the sub command that will be used in {@code /ct dump} to invoke this dumper.
     * @param outputFileName The name of the file where all valid expressions will be written to if requested by the
     *                       user.
     * @param data           A {@link Supplier} for a {@link Stream} of strings representing all valid expressions. Note
     *                       that the returned stream should be newly created every time inside the supplier, since the
     *                       supplier might be called multiple times.
     *
     * @since 9.1.0
     */
    record DumperData(String subCommandName, String outputFileName, Supplier<Stream<String>> data) {
        
        /**
         * Constructs a new bracket dumper using the sub command name for the name of the dump file.
         *
         * @param subCommandName The name of the sub command that will be used in {@code /ct dump} to invoke this
         *                       dumper.
         * @param data           A {@link Supplier} for a {@link Stream} of strings representing all valid expressions.
         *                       Note that the returned stream should be newly created every time inside the supplier,
         *                       since the supplier might be called multiple times.
         *
         * @since 9.1.0
         */
        public DumperData(final String subCommandName, final Supplier<Stream<String>> data) {
            
            this(subCommandName, subCommandName, data);
        }
        
    }
    
    /**
     * Registers a bracket expression parser that targets the specified parser name when within the given loader.
     *
     * <p>Refer to the {@linkplain IBracketParserRegistrationHandler main class documentation} for more information on
     * bracket parsers.</p>
     *
     * @param loader       A string representing the loader in which the bracket expression should be parsed by the
     *                     parser being registered.
     * @param parserName   The name of the bracket that identifies this parser.
     * @param parser       A {@link BracketExpressionParser} instance that is responsible for parsing the bracket and
     *                     building the resulting expression.
     * @param parserDumper A {@link DumperData} holding information relative to expression dumping, if available;
     *                     {@code null} otherwise.
     *
     * @since 9.1.0
     */
    void registerParserFor(final String loader, final String parserName, final BracketExpressionParser parser, final DumperData parserDumper);
    
    /**
     * Registers a bracket expression parser that targets the specified parser name when within the given loader.
     *
     * <p>Refer to the {@linkplain IBracketParserRegistrationHandler main class documentation} for more information on
     * bracket parsers.</p>
     *
     * <p>No {@link DumperData} is provided in this case, thus disallowing any possibility of dumping valid
     * expressions for this bracket.</p>
     *
     * @param loader     A string representing the loader in which the bracket expression should be parsed by the
     *                   parser being registered.
     * @param parserName The name of the bracket that identifies this parser.
     * @param parser     A {@link BracketExpressionParser} instance that is responsible for parsing the bracket and
     *                   building the resulting expression.
     *
     * @since 9.1.0
     */
    default void registerParserFor(final String loader, final String parserName, final BracketExpressionParser parser) {
        
        this.registerParserFor(loader, parserName, parser, null);
    }
    
    /**
     * Registers a bracket expression parser that targets the specified parser name when within the given loader.
     *
     * <p>Refer to the {@linkplain IBracketParserRegistrationHandler main class documentation} for more information on
     * bracket parsers.</p>
     *
     * <p>The {@code parser} and {@code validator} methods are used to build a default {@link BracketExpressionParser}
     * instance that does what is supposed to. Refer to the documentation for the parameters for more information
     * relative to the restrictions imposed on the signature of these methods.</p>
     *
     * @param loader     A string representing the loader in which the bracket expression should be parsed by the parser
     *                   being registered.
     * @param parserName The name of the bracket that identifies this parser.
     * @param parser     The {@link Method} responsible for resolution of the bracket parser. The method must be located
     *                   in a class that is exposed to ZenCode, be public and static, and have a single {@link String}
     *                   parameter as input. The return type should also be a subtype of
     *                   {@link com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable}. The method will be
     *                   called automatically by CraftTweaker with the entire token stream between the first colon and
     *                   the last closing bracket (i.e. between the first {@code :} and the ending {@code >}) read and
     *                   converted to a string. It is up to the method to parse the string. If a validator is provided,
     *                   the validator will be invoked before this method.
     * @param validator  The {@link Method} responsible for verifying that the expression given to the bracket parser is
     *                   correct. The method must be public and static, and have a single input parameter of type
     *                   {@link String}. The return type must be {@code boolean}. The method will be called by
     *                   CraftTweaker with the entire token stream between the first colon and the last closing bracket
     *                   (i.e. between the first {@code :} and the ending {@code >}) read and converted to a string. It
     *                   is up to the validator to parse the string and return {@code true} or {@code false} depending
     *                   on the string's validity. If no validator is desired, this parameter can be {@code null}.
     * @param dumper     A {@link DumperData} holding information relative to expression dumping, if available; or
     *                   {@code null} if not desired.
     *
     * @since 9.1.0
     */
    void registerParserFor(final String loader, final String parserName, final Method parser, final Method validator, final DumperData dumper);
    
    /**
     * Registers a bracket expression parser that targets the specified parser name when within the given loader.
     *
     * <p>Refer to the {@linkplain IBracketParserRegistrationHandler main class documentation} for more information on
     * bracket parsers.</p>
     *
     * <p>The {@code parser} method is used to build a default {@link BracketExpressionParser} instance that does what
     * is supposed to. Refer to the documentation for the parameters for more information relative to the restrictions
     * imposed on the signature of the method.</p>
     *
     * <p>The created expression parser will have <strong>no</strong> validator.</p>
     *
     * @param loader     A string representing the loader in which the bracket expression should be parsed by the parser
     *                   being registered.
     * @param parserName The name of the bracket that identifies this parser.
     * @param parser     The {@link Method} responsible for resolution of the bracket parser. The method must be located
     *                   in a class that is exposed to ZenCode, be public and static, and have a single {@link String}
     *                   parameter as input. The return type should also be a subtype of
     *                   {@link com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable}. The method will be
     *                   called automatically by CraftTweaker with the entire token stream between the first colon and
     *                   the last closing bracket (i.e. between the first {@code :} and the ending {@code >}) read and
     *                   converted to a string. It is up to the method to parse the string.
     * @param dumper     A {@link DumperData} holding information relative to expression dumping, if available; or
     *                   {@code null} if not desired.
     *
     * @since 9.1.0
     */
    default void registerParserFor(final String loader, final String parserName, final Method parser, final DumperData dumper) {
        
        this.registerParserFor(loader, parserName, parser, null, dumper);
    }
    
    /**
     * Registers a bracket expression parser that targets the specified parser name when within the given loader.
     *
     * <p>Refer to the {@linkplain IBracketParserRegistrationHandler main class documentation} for more information on
     * bracket parsers.</p>
     *
     * <p>The {@code parser} and {@code validator} methods are used to build a default {@link BracketExpressionParser}
     * instance that does what is supposed to. Refer to the documentation for the parameters for more information
     * relative to the restrictions imposed on the signature of these methods.</p>
     *
     * <p>The created expression parser will not allow dumping of its expressions.</p>
     *
     * @param loader     A string representing the loader in which the bracket expression should be parsed by the parser
     *                   being registered.
     * @param parserName The name of the bracket that identifies this parser.
     * @param parser     The {@link Method} responsible for resolution of the bracket parser. The method must be located
     *                   in a class that is exposed to ZenCode, be public and static, and have a single {@link String}
     *                   parameter as input. The return type should also be a subtype of
     *                   {@link com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable}. The method will be
     *                   called automatically by CraftTweaker with the entire token stream between the first colon and
     *                   the last closing bracket (i.e. between the first {@code :} and the ending {@code >}) read and
     *                   converted to a string. It is up to the method to parse the string. If a validator is provided,
     *                   the validator will be invoked before this method.
     * @param validator  The {@link Method} responsible for verifying that the expression given to the bracket parser is
     *                   correct. The method must be public and static, and have a single input parameter of type
     *                   {@link String}. The return type must be {@code boolean}. The method will be called by
     *                   CraftTweaker with the entire token stream between the first colon and the last closing bracket
     *                   (i.e. between the first {@code :} and the ending {@code >}) read and converted to a string. It
     *                   is up to the validator to parse the string and return {@code true} or {@code false} depending
     *                   on the string's validity. If no validator is desired, this parameter can be {@code null}.
     *
     * @since 9.1.0
     */
    default void registerParserFor(final String loader, final String parserName, final Method parser, final Method validator) {
        
        this.registerParserFor(loader, parserName, parser, validator, null);
    }
    
    /**
     * Registers a bracket expression parser that targets the specified parser name when within the given loader.
     *
     * <p>Refer to the {@linkplain IBracketParserRegistrationHandler main class documentation} for more information on
     * bracket parsers.</p>
     *
     * <p>The {@code parser} method is used to build a default {@link BracketExpressionParser} instance that does what
     * is supposed to. Refer to the documentation for the parameters for more information relative to the restrictions
     * imposed on the signature of the method.</p>
     *
     * <p>The created expression parser will have <strong>no</strong> validator and no possibility of dumping valid
     * expressions.</p>
     *
     * @param loader     A string representing the loader in which the bracket expression should be parsed by the parser
     *                   being registered.
     * @param parserName The name of the bracket that identifies this parser.
     * @param parser     The {@link Method} responsible for resolution of the bracket parser. The method must be located
     *                   in a class that is exposed to ZenCode, be public and static, and have a single {@link String}
     *                   parameter as input. The return type should also be a subtype of
     *                   {@link com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable}. The method will be
     *                   called automatically by CraftTweaker with the entire token stream between the first colon and
     *                   the last closing bracket (i.e. between the first {@code :} and the ending {@code >}) read and
     *                   converted to a string. It is up to the method to parse the string.
     *
     * @since 9.1.0
     */
    default void registerParserFor(final String loader, final String parserName, final Method parser) {
        
        this.registerParserFor(loader, parserName, parser, (Method) null);
    }
    
    /**
     * Registers the given enum class as a valid target for the built-in {@code enum} bracket parser.
     *
     * <p>The enumeration will then be accessible through a bracket structured like the following:
     * {@code <}{@code constant:}<em>id</em>{@code :}<em>constant</em>{@code >}, where <em>id</em> represents the ID
     * used to register the enumeration and <em>constant</em> the name of the constant that should be accessed.</p>
     *
     * @param loader    A string representing the loader in which the enum should be available.
     * @param id        A {@link ResourceLocation} that uniquely identifies the enumeration class.
     * @param enumClass The actual enum class.
     * @param <T>       The type of the enumeration that is being registered.
     *
     * @since 9.1.0
     */
    <T extends Enum<T>> void registerEnumForBracket(final String loader, final ResourceLocation id, final Class<T> enumClass);
    
}
