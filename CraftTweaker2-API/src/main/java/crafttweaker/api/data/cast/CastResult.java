package crafttweaker.api.data.cast;

import crafttweaker.api.data.IllegalDataException;

/**
 * @author youyihj
 */
public interface CastResult<T> {
    T get();

    boolean isOk();

    static <T> CastResult<T> ok(T value) {
        return new Ok<>(value);
    }

    static <T> CastResult<T> fail(String message) {
        return new Fail<>(message);
    }

    @SuppressWarnings("unchecked")
    static <T> CastResult<T> nil() {
        return (CastResult<T>) Nil.INSTANCE;
    }

    class Ok<T> implements CastResult<T> {
        private final T value;

        public Ok(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean isOk() {
            return true;
        }
    }

    class Fail<T> implements CastResult<T> {
        private final String message;

        public Fail(String message) {
            this.message = message;
        }

        @Override
        public T get() {
            throw new IllegalDataException(message);
        }

        @Override
        public boolean isOk() {
            return false;
        }
    }

    enum Nil implements CastResult<Object> {
        INSTANCE;

        @Override
        public Object get() {
            return null;
        }

        @Override
        public boolean isOk() {
            return false;
        }
    }
}
