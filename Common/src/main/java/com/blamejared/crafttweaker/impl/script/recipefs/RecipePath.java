package com.blamejared.crafttweaker.impl.script.recipefs;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

final class RecipePath implements Path {
    
    private static final String[] EMPTY_COMPONENTS = new String[0];
    private static final String[] ROOT_COMPONENTS = new String[0];
    
    private final RecipeFileSystem fs;
    private final String[] components;
    private final int last;
    private final boolean absolute;
    
    private int hash;
    
    private RecipePath(final RecipeFileSystem fs, final String[] components, final boolean absolute) {
        
        this.fs = fs;
        this.last = (this.components = components).length - 1;
        this.absolute = absolute;
        this.hash = 0;
    }
    
    static RecipePath of(final RecipeFileSystem fs, final String toParse) {
        
        Objects.requireNonNull(fs);
        Objects.requireNonNull(toParse);
        if(toParse.isEmpty()) {
            return emptyPath(fs);
        }
        
        final boolean absolute = toParse.charAt(0) == '/';
        final String path = absolute ? toParse.substring(1) : toParse;
        
        if(path.isEmpty()) {
            return rootPath(fs);
        }
        
        final String slightlyNormalized = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
        final String[] components = slightlyNormalized.split(Pattern.quote("/"));
        assert components.length != 0 : "Waku waku";
        
        for(final String component : components) {
            if(component.isEmpty()) {
                throw new IllegalArgumentException("Invalid path " + toParse);
            }
        }
        
        return new RecipePath(fs, components, absolute);
    }
    
    private static RecipePath emptyPath(final RecipeFileSystem fs) {
        
        return new RecipePath(fs, EMPTY_COMPONENTS, false);
    }
    
    private static RecipePath rootPath(final RecipeFileSystem fs) {
        
        return new RecipePath(fs, ROOT_COMPONENTS, true);
    }
    
    @NotNull
    @Override
    public FileSystem getFileSystem() {
        
        return this.fs;
    }
    
    @Override
    public boolean isAbsolute() {
        
        return this.absolute;
    }
    
    @Override
    public Path getRoot() {
        
        if(this.isAbsolute()) {
            return rootPath(this.fs);
        }
        return null;
    }
    
    @Override
    public Path getFileName() {
        
        if(this.isEmpty()) {
            return this;
        }
        if(this.isRoot()) {
            return null;
        }
        
        return of(this.fs, this.components[this.last]);
    }
    
    @Override
    public Path getParent() {
        
        if(this.isRoot()) {
            return null;
        }
        
        final String path = this.computeSubPath(0, this.getNameCount() - 1, true);
        return of(this.fs, path);
    }
    
    @Override
    public int getNameCount() {
        
        return this.isRoot() ? 0 : this.last + 1;
    }
    
    @NotNull
    @Override
    public Path getName(final int index) {
        
        if(this.isRoot() || index < 0 || index > this.last) {
            throw new IllegalArgumentException(Integer.toString(index));
        }
        return of(this.fs, this.components[index]);
    }
    
    @NotNull
    @Override
    public Path subpath(final int beginIndex, final int endIndex) {
        
        if(this.isRoot()) {
            throw new IllegalArgumentException();
        }
        if(beginIndex < 0 || beginIndex > this.last) {
            throw new IllegalArgumentException(Integer.toString(beginIndex));
        }
        if(endIndex < 0 || endIndex > this.last || endIndex <= beginIndex) {
            throw new IllegalArgumentException(Integer.toString(endIndex));
        }
        final String sub = this.computeSubPath(beginIndex, endIndex, false);
        return of(this.fs, sub);
    }
    
