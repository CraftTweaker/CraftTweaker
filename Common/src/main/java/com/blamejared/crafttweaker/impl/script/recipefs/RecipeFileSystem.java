package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.google.common.base.Suppliers;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.ProviderMismatchException;
import java.nio.file.ReadOnlyFileSystemException;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.regex.Pattern;

final class RecipeFileSystem extends FileSystem {
    
    private final RecipeFileSystemProvider provider;
    private final Collection<ScriptRecipe> recipes;
    private final RecipePath root;
    private final Supplier<FileStore> store;
    private final FileTime creationTime;
    private boolean closed;
    
    RecipeFileSystem(final RecipeFileSystemProvider provider, final Collection<ScriptRecipe> recipes) {
        
        this.provider = provider;
        this.recipes = recipes;
        this.root = RecipePath.of(this, "/");
        this.store = Suppliers.memoize(() -> new RecipeFileStore(recipes));
        this.creationTime = FileTime.from(Instant.now());
        this.closed = false;
    }
    
    @Override
    public FileSystemProvider provider() {
        
        return this.provider;
    }
    
    @Override
    public void close() {
        
        this.recipes.clear();
        this.provider.closing();
        this.closed = true;
    }
    
    @Override
    public boolean isOpen() {
        
        return !this.closed;
    }
    
    @Override
    public boolean isReadOnly() {
        
        return true;
    }
    
    @Override
    public String getSeparator() {
        
        return "/";
    }
    
    @Override
    public Iterable<Path> getRootDirectories() {
        
        this.open();
        return List.of(this.root);
    }
    
    @Override
    public Iterable<FileStore> getFileStores() {
        
        this.open();
        return List.of(this.store.get());
    }
    
    @Override
    public Set<String> supportedFileAttributeViews() {
        
        this.open();
        return Set.of("basic");
    }
    
    @NotNull
    @Override
    public Path getPath(@NotNull final String first, @NotNull final String... more) {
        
        this.open();
        Objects.requireNonNull(more);
        Path p = RecipePath.of(this, Objects.requireNonNull(first));
        for(final String s : more) {
            p = p.resolve(s);
        }
        return p;
    }
    
    @Override
    public PathMatcher getPathMatcher(final String syntaxAndPattern) {
        
        this.open();
        Objects.requireNonNull(syntaxAndPattern);
        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher(syntaxAndPattern);
        return path -> matcher.matches(Paths.get(path.toAbsolutePath().toString()));
    }
    
    @Override
    public UserPrincipalLookupService getUserPrincipalLookupService() {
        
        this.open();
        throw new UnsupportedOperationException();
    }
    
    @Override
    public WatchService newWatchService() {
        
        this.open();
        throw new UnsupportedOperationException();
    }
    
    SeekableByteChannel seekableByteChannel(final RecipePath path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        
        this.open();
        return FileChannel.open(path, options, attrs);
    }
    
    DirectoryStream<Path> directoryStream(final RecipePath path, final DirectoryStream.Filter<? super Path> filter) throws IOException {
        
        this.open();
        return RecipeDirectoryStream.of(this, path, this.recipes, filter);
    }
    
