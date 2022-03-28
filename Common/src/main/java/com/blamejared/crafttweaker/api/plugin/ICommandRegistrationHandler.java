package com.blamejared.crafttweaker.api.plugin;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Consumer;

/**
 * Handles registration of commands for the {@code /ct} main command.
 *
 * <p>All "root" and "sub" terms are re-defined in this context not to consider {@code /ct} as the actual root. In other
 * words, registering a root command named {@code foo} will actually register a sub-command accessible through
 * {@code /ct foo}; whereas registering a sub-command {@code bar} for the command {@code baz} leads to the sub-command
 * being accessible through {@code /ct baz bar}.</p>
 *
 * @since 9.1.0
 */
public interface ICommandRegistrationHandler {
    
    /**
     * Represents an operation that is responsible for construction of the command given a builder.
     *
     * <p>This is a {@linkplain FunctionalInterface functional interface} whose functional method is
     * {@link #accept(Object)}. This interface effectively acts as a redefinition of {@link Consumer} albeit
     * specialized for a specific use-case for better semantics.</p>
     *
     * @since 9.1.0
     */
    @FunctionalInterface
    interface CommandBuilder extends Consumer<LiteralArgumentBuilder<CommandSourceStack>> {
        
        /**
         * Builds the command on the given builder.
         *
         * <p>Calling this method should be preferred to calling {@link #accept(Object)} to provide better semantics
         * when dealing with the code.</p>
         *
         * @param builder The builder on which the command should be built on.
         *
         * @since 9.1.0
         */
        default void buildCommand(final LiteralArgumentBuilder<CommandSourceStack> builder) {
            
            this.accept(builder);
        }
        
    }
    
    /**
     * Registers a new root command with the given name and description.
     *
     * <p>The given command will then be accessible at the root level, right after the {@code /ct} main command.</p>
     *
     * @param commandId   The name of the command. Conventionally, the string should be all lower-case and a single word
     *                    if possible. Multiple words should be merged together with {@code snake_case}.
     * @param description A {@link MutableComponent} acting as the description of the command, used in {@code /ct help}
     *                    to guide the user.
     * @param builder     A {@link CommandBuilder} used to create the actual command code.
     *
     * @since 9.1.0
     */
    void registerRootCommand(final String commandId, final MutableComponent description, final CommandBuilder builder);
    
    /**
     * Registers a new sub command to the given parent with the specified name and description.
     *
     * <p>The parent command must exist, otherwise an error on registration will occur.</p>
     *
     * @param parentCommand The name of the parent command.
     * @param commandId     The name of the sub-command. Conventionally, the string should be all lowercase and a single
     *                      word if possible. Multiple words should be merged together with {@code snake_case}.
     * @param description   A {@link MutableComponent} acting as the description of the command, used in
     *                      {@code /ct help} to guide the user.
     * @param builder       A {@link CommandBuilder} used to create the actual command code.
     *
     * @since 9.1.0
     */
    void registerSubCommand(final String parentCommand, final String commandId, final MutableComponent description, final CommandBuilder builder);
    
    /**
     * Registers a new command which can be used to dump brackets.
     *
     * <p>This can be seen as the command equivalent to {@link IBracketParserRegistrationHandler.DumperData}, and the
     * two components work in synergy with one another.</p>
     *
     * @param dumpId      The identifier that will be used to invoke this dump command.
     * @param description A {@link MutableComponent} acting as the description of the command, used in {@code /ct help}
     *                    to guide the user.
     * @param builder     A {@link CommandBuilder} used to create the actual command code.
     *
     * @since 9.1.0
     */
    void registerDump(final String dumpId, final MutableComponent description, final CommandBuilder builder);
    
}
