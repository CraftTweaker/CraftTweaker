package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the given class contains an expansion for a certain type. The
 * expansions themselves are given through annotated static methods.
 * 
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ZenExpansion {
	/**
	 * Contains the type of the annotated class. May be any kind of ZenScript
	 * type.
	 * 
	 * Examples:
	 * <ul>
	 * <li>int</li>
	 * <li>my.package.MyClass</li>
	 * <li>my.package.MyClass[]</li>
	 * <li>any</li>
	 * <li>any[]</li>
	 * <li>any[any]</li>
	 * </li>
	 * </ul>
	 * 
	 * @return expanded type
	 */
	public String value();
}
