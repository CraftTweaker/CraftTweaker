package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.logger.*;
import com.google.common.collect.*;
import org.openzen.zencode.java.*;

import java.io.*;
import java.util.*;

@ZenRegister
public class CraftTweakerAPI {
    
    public static final File SCRIPT_DIR = new File("scripts");
    
    @ZenCodeGlobals.Global
    public static ILogger logger;
    
    private static final List<IAction> ACTION_LIST = new ArrayList<>();
    private static final List<IAction> ACTION_LIST_INVALID = new ArrayList<>();
    
    private static boolean firstRun = true;
    
    public static void endFirstRun() {
        firstRun = false;
    }
    
    public static boolean isFirstRun() {
        return firstRun;
    }
    
    public static void apply(IAction action) {
        if(!(action instanceof IRuntimeAction)) {
            if(!isFirstRun()) {
                return;
            }
        }
        try {
            if(action.validate(logger)) {
                String describe = action.describe();
                if(describe != null && !describe.isEmpty()) {
                    logInfo(describe);
                }
                action.apply();
                ACTION_LIST.add(action);
            } else {
                ACTION_LIST_INVALID.add(action);
            }
        } catch(Exception e) {
            logThrowing("Error running action", e);
        }
    }
    
    public static void reload() {
        ACTION_LIST.stream().filter(iAction -> iAction instanceof IUndoableAction).map(iAction -> (IUndoableAction) iAction).forEach(iUndoableAction -> {
            CraftTweakerAPI.logInfo(iUndoableAction.describeUndo());
            iUndoableAction.undo();
        });
        
        ACTION_LIST.clear();
        ACTION_LIST_INVALID.clear();
    }
    
    public static void setupLoggers() {
        logger = new GroupLogger();
        ((GroupLogger) logger).addLogger(new FileLogger(new File("logs/crafttweaker.log")));
        //TODO maybe post an event to collect a bunch of loggers? not sure if it will be used much
    }
    
    public static void logInfo(String message, Object... formats) {
        logger.info(String.format(message, formats));
    }
    
    public static void logDebug(String message, Object... formats) {
        logger.debug(String.format(message, formats));
    }
    
    public static void logWarning(String message, Object... formats) {
        logger.warning(String.format(message, formats));
    }
    
    public static void logError(String message, Object... formats) {
        logger.error(String.format(message, formats));
    }
    
    public static void logThrowing(String message, Throwable e, Object... formats) {
        logger.throwing(String.format(message, formats), e);
    }
    
    
    public static List<IAction> getActionList() {
        return ImmutableList.copyOf(ACTION_LIST);
    }
    
    public static List<IAction> getActionListInvalid() {
        return ImmutableList.copyOf(ACTION_LIST_INVALID);
    }
}
