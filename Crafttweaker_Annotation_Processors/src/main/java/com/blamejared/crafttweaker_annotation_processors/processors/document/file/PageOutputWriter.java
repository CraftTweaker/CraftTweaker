package com.blamejared.crafttweaker_annotation_processors.processors.document.file;

import java.io.Closeable;
import java.io.PrintWriter;
import java.util.Objects;

public class PageOutputWriter implements AutoCloseable, Closeable {
    private final PrintWriter backend;
    
    PageOutputWriter(final PrintWriter backend) {
        this.backend = backend;
    }
    
    public void printf(final String format, final Object... arguments) {
        this.backend.printf(format, arguments);
    }
    
    public void println() {
        this.backend.println();
    }
    
    public void println(final String line) {
        this.backend.println(line);
    }
    
    public void print(final String line) {
        this.backend.print(line);
    }
    
    public void zenBlock(final Runnable blockContents) {
        this.println("```zenscript");
        blockContents.run();
        this.println("```");
    }
    
    public void group(final String name, final String sinceVersion, final Runnable groupContents) {
        Objects.requireNonNull(name);
        if (sinceVersion != null) {
            this.printf(":::group{name=%s since=\"%s\"}%n%n", name, sinceVersion);
        } else {
            this.printf(":::group{name=%s}%n%n", name);
        }
        Objects.requireNonNull(groupContents).run();
        this.printf(":::%n%n");
    }
    
    public void pageSince(final String sinceVersion) {
        if (sinceVersion == null) return;
        this.printf("::since{version=%s}%n", sinceVersion);
    }
    
    public void deprecationMessage(final String deprecationMessage) {
        if (deprecationMessage == null) return;
        this.printf("::deprecated[%s]%n%n", deprecationMessage);
    }
    
    public void modSupportBuiltin(final String mod, final String modLink) {
        this.printf("::requiredMod[%s]{builtIn=true modLink=%s}", mod, this.fromSlugToLinkIfNeeded(modLink));
    }
    
    public void modSupportWithAddon(final String mod, final String modLink, final String requiredMod, final String requiredModLink) {
        this.printf("::requiredMod[%s]{builtIn=false modLink=%s requiredMod=%s requiredModLink=%s}", mod,
                this.fromSlugToLinkIfNeeded(modLink), requiredMod, this.fromSlugToLinkIfNeeded(requiredModLink));
    }
    
    private String fromSlugToLinkIfNeeded(final String slug) {
        return slug.startsWith("https://")? slug : "https://www.curseforge.com/minecraft/mc-mods/" + slug;
    }

    @Override
    public void close() {
        this.backend.close();
    }
    
}