    void directory(final RecipePath path, final FileAttribute<?>... attributes) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(attributes);
        this.open();
        this.write();
    }
    
    void yeet(final RecipePath path) throws IOException {
        
        Objects.requireNonNull(path);
        this.open();
        this.write();
    }
    
    void copyTo(final RecipePath path, final Path destination, final CopyOption... options) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(destination);
        Objects.requireNonNull(options);
        this.open();
        if(destination.getFileSystem() != path.getFileSystem()) {
            throw new ProviderMismatchException();
        }
        this.write();
    }
    
    void moveTo(final RecipePath path, final Path destination, final CopyOption... options) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(destination);
        Objects.requireNonNull(options);
        this.open();
        this.write();
    }
    
    boolean sameFile(final RecipePath path, final Path other) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(other);
        this.open();
        if(other.getFileSystem() != path.getFileSystem()) {
            return false;
        }
        return this.resolve(path) == this.resolve((RecipePath) other);
    }
    
    @SuppressWarnings("RedundantThrows")
    boolean hidden(final RecipePath path) throws IOException {
        
        Objects.requireNonNull(path);
        this.open();
        return false;
    }
    
    @SuppressWarnings("RedundantThrows")
    FileStore fileStore(final RecipePath path) throws IOException {
        
        Objects.requireNonNull(path);
        this.open();
        return this.store.get();
    }
    
    void access(final RecipePath path, final AccessMode... modes) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(modes);
        this.open();
        this.resolve(path);
        final Set<AccessMode> modeSet = Set.of(modes);
        if(modeSet.contains(AccessMode.WRITE) || modeSet.contains(AccessMode.EXECUTE)) {
            throw new AccessDeniedException(path.toAbsolutePath().normalize().toString(), null, "Read only");
        }
    }
    
    <V extends FileAttributeView> V fileAttributeView(final RecipePath path, final Class<V> type, final LinkOption... options) {
        
        Objects.requireNonNull(options);
        this.open();
        if(type != BasicFileAttributeView.class) {
            return null;
        }
        return GenericUtil.uncheck(new RecipeFileAttributeView(path));
    }
    
    <A extends BasicFileAttributes> A attributes(final RecipePath path, final Class<A> type, final LinkOption... options) throws IOException {
        
        Objects.requireNonNull(options);
        this.open();
        if(type != BasicFileAttributes.class) {
            return null;
        }
        return GenericUtil.uncheck(new RecipeFileAttributes(this.creationTime, this.bindResolver(path)));
    }
    
    Map<String, Object> attributes(final RecipePath path, final String attributes, final LinkOption... options) throws IOException {
        
        Objects.requireNonNull(options);
        final int colon;
        final String view = (colon = attributes.indexOf(':')) != -1 ? attributes.substring(0, colon) : "basic";
        
        if(!path.fileStore().supportsFileAttributeView(view)) {
            throw new UnsupportedOperationException("View " + view + " is not available");
        }
        
        final String[] attributeList = (colon != -1 ? attributes.substring(colon + 1) : attributes).split(Pattern.quote(","));
        final int listLength;
        final boolean isWildcard = (listLength = attributeList.length) == 1 && "*".equals(attributeList[0]);
        if(listLength == 0) {
            throw new IllegalArgumentException("At least one attribute must be specified");
        }
        
        final RecipeFileAttributes attr = (RecipeFileAttributes) Files.readAttributes(path, BasicFileAttributes.class, options);
        final Map<String, Object> map = attr.asMap();
        
        if(isWildcard) {
            return map;
        }
        
        final Map<String, Object> newMap = new HashMap<>();
        for(final String attrib : attributeList) {
            if(map.containsKey(attrib)) {
                newMap.put(attrib, map.get(attrib));
            }
        }
        return newMap;
    }
    
    void attributes(final RecipePath path, final String attribute, final Object value, final LinkOption... options) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(value);
        Objects.requireNonNull(options);
        this.open();
        this.write();
    }
    
    InputStream input(final RecipePath path, final OpenOption... options) throws IOException {
        
        final int l = options.length;
        final OpenOption[] opt = new OpenOption[l + 1];
        System.arraycopy(options, 0, opt, 0, l);
        opt[l] = StandardOpenOption.READ;
        return Channels.newInputStream(Files.newByteChannel(path, opt));
    }
    
    OutputStream output(final RecipePath path, final OpenOption... options) throws IOException {
        
        final int l = options.length;
        final OpenOption[] opt = new OpenOption[l + 1];
        System.arraycopy(options, 0, opt, 0, l);
        opt[l] = StandardOpenOption.WRITE;
        return Channels.newOutputStream(Files.newByteChannel(path, opt));
    }
    
    FileChannel fileChannel(final RecipePath path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        
        this.open();
        return RecipeFileChannel.of(this.bindResolver(path), options, attrs);
    }
    
    @SuppressWarnings("unused")
    AsynchronousFileChannel asyncFileChannel(final RecipePath path, final Set<? extends OpenOption> options, final Executor executor, final FileAttribute<?>... attrs) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(options);
        Objects.requireNonNull(attrs);
        this.open();
        this.write();
        throw new UnsupportedOperationException();
    }
    
    void symLink(final RecipePath path, final Path target, final FileAttribute<?>... attrs) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(target);
        Objects.requireNonNull(attrs);
        this.open();
        this.write();
    }
    
    void link(final RecipePath path, final Path target) throws IOException {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(target);
        this.open();
        this.write();
    }
    
    boolean yeetExisting(final RecipePath path) throws IOException {
        
        Objects.requireNonNull(path);
        this.open();
        this.write();
        return false;
    }
    
    @SuppressWarnings("RedundantThrows")
    Path symLink(final RecipePath path) throws IOException {
        
        Objects.requireNonNull(path);
        this.open();
        throw new UnsupportedOperationException("Symbolic links not supported");
    }
    
    private void open() {
        
        if(this.closed) {
            throw new ClosedFileSystemException();
        }
    }
    
    @SuppressWarnings("RedundantThrows")
    private void write() throws IOException {
        
        if(this.isReadOnly()) {
            throw new ReadOnlyFileSystemException();
        }
    }
    
    private RecipeFsResolver.Bound bindResolver(final RecipePath path) {
        
        return ((RecipeFsResolver) this::resolve).bind(path);
    }
    
    private ScriptRecipe resolve(final RecipePath path) throws IOException {
        
        final String fileName = this.root.relativize(path.normalize().toAbsolutePath()).toString();
        for(final ScriptRecipe recipe : this.recipes) {
            if(fileName.equals(recipe.getFileName())) {
                return recipe;
            }
        }
        throw new FileNotFoundException(fileName);
    }
    
}
