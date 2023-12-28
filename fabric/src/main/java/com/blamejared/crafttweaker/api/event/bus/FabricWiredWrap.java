package com.blamejared.crafttweaker.api.event.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as being a <em>wrap method</em> for the {@link FabricEventBusWire}.
 *
 * <p>The <em>wrap method</em> is responsible for converting a Fabric event from a functional interface into an object
 * that can then be used by {@link IEventBus}.</p>
 *
 * <p>In the following, the term <em>owner</em> will be used to refer to the class where the method annotated by this
 * declaration is defined in, and <em>function</em> to refer to the functional interface representing the Fabric event
 * that must be converted. It is mandatory that the owner coincides with the class that will act as the wrapper.</p>
 *
 * <p>The method must be {@code public static} and have the owner as its return type. The parameters must match exactly
 * in both type and order the ones of the function. It is thus disallowed for the wrap method to specify more or less
 * parameters, or to perform any reordering.</p>
 *
 * <p>As an example, considering as function the prototype {@code boolean accept(Entity entity, Context context)} and
 * the class {@code ContextualEntityEvent} as owner, the resulting code snippet should be similar to the following,
 * method name aside:</p>
 *
 * <pre>
 *     {@code
 *     public class ContextualEntityEvent {
 *         @FabricWiredWrap
 *         public static ContextualEntityEvent wrap(final Entity entity, final Context context) {
 *             return new ContextualEntityEvent(entity, ZenContext.of(context));
 *         }
 *     }
 *     }
 * </pre>
 *
 * @since 11.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FabricWiredWrap {}
