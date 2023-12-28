package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public final class RecipeFileSystemProvider extends FileSystemProvider {
    
    public static final String SCHEME = "crt-recipe-fs";
    private static final Object LOCK = new Object();
    public static final String FILE_SYSTEM_NAME = "recipes";
    
    private RecipeFileSystem fileSystem;
    
    public RecipeFileSystemProvider() {
        
        this.fileSystem = null;
    }
    
    @Override
    public String getScheme() {
        
        return SCHEME;
    }
    
    @Override
    public FileSystem newFileSystem(final URI uri, final Map<String, ?> env) {
        
        Objects.requireNonNull(env);
        this.checkUri(uri, false);
        final Collection<ScriptRecipe> recipes = GenericUtil.uncheck(env.get("recipes"));
        if(recipes == null) {
            throw new IllegalArgumentException("Missing \"recipes\" environment data");
        }
        
        synchronized(LOCK) {
            if(this.fileSystem != null) {
                throw new FileSystemAlreadyExistsException();
            }
            
            return this.fileSystem = new RecipeFileSystem(this, recipes);
        }
    }
    
    
    @Override
    public FileSystem newFileSystem(final Path path, final Map<String, ?> env) {
        
        Objects.requireNonNull(path);
        Objects.requireNonNull(env);
        throw new UnsupportedOperationException("Unable to access " + path + " as RecipeFS");
    }
    
    @Override
    public FileSystem getFileSystem(final URI uri) {
        
        this.checkUri(uri, false);
        return this.getFileSystemFrom(uri);
    }
    
    @NotNull
    @Override
    public Path getPath(@NotNull final URI uri) {
        
        this.checkUri(uri, true);
        final String path = uri.getRawSchemeSpecificPart();
        if(path.isEmpty()) {
            throw new IllegalArgumentException("URI " + uri + " does not contain path");
        }
        return this.getFileSystemFrom(uri).getPath(path);
    }
    
    @Override
    public SeekableByteChannel newByteChannel(final Path path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        
        Objects.requireNonNull(options);
        Objects.requireNonNull(attrs);
        return this.path(path).seekableByteChannel(options, attrs);
    }
    
    @Override
    public DirectoryStream<Path> newDirectoryStream(final Path dir, final DirectoryStream.Filter<? super Path> filter) throws IOException {
        
        Objects.requireNonNull(filter);
        return this.path(dir).directoryStream(filter);
    }
    
    @Override
    public void createDirectory(final Path dir, final FileAttribute<?>... attrs) throws IOException {
        
        Objects.requireNonNull(attrs);
        this.path(dir).directory(attrs);
    }
    
    @Override
    public void delete(final Path path) throws IOException {
        
        this.path(path).yeet();
    }
    
    @Override
    public void copy(final Path source, final Path target, final CopyOption... options) throws IOException {
        
        Objects.requireNonNull(target);
        Objects.requireNonNull(options);
        this.path(source).copyTo(target, options);
    }
    
    @Override
    public void move(final Path source, final Path target, final CopyOption... options) throws IOException {
        
        Objects.requireNonNull(target);
        Objects.requireNonNull(options);
        this.path(source).moveTo(target, options);
    }
    
    @Override
    public boolean isSameFile(final Path path, final Path path2) throws IOException {
        
        Objects.requireNonNull(path2);
        return this.path(path).sameFile(path2);
    }
    
    @Override
    public boolean isHidden(final Path path) throws IOException {
        
        return this.path(path).hidden();
    }
    
    @Override
    public FileStore getFileStore(final Path path) throws IOException {
        
        return this.path(path).fileStore();
    }
    
    @Override
    public void checkAccess(final Path path, final AccessMode... modes) throws IOException {
        
        Objects.requireNonNull(modes);
        this.path(path).access(modes);
    }
    
    @Override
    public <V extends FileAttributeView> V getFileAttributeView(final Path path, final Class<V> type, final LinkOption... options) {
        
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);
        return this.path(path).fileAttributeView(type, options);
    }
    
    @Override
    public <A extends BasicFileAttributes> A readAttributes(final Path path, final Class<A> type, final LinkOption... options) throws IOException {
        
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);
        return this.path(path).attributes(type, options);
    }
    
    @Override
    public Map<String, Object> readAttributes(final Path path, final String attributes, final LinkOption... options) throws IOException {
        
        Objects.requireNonNull(attributes);
        Objects.requireNonNull(options);
        return this.path(path).attributes(attributes, options);
    }
    
    @Override
    public void setAttribute(final Path path, final String attribute, final Object value, final LinkOption... options) throws IOException {
        
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(value);
        Objects.requireNonNull(options);
        this.path(path).attributes(attribute, value, options);
    }
    
    @Override
    public InputStream newInputStream(final Path path, final OpenOption... options) throws IOException {
        
        Objects.requireNonNull(options);
        return this.path(path).input(options);
    }
    
    @Override
    public OutputStream newOutputStream(final Path path, final OpenOption... options) throws IOException {
        
        Objects.requireNonNull(options);
        return this.path(path).output(options);
    }
    
    @Override
    public FileChannel newFileChannel(final Path path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        
        Objects.requireNonNull(options);
        Objects.requireNonNull(attrs);
        return this.path(path).fileChannel(options, attrs);
    }
    
    @Override
    public AsynchronousFileChannel newAsynchronousFileChannel(final Path path, final Set<? extends OpenOption> options, final ExecutorService executor, final FileAttribute<?>... attrs) throws IOException {
        
        Objects.requireNonNull(options);
        Objects.requireNonNull(attrs);
        return this.path(path).asyncFileChannel(options, executor, attrs);
    }
    
    @Override
    public void createSymbolicLink(final Path link, final Path target, final FileAttribute<?>... attrs) throws IOException {
        
        Objects.requireNonNull(target);
        Objects.requireNonNull(attrs);
        this.path(link).symLink(target, attrs);
    }
    
    @Override
    public void createLink(final Path link, final Path existing) throws IOException {
        
        Objects.requireNonNull(existing);
        this.path(link).link(existing);
    }
    
    @Override
    public boolean deleteIfExists(final Path path) throws IOException {
        
        return this.path(path).yeetExisting();
    }
    
    @Override
    public Path readSymbolicLink(final Path link) throws IOException {
        
        return this.path(link).symLink();
    }
    
    void closing() {
        
        synchronized(LOCK) {
            this.fileSystem = null;
        }
    }
    
    private void checkUri(final URI uri, final boolean path) {
        
        if(!SCHEME.equals(uri.getScheme())) { // Implicit null check
            throw new IllegalArgumentException("Unable to create a file system with a non-" + this.getScheme() + " URI");
        }
        if(path) {
            return;
        }
        if(!FILE_SYSTEM_NAME.equals(uri.getRawSchemeSpecificPart())) {
            throw new IllegalArgumentException("Unable to create a file system with URI " + uri);
        }
    }
    
    private FileSystem getFileSystemFrom(final URI uri) {
        
        synchronized(LOCK) {
            return Objects.requireNonNull(this.fileSystem, () -> "No file system created with URI " + uri);
        }
    }
    
    private RecipePath path(final Path path) {
        
        if(!(Objects.requireNonNull(path) instanceof RecipePath recipePath)) {
            throw new ProviderMismatchException("Mismatch with path " + path);
        }
        return recipePath;
    }
    
}
