package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ZenWrapper {
	/**
	 * The class that is wrapped by this type.
	 * Use the qualified type here.
	 * We cannot use {@code Class<?>} here as that is not callable at compile time.
	 */
	String wrappedClass();

	/**
	 * The format on how to convert the parameter to the base type.
	 * E.g. {@code "%s.getInternal()}
	 */
	String conversionMethodFormat();

	/**
	 * CrT will call {@code String.format(displayStringFormat(), parameter)} when describing actions.
	 * Use this if you want the item to be displayed differently from its "toString" implementation.
	 * E.g. {@code "%s.getCommandString()"}
	 */
	String displayStringFormat() default "%s";
}
