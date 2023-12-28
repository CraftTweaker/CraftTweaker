package com.blamejared.crafttweaker.impl.mod;

import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.mod.PlatformMod;
import com.blamejared.crafttweaker.api.util.PathUtil;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.IntStream;

public final class FabricMod implements PlatformMod {
    private static final FileSystem DEFAULT_FILESYSTEM = FileSystems.getDefault();
    
    private final Mod mod;
    private final Path modFile;
    private final boolean requiresFabricHack;
    
    private FabricMod(final Mod mod, final Path modFile, final boolean requiresFabricHack) {
        this.mod = mod;
        this.modFile = modFile;
        this.requiresFabricHack = requiresFabricHack;
    }
    
    public static FabricMod of(final Mod mod, final Path modFile) {
        final boolean requiresFabricHack = Objects.equals(modFile.getFileSystem(), DEFAULT_FILESYSTEM);
        return new FabricMod(mod, modFile, requiresFabricHack);
    }
    
    @Override
    public Mod mod() {
        
        return this.mod;
    }
    
    @Override
    public Path modFile() {
        
        return this.modFile;
    }
    
    @Override
    public Path modRoot() {
        
        return this.modFile;
    }
    
    @Override
    public Path file(final String path) {
        
        if (!this.requiresFabricHack) {
            return PlatformMod.super.file(path);
        }
        
        final Path modRoot = this.modRoot();
        final Path target = PathUtil.makeSameFileSystemPath(modRoot, "/").resolve(path);
        
        return IntStream.range(0, target.getNameCount())
                .mapToObj(target::getName)
                .reduce(modRoot, Path::resolve);
    }
    
}