    @Override
    public boolean startsWith(@NotNull final Path other) {
        
        Objects.requireNonNull(other);
        if(!(other instanceof RecipePath path)) {
            return false;
        }
        if(this.absolute != path.absolute) {
            return false;
        }
        if(this.isEmpty()) {
            return path.isEmpty();
        }
        if(this.isRoot()) {
            return path.isRoot();
        }
        final int pathCount = path.getNameCount();
        if(this.getNameCount() < pathCount) {
            return false;
        }
        
        for(int i = 0; i < pathCount; ++i) {
            if(!this.components[i].equals(path.components[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public boolean endsWith(@NotNull final Path other) {
        
        Objects.requireNonNull(other);
        if(!(other instanceof RecipePath path)) {
            return false;
        }
        if(this.absolute != path.absolute) {
            return false;
        }
        if(this.isEmpty()) {
            return path.isEmpty();
        }
        if(this.isRoot()) {
            return path.isRoot();
        }
        final int ourCount = this.getNameCount();
        final int pathCount = path.getNameCount();
        if(ourCount < pathCount) {
            return false;
        }
        
        for(int i = 0; i < pathCount; ++i) {
            if(!this.components[ourCount - 1 - i].equals(path.components[pathCount - 1 - i])) {
                return false;
            }
        }
        
        return true;
    }
    
    @NotNull
    @Override
    public Path normalize() {
        
        return this.isRoot() || this.isEmpty() ? this : of(this.fs, this.normalizePath());
    }
    
    @NotNull
    @Override
    public Path resolve(@NotNull final Path other) {
        
        Objects.requireNonNull(other);
        if(!(other instanceof RecipePath path)) {
            throw new ProviderMismatchException();
        }
        if(path.isAbsolute()) {
            return path;
        }
        if(path.isEmpty()) {
            return this;
        }
        
        final int thisLength = this.components.length;
        final int theirLength = path.components.length;
        final int newLength = thisLength + theirLength;
        final String[] newComponents = new String[newLength];
        System.arraycopy(this.components, 0, newComponents, 0, thisLength);
        System.arraycopy(path.components, 0, newComponents, thisLength, theirLength);
        return of(this.fs, this.computeSubPath(0, newLength, this.absolute, newComponents));
    }
    
    @NotNull
    @Override
    public Path relativize(@NotNull final Path other) {
        
        Objects.requireNonNull(other);
        if(!(other instanceof RecipePath path)) {
            throw new ProviderMismatchException();
        }
        if(this.equals(other)) {
            return emptyPath(this.fs);
        }
        final boolean thisAbsolute = this.isAbsolute();
        if(thisAbsolute != path.isAbsolute()) {
            throw new IllegalArgumentException("Cannot relativize with mismatching path types");
        }
        if(this.isEmpty()) {
            return path;
        }
        
        int divergenceIndex = -1;
        final int ourComponents = this.getNameCount();
        final int theirComponents = path.getNameCount();
        final int min = Math.min(ourComponents, theirComponents);
        for(int i = 0; i < min; ++i) {
            final String ourComponent = this.components[i];
            final String theirComponent = path.components[i];
            if(!ourComponent.equals(theirComponent)) {
                divergenceIndex = i;
                break;
            }
        }
        
        if(divergenceIndex == -1) {
            if(theirComponents < ourComponents) {
                final int walkUpLength = ourComponents - theirComponents;
                final String[] newComponents = new String[walkUpLength];
                for(int i = 0; i < walkUpLength; ++i) {
                    newComponents[i] = "..";
                }
                
                return of(this.fs, this.computeSubPath(0, walkUpLength, false, newComponents));
            }
            
            return of(this.fs, path.computeSubPath(ourComponents, theirComponents, false));
        }
        
        final int upwardsWalkerLength = ourComponents - divergenceIndex;
        final String[] subComponents = ((RecipePath) path.subpath(divergenceIndex, theirComponents)).components;
        
        final int length = upwardsWalkerLength + subComponents.length;
        final String[] newComponents = new String[length];
        for(int i = 0; i < upwardsWalkerLength; ++i) {
            newComponents[i] = "..";
        }
        System.arraycopy(subComponents, 0, newComponents, upwardsWalkerLength, subComponents.length);
        
        return of(this.fs, this.computeSubPath(0, length, false, newComponents));
    }
    
    @NotNull
    @Override
    public URI toUri() {
        
        try {
            return new URI("%s:%s".formatted(RecipeFileSystemProvider.SCHEME, this));
        } catch(final URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
    
    @NotNull
    @Override
    public Path toAbsolutePath() {
        
        if(this.isAbsolute()) {
            return this;
        }
        if(this.isEmpty()) {
            return rootPath(this.fs);
        }
        
        return of(this.fs, this.computeSubPath(0, this.components.length, true));
    }
    
    @NotNull
    @Override
    public Path toRealPath(@NotNull final LinkOption... options) {
        
        Objects.requireNonNull(options);
        return this.toAbsolutePath();
    }
    
    @NotNull
    @Override
    public WatchKey register(@NotNull final WatchService watcher, @NotNull final WatchEvent.Kind<?>[] events, final WatchEvent.Modifier... modifiers) {
        
        Objects.requireNonNull(watcher);
        Objects.requireNonNull(events);
        Objects.requireNonNull(modifiers);
        throw new UnsupportedOperationException("Watches not supported");
    }
    
    @Override
    public int compareTo(@NotNull final Path other) {
        
        final RecipePath path = (RecipePath) Objects.requireNonNull(other);
        if(this.isEmpty()) {
            return path.isEmpty() ? 0 : 1;
        }
        if(this.isRoot()) {
            return path.isRoot() ? 0 : -1;
        }
        return Arrays.compare(this.components, path.components);
    }
    
    @Override
    public boolean equals(final Object obj) {
        
        return obj == this || (obj instanceof RecipePath path && this.compareTo(path) == 0);
    }
    
    @Override
    public int hashCode() {
        
        return this.hash != 0 ? this.hash : (this.hash = Objects.hash(this.absolute, Arrays.hashCode(this.components)));
    }
    
    @Override
    public String toString() {
        
        return this.computeSubPath(0, this.components.length, this.isAbsolute());
    }
    
    SeekableByteChannel seekableByteChannel(final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        
        return this.fs.seekableByteChannel(this, options, attrs);
    }
    
    DirectoryStream<Path> directoryStream(final DirectoryStream.Filter<? super Path> filter) throws IOException {
        
        return this.fs.directoryStream(this, filter);
    }
    
    void directory(final FileAttribute<?>... attributes) throws IOException {
        
        this.fs.directory(this, attributes);
    }
    
    void yeet() throws IOException {
        
        this.fs.yeet(this);
    }
    
    void copyTo(final Path destination, final CopyOption... options) throws IOException {
        
        this.fs.copyTo(this, destination, options);
    }
    
    void moveTo(final Path destination, final CopyOption... options) throws IOException {
        
        this.fs.moveTo(this, destination, options);
    }
    
    boolean sameFile(final Path other) throws IOException {
        
        return this.fs.sameFile(this, other);
    }
    
    boolean hidden() throws IOException {
        
        return this.fs.hidden(this);
    }
    
    FileStore fileStore() throws IOException {
        
        return this.fs.fileStore(this);
    }
    
    void access(final AccessMode... modes) throws IOException {
        
        this.fs.access(this, modes);
    }
    
    <V extends FileAttributeView> V fileAttributeView(final Class<V> type, final LinkOption... options) {
        
        return this.fs.fileAttributeView(this, type, options);
    }
    
    <A extends BasicFileAttributes> A attributes(final Class<A> type, final LinkOption... options) throws IOException {
        
        return this.fs.attributes(this, type, options);
    }
    
    Map<String, Object> attributes(final String attributes, final LinkOption... options) throws IOException {
        
        return this.fs.attributes(this, attributes, options);
    }
    
    void attributes(final String attribute, final Object value, final LinkOption... options) throws IOException {
        
        this.fs.attributes(this, attribute, value, options);
    }
    
    InputStream input(final OpenOption... options) throws IOException {
        
        return this.fs.input(this, options);
    }
    
    OutputStream output(final OpenOption... options) throws IOException {
        
        return this.fs.output(this, options);
    }
    
    FileChannel fileChannel(final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        
        return this.fs.fileChannel(this, options, attrs);
    }
    
    AsynchronousFileChannel asyncFileChannel(final Set<? extends OpenOption> options, final Executor executor, final FileAttribute<?>... attrs) throws IOException {
        
        return this.fs.asyncFileChannel(this, options, executor, attrs);
    }
    
    void symLink(final Path target, final FileAttribute<?>... attrs) throws IOException {
        
        this.fs.symLink(this, target, attrs);
    }
    
    void link(final Path target) throws IOException {
        
        this.fs.link(this, target);
    }
    
    boolean yeetExisting() throws IOException {
        
        return this.fs.yeetExisting(this);
    }
    
    Path symLink() throws IOException {
        
        return this.fs.symLink(this);
    }
    
    private boolean isEmpty() {
        
        return this.components == EMPTY_COMPONENTS;
    }
    
    private boolean isRoot() {
        
        return this.components == ROOT_COMPONENTS;
    }
    
    private String normalizePath() {
        
        final String[] oldComponents = this.components;
        final List<String> components = new ArrayList<>();
        int idx = 0;
        
        for(final String oldComponent : oldComponents) {
            if(".".equals(oldComponent)) {
                continue;
            }
            if("..".equals(oldComponent)) {
                components.remove(--idx);
                continue;
            }
            components.add(oldComponent);
            ++idx;
        }
        
        return computeSubPath(0, idx, this.absolute, components.toArray(String[]::new));
    }
    
    private String computeSubPath(final int begin, final int end, final boolean absolute) {
        
        return computeSubPath(begin, end, absolute, this.components);
    }
    
    private String computeSubPath(final int begin, final int end, final boolean absolute, final String[] components) {
        
        final StringBuilder builder = new StringBuilder(absolute ? "/" : "");
        for(int i = begin; i < end; ++i) {
            builder.append(components[i]).append('/');
        }
        if(end - begin > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
    
}
