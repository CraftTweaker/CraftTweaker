package com.blamejared.crafttweaker.impl.script.scriptrun;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

final class SuspiciousAwarePathList implements List<Path> {
    
    @SuppressWarnings("ClassCanBeRecord")
    private static final class SuspiciousAwarePathListIterator implements ListIterator<Path> {
        
        private final ListIterator<Path> delegate;
        private final PathMatcher suspiciousMatcher;
        
        SuspiciousAwarePathListIterator(final ListIterator<Path> delegate, final PathMatcher suspiciousMatcher) {
            
            this.delegate = delegate;
            this.suspiciousMatcher = suspiciousMatcher;
        }
        
        @Override
        public boolean hasNext() {
            
            return this.delegate.hasNext();
        }
        
        @Override
        public Path next() {
            
            return this.delegate.next();
        }
        
        @Override
        public boolean hasPrevious() {
            
            return this.delegate.hasPrevious();
        }
        
        @Override
        public Path previous() {
            
            return this.delegate.previous();
        }
        
        @Override
        public int nextIndex() {
            
            return this.delegate.nextIndex();
        }
        
        @Override
        public int previousIndex() {
            
            return this.delegate.previousIndex();
        }
        
        @Override
        public void remove() {
            
            this.delegate.remove();
        }
        
        @Override
        public void forEachRemaining(final Consumer<? super Path> action) {
            
            this.delegate.forEachRemaining(action);
        }
        
        @Override
        public void set(final Path path) {
            
            if(this.suspiciousMatcher.matches(path)) {
                throw new IllegalArgumentException("Suspicious element " + path);
            }
            
            this.delegate.set(path);
        }
        
        @Override
        public void add(final Path path) {
            
            if(this.suspiciousMatcher.matches(path)) {
                throw new IllegalArgumentException("Suspicious element " + path);
            }
            
            this.delegate.add(path);
        }
        
    }
    
    private final List<Path> delegate;
    private final PathMatcher suspiciousMatcher;
    private final Consumer<Path> logger;
    
    private SuspiciousAwarePathList(final List<Path> delegate, final PathMatcher suspiciousMatcher, final Consumer<Path> logger) {
        
        this.delegate = delegate;
        this.suspiciousMatcher = suspiciousMatcher;
        this.logger = logger;
    }
    
    static SuspiciousAwarePathList of(final PathMatcher suspiciousMatcher, final Consumer<Path> logger) {
        
        return new SuspiciousAwarePathList(new ArrayList<>(), suspiciousMatcher, logger);
    }
    
    
    @Override
    public boolean add(final Path path) {
        
        if(this.suspiciousMatcher.matches(path)) {
            this.logger.accept(path);
            return false;
        }
        
        return this.delegate.add(path);
    }
    
    @Override
    public boolean addAll(@NotNull final Collection<? extends Path> c) {
        
        boolean result = false;
        for(final Path p : c) {
            result |= this.add(p);
        }
        return result;
    }
    
    @Override
    public boolean addAll(final int index, @NotNull final Collection<? extends Path> c) {
        
        boolean result = false;
        int idx = index;
        for(final Path p : c) {
            final boolean r = this.addImpl(idx, p);
            result |= r;
            if(r) {
                idx++;
            }
        }
        return result;
    }
    
    @Override
    public int size() {
        
        return this.delegate.size();
    }
    
    @Override
    public boolean isEmpty() {
        
        return this.delegate.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        
        return this.delegate.contains(o);
    }
    
    @NotNull
    @Override
    public Iterator<Path> iterator() {
        
        return this.delegate.iterator();
    }
    
    @NotNull
    @Override
    public Object[] toArray() {
        
        return this.delegate.toArray();
    }
    
    @NotNull
    @Override
    public <T> T[] toArray(@NotNull final T[] a) {
        
        return this.delegate.toArray(a);
    }
    
    @Override
    public boolean remove(final Object o) {
        
        return this.delegate.remove(o);
    }
    
    @Override
    @SuppressWarnings("SlowListContainsAll")
    public boolean containsAll(@NotNull final Collection<?> c) {
        
        return this.delegate.containsAll(c);
    }
    
    @Override
    public boolean removeAll(@NotNull final Collection<?> c) {
        
        return this.delegate.removeAll(c);
    }
    
    @Override
    public boolean retainAll(@NotNull final Collection<?> c) {
        
        return this.delegate.retainAll(c);
    }
    
    @Override
    public void clear() {
        
        this.delegate.clear();
    }
    
    @Override
    public Path get(final int index) {
        
        return this.delegate.get(index);
    }
    
    @Override
    public Path set(final int index, final Path element) {
        
        if(this.suspiciousMatcher.matches(element)) {
            throw new IllegalArgumentException("Suspicious element " + element);
        }
        
        return this.delegate.set(index, element);
    }
    
    @Override
    public void add(final int index, final Path element) {
        
        if(!this.addImpl(index, element)) {
            throw new IllegalArgumentException("Suspicious element " + element);
        }
    }
    
    @Override
    public Path remove(final int index) {
        
        return this.delegate.remove(index);
    }
    
    @Override
    public int indexOf(final Object o) {
        
        return this.delegate.indexOf(o);
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        
        return this.delegate.lastIndexOf(o);
    }
    
    @NotNull
    @Override
    public ListIterator<Path> listIterator() {
        
        return new SuspiciousAwarePathListIterator(this.delegate.listIterator(), this.suspiciousMatcher);
    }
    
    @NotNull
    @Override
    public ListIterator<Path> listIterator(final int index) {
        
        return new SuspiciousAwarePathListIterator(this.delegate.listIterator(index), this.suspiciousMatcher);
    }
    
    @NotNull
    @Override
    public List<Path> subList(final int fromIndex, final int toIndex) {
        
        return new SuspiciousAwarePathList(this.delegate.subList(fromIndex, toIndex), this.suspiciousMatcher, this.logger);
    }
    
    @Override
    public void replaceAll(final UnaryOperator<Path> operator) {
        
        this.delegate.replaceAll(it -> {
            final Path result = operator.apply(it);
            if(this.suspiciousMatcher.matches(result)) {
                throw new UnsupportedOperationException("Cannot replace element %s with suspicious element %s".formatted(it, result));
            }
            return result;
        });
    }
    
    @Override
    public void sort(final Comparator<? super Path> c) {
        
        this.delegate.sort(c);
    }
    
    @Override
    public Spliterator<Path> spliterator() {
        
        return this.delegate.spliterator();
    }
    
    @Override
    public <T> T[] toArray(final IntFunction<T[]> generator) {
        
        return this.delegate.toArray(generator);
    }
    
    @Override
    public boolean removeIf(final Predicate<? super Path> filter) {
        
        return this.delegate.removeIf(filter);
    }
    
    @Override
    public Stream<Path> stream() {
        
        return this.delegate.stream();
    }
    
    @Override
    public Stream<Path> parallelStream() {
        
        return this.delegate.parallelStream();
    }
    
    @Override
    public void forEach(final Consumer<? super Path> action) {
        
        this.delegate.forEach(action);
    }
    
    private boolean addImpl(final int index, final Path p) {
        
        if(this.suspiciousMatcher.matches(p)) {
            this.logger.accept(p);
            return false;
        }
        
        try {
            this.delegate.add(index, p);
            return true;
        } catch(final IllegalArgumentException e) {
            return false;
        }
    }
    
}
