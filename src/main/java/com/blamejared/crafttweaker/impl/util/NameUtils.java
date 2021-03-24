package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;

/**
 * Set of utility methods related to names and naming in general.
 */
@Document("vanilla/api/util/NameUtils")
@ZenCodeType.Name("crafttweaker.util.NameUtils")
@ZenRegister
public final class NameUtils {
    /**
     * Creates a {@link ResourceLocation} from the given <code>input</code>, if possible, while fixing mistakes that
     * may be present in the string.
     *
     * @param input The string that should be fixed and converted to a {@link ResourceLocation}.
     * @return A {@link ResourceLocation} that represents the fixed input.
     * @throws IllegalArgumentException If the string cannot be automatically fixed.
     * @throws ResourceLocationException If the string cannot be automatically fixed.
     */
    @ZenCodeType.Method
    public static ResourceLocation fromFixedName(final String input) {
        return fromFixedName(input, (fix, mistakes) -> {});
    }
    
    /**
     * Creates a {@link ResourceLocation} from the given <code>input</code>, if possible, while fixing mistakes that
     * may be present in the string.
     *
     * @param input The string that should be fixed and converted to a {@link ResourceLocation}.
     * @param mistakeHandler A bi-consumer that gets called if there were any mistakes in the original string. The first
     *                       element is the fixed string, and the second is a list of strings containing explanations
     *                       for all the identified mistakes.
     * @return A {@link ResourceLocation} that represents the fixed input.
     * @throws IllegalArgumentException If the string cannot be automatically fixed.
     * @throws ResourceLocationException If the string cannot be automatically fixed.
     */
    @ZenCodeType.Method
    public static ResourceLocation fromFixedName(final String input, final BiConsumer<String, List<String>> mistakeHandler) {
        return new ResourceLocation(CraftTweaker.MODID, fixing(input, mistakeHandler));
    }
    
    /**
     * Attempts to automatically fix the given <code>input</code> string, if possible, so that it can be used to build a
     * well-formed {@link ResourceLocation}.
     *
     * @param input The string that should be fixed to a {@link ResourceLocation}-compatible format.
     * @return The fixed string.
     * @throws IllegalArgumentException If the string cannot be automatically fixed.
     * @throws ResourceLocationException If the string cannot be automatically fixed.
     */
    @ZenCodeType.Method
    public static String fixing(final String input) {
        return fixing(input, (fix, mistakes) -> {});
    }
    
    /**
     * Attempts to automatically fix the given <code>input</code> string, if possible, so that it can be used to build a
     * well-formed {@link ResourceLocation}.
     *
     * @param input The string that should be fixed to a {@link ResourceLocation}-compatible format.
     * @param mistakeHandler A bi-consumer that gets called if there were any mistakes in the original string. The first
     *                       element is the fixed string, and the second is a list of strings containing explanations
     *                       for all the identified mistakes.
     * @return The fixed string.
     * @throws IllegalArgumentException If the string cannot be automatically fixed.
     * @throws ResourceLocationException If the string cannot be automatically fixed.
     */
    @ZenCodeType.Method
    public static String fixing(final String input, final BiConsumer<String, List<String>> mistakeHandler) {
        final List<String> mistakes = new ArrayList<>();
        /*mutable*/ String fixed = input;
    
        if (fixed.indexOf(':') >= 0) {
            mistakes.add("- it cannot contain colons (':')");
            fixed = fixed.replace(':', '.');
        }
        if (fixed.indexOf(' ') >= 0) {
            mistakes.add("- it cannot contain spaces");
            fixed = fixed.replace(' ', '.');
        }
        if (!fixed.toLowerCase(Locale.ENGLISH).equals(fixed)) {
            mistakes.add("- it must be all lowercase");
            fixed = fixed.toLowerCase(Locale.ENGLISH);
        }
    
        try {
            new ResourceLocation(CraftTweaker.MODID, fixed); // Initializing it for side effects
            if (!mistakes.isEmpty()) {
                mistakeHandler.accept(fixed, mistakes);
            }
            return fixed;
        } catch (final ResourceLocationException | IllegalArgumentException e) {
            mistakes.add("- " + e.getMessage());
            mistakeHandler.accept("<unable to auto-fix, best attempt: " + fixed + '>', mistakes);
            throw e;
        }
    }
}
