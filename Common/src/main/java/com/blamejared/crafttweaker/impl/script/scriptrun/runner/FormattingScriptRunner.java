package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.util.PathUtil;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.formatter.FileFormatter;
import org.openzen.zenscript.formatter.ScriptFormattingSettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

final class FormattingScriptRunner extends ScriptRunner {
    
    FormattingScriptRunner(final IScriptRunInfo runInfo, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        super(runInfo, sources, logger);
    }
    
    @Override
    protected void executeRunAction(final SemanticModule module) {
        
        this.format(module);
        
        if(!this.runInfo().dumpClasses()) {
            return;
        }
        
        final Path classes = PathUtil.findFromGameDirectory("classes");
        this.engine().createRunUnit().dump(classes.toFile());
    }
    
    private void format(final SemanticModule module) {
        
        final List<HighLevelDefinition> definitions = module.definitions.getAll();
        final ScriptFormattingSettings.Builder builder = new ScriptFormattingSettings.Builder();
        final FileFormatter formatter = new FileFormatter(builder.build());
        final Path formattedScriptsDirectory = PathUtil.findFromGameDirectory("scriptsFormatted");
        
        module.scripts.forEach(block -> {
            final String format = formatter.format(module.rootPackage, block, definitions);
            final Path file = formattedScriptsDirectory.resolve(block.file.getFilename());
            final Path parent = file.getParent();
            
            if(!Files.isDirectory(parent)) {
                try {
                    Files.createDirectories(parent);
                } catch(final IOException e) {
                    this.engine().logger.error("Unable to create directory " + file.getParent() + ", aborting: " + e.getMessage());
                    return;
                }
            }
            
            try {
                Files.write(file, Arrays.asList(format.split(System.lineSeparator())));
            } catch(final IOException e) {
                this.engine().logger.error("Unable to write formatted files: " + e.getMessage());
            }
        });
    }
    
}
