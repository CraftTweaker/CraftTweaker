package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

import java.util.Collection;

public record LoaderData(String loader, Collection<IScriptLoader> inheritedLoaders) implements IScriptLoader {}
