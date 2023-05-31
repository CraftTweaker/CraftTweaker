package com.blamejared.crafttweaker.api.event.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as being a <em>reveal method</em> for the {@link FabricEventBusWire}.
 *
 * <p>The <em>reveal method</em> is responsible for converting back the object that was obtained by wrapping a Fabric
 * event back into the return type of the functional interface, to allow for further processing.</p>
 *
 * <p>In the following, the term <em>owner</em> will be used to refer to the class where the method annotated by this
 * declaration is defined in, and <em>function</em> to refer to the functional interface representing the Fabric event
 * that must be converted. It is mandatory that the owner coincides with the class that acts as the wrapper.</p>
 *
 * <p>The method must be {@code public static} and have the owner as its sole accepted parameter. The return type must
 * match the one of the functional interface exactly, without considering any automatic conversions such as autoboxing
 * or superclass casting. Moreover, if the return type of the function is {@code void}, then no reveal method is allowed
 * to exist as any computations within would be automatically discarded.</p>
 *
 * <p>As an example, considering as function the prototype {@code boolean accept(Entity entity, Context context)} and
 * the class {@code ContextualEntityEvent} as owner, the resulting code snippet should be similar to the following,
 * method name aside:</p>
 *
 * <pre>
 *     {@code
 *     public class ContextualEntityEvent {
 *         @FabricWiredReveal
 *         public static boolean reveal(final ContextualEntityEvent event) {
 *             return event.booleanResult();
 *         }
 *     }
 *     }
 * </pre>
 *
 * @since 11.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FabricWiredReveal {}
