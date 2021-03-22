package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Supplier;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.IVanillaWrappingPredicate")
@Document("vanilla/api/predicate/IVanillaWrappingPredicate")
public interface IVanillaWrappingPredicate<T> {
    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.predicate.AnyDefaultingVanillaWrappingPredicate")
    @Document("vanilla/api/predicate/AnyDefaultingVanillaWrappingPredicate")
    abstract class AnyDefaulting<T> implements IVanillaWrappingPredicate<T> {
        private final Supplier<T> any;

        protected AnyDefaulting(final T any) {
            this(() -> any);
        }

        protected AnyDefaulting(final Supplier<T> any) {
            this.any = any;
        }

        @Override
        public final T toVanillaPredicate() {
            if (this.isAny()) {
                return Objects.requireNonNull(this.any.get());
            }
            return this.toVanilla();
        }

        public abstract boolean isAny();
        public abstract T toVanilla();
    }

    T toVanillaPredicate();
}
