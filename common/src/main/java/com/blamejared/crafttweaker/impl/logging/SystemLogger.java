package com.blamejared.crafttweaker.impl.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

final class SystemLogger implements Logger {
    
    private final String name;
    private final Logger mainLogger;
    private final Marker systemMarker;
    
    private SystemLogger(final String name, final Logger mainLogger, final Marker systemMarker) {
        
        this.name = name;
        this.mainLogger = mainLogger;
        this.systemMarker = systemMarker;
    }
    
    static SystemLogger of(final String name, final Logger mainLogger, final String systemName) {
        
        final Marker marker = MarkerManager.getMarker("CT_SYS_MARK_" + systemName);
        final Marker systemMarker = SystemMarker.of(systemName, marker);
        return new SystemLogger(name, mainLogger, systemMarker);
    }
    
    @Override
    public void catching(final Level level, final Throwable throwable) {
        
        this.mainLogger.catching(level, throwable);
    }
    
    @Override
    public void catching(final Throwable throwable) {
        
        this.mainLogger.catching(throwable);
    }
    
    @Override
    public void debug(final Marker marker, final Message message) {
        
        this.debug(message);
    }
    
    @Override
    public void debug(final Marker marker, final Message message, final Throwable throwable) {
        
        this.debug(message, throwable);
    }
    
    @Override
    public void debug(final Marker marker, final MessageSupplier messageSupplier) {
        
        this.debug(messageSupplier);
    }
    
    @Override
    public void debug(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.debug(messageSupplier, throwable);
    }
    
    @Override
    public void debug(final Marker marker, final CharSequence message) {
        
        this.debug(message);
    }
    
    @Override
    public void debug(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        this.debug(message, throwable);
    }
    
    @Override
    public void debug(final Marker marker, final Object message) {
        
        this.debug(message);
    }
    
    @Override
    public void debug(final Marker marker, final Object message, final Throwable throwable) {
        
        this.debug(message, throwable);
    }
    
    @Override
    public void debug(final Marker marker, final String message) {
        
        this.debug(message);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object... params) {
        
        this.debug(message, params);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        this.debug(message, paramSuppliers);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Throwable throwable) {
        
        this.debug(message, throwable);
    }
    
    @Override
    public void debug(final Marker marker, final Supplier<?> messageSupplier) {
        
        this.debug(messageSupplier);
    }
    
    @Override
    public void debug(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.debug(messageSupplier, throwable);
    }
    
    @Override
    public void debug(final Message message) {
        
        this.mainLogger.debug(this.systemMarker, message);
    }
    
    @Override
    public void debug(final Message message, final Throwable throwable) {
        
        this.mainLogger.debug(this.systemMarker, message, throwable);
    }
    
    @Override
    public void debug(final MessageSupplier messageSupplier) {
        
        this.mainLogger.debug(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void debug(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.mainLogger.debug(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void debug(final CharSequence message) {
        
        this.mainLogger.debug(this.systemMarker, message);
    }
    
    @Override
    public void debug(final CharSequence message, final Throwable throwable) {
        
        this.mainLogger.debug(this.systemMarker, message, throwable);
    }
    
    @Override
    public void debug(final Object message) {
        
        this.mainLogger.debug(this.systemMarker, message);
    }
    
    @Override
    public void debug(final Object message, final Throwable throwable) {
        
        this.mainLogger.debug(this.systemMarker, message, throwable);
    }
    
    @Override
    public void debug(final String message) {
        
        this.mainLogger.debug(this.systemMarker, message);
    }
    
    @Override
    public void debug(final String message, final Object... params) {
        
        this.mainLogger.debug(this.systemMarker, message, params);
    }
    
    @Override
    public void debug(final String message, final Supplier<?>... paramSuppliers) {
        
        this.mainLogger.debug(this.systemMarker, message, paramSuppliers);
    }
    
    @Override
    public void debug(final String message, final Throwable throwable) {
        
        this.mainLogger.debug(this.systemMarker, message, throwable);
    }
    
    @Override
    public void debug(final Supplier<?> messageSupplier) {
        
        this.mainLogger.debug(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void debug(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.mainLogger.debug(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0) {
        
        this.debug(message, p0);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1) {
        
        this.debug(message, p0, p1);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.debug(message, p0, p1, p2);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.debug(message, p0, p1, p2, p3);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.debug(message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.debug(message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.debug(message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.debug(message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void debug(final String message, final Object p0) {
        
        this.mainLogger.debug(this.systemMarker, message, p0);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.mainLogger.debug(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Deprecated
    @Override
    public void entry() {
        
        this.mainLogger.entry();
    }
    
    @Deprecated
    @Override
    public void entry(final Object... params) {
        
        this.mainLogger.entry(params);
    }
    
    @Override
    public void error(final Marker marker, final Message message) {
        
        this.error(message);
    }
    
    @Override
    public void error(final Marker marker, final Message message, final Throwable throwable) {
        
        this.error(message, throwable);
    }
    
    @Override
    public void error(final Marker marker, final MessageSupplier messageSupplier) {
        
        this.error(messageSupplier);
    }
    
    @Override
    public void error(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.error(messageSupplier, throwable);
    }
    
    @Override
    public void error(final Marker marker, final CharSequence message) {
        
        this.error(message);
    }
    
    @Override
    public void error(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        this.error(message, throwable);
    }
    
    @Override
    public void error(final Marker marker, final Object message) {
        
        this.error(message);
    }
    
    @Override
    public void error(final Marker marker, final Object message, final Throwable throwable) {
        
        this.error(message, throwable);
    }
    
    @Override
    public void error(final Marker marker, final String message) {
        
        this.error(message);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object... params) {
        
        this.error(message, params);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        this.error(message, paramSuppliers);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Throwable throwable) {
        
        this.error(message, throwable);
    }
    
    @Override
    public void error(final Marker marker, final Supplier<?> messageSupplier) {
        
        this.error(messageSupplier);
    }
    
    @Override
    public void error(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.error(messageSupplier, throwable);
    }
    
    @Override
    public void error(final Message message) {
        
        this.mainLogger.error(this.systemMarker, message);
    }
    
    @Override
    public void error(final Message message, final Throwable throwable) {
        
        this.mainLogger.error(this.systemMarker, message, throwable);
    }
    
    @Override
    public void error(final MessageSupplier messageSupplier) {
        
        this.mainLogger.error(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void error(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.mainLogger.error(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void error(final CharSequence message) {
        
        this.mainLogger.error(this.systemMarker, message);
    }
    
    @Override
    public void error(final CharSequence message, final Throwable throwable) {
        
        this.mainLogger.error(this.systemMarker, message, throwable);
    }
    
    @Override
    public void error(final Object message) {
        
        this.mainLogger.error(this.systemMarker, message);
    }
    
    @Override
    public void error(final Object message, final Throwable throwable) {
        
        this.mainLogger.error(this.systemMarker, message, throwable);
    }
    
    @Override
    public void error(final String message) {
        
        this.mainLogger.error(this.systemMarker, message);
    }
    
    @Override
    public void error(final String message, final Object... params) {
        
        this.mainLogger.error(this.systemMarker, message, params);
    }
    
    @Override
    public void error(final String message, final Supplier<?>... paramSuppliers) {
        
        this.mainLogger.error(this.systemMarker, message, paramSuppliers);
    }
    
    @Override
    public void error(final String message, final Throwable throwable) {
        
        this.mainLogger.error(this.systemMarker, message, throwable);
    }
    
    @Override
    public void error(final Supplier<?> messageSupplier) {
        
        this.mainLogger.error(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void error(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.mainLogger.error(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0) {
        
        this.error(message, p0);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1) {
        
        this.error(message, p0, p1);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.error(message, p0, p1, p2);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.error(message, p0, p1, p2, p3);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.error(message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.error(message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.error(message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.error(message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void error(final String message, final Object p0) {
        
        this.mainLogger.error(this.systemMarker, message, p0);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.mainLogger.error(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Deprecated
    @Override
    public void exit() {
        
        this.mainLogger.exit();
    }
    
    @Deprecated
    @Override
    public <R> R exit(final R result) {
        
        return this.mainLogger.exit(result);
    }
    
    @Override
    public void fatal(final Marker marker, final Message message) {
        
        this.fatal(message);
    }
    
    @Override
    public void fatal(final Marker marker, final Message message, final Throwable throwable) {
        
        this.fatal(message, throwable);
    }
    
    @Override
    public void fatal(final Marker marker, final MessageSupplier messageSupplier) {
        
        this.fatal(messageSupplier);
    }
    
    @Override
    public void fatal(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.fatal(messageSupplier, throwable);
    }
    
    @Override
    public void fatal(final Marker marker, final CharSequence message) {
        
        this.fatal(message);
    }
    
    @Override
    public void fatal(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        this.fatal(message, throwable);
    }
    
    @Override
    public void fatal(final Marker marker, final Object message) {
        
        this.fatal(message);
    }
    
    @Override
    public void fatal(final Marker marker, final Object message, final Throwable throwable) {
        
        this.fatal(message, throwable);
    }
    
    @Override
    public void fatal(final Marker marker, final String message) {
        
        this.fatal(message);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object... params) {
        
        this.fatal(message, params);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        this.fatal(message, paramSuppliers);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Throwable throwable) {
        
        this.fatal(message, throwable);
    }
    
    @Override
    public void fatal(final Marker marker, final Supplier<?> messageSupplier) {
        
        this.fatal(messageSupplier);
    }
    
    @Override
    public void fatal(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.fatal(messageSupplier, throwable);
    }
    
    @Override
    public void fatal(final Message message) {
        
        this.mainLogger.fatal(this.systemMarker, message);
    }
    
    @Override
    public void fatal(final Message message, final Throwable throwable) {
        
        this.mainLogger.fatal(this.systemMarker, message, throwable);
    }
    
    @Override
    public void fatal(final MessageSupplier messageSupplier) {
        
        this.mainLogger.fatal(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void fatal(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.mainLogger.fatal(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void fatal(final CharSequence message) {
        
        this.mainLogger.fatal(this.systemMarker, message);
    }
    
    @Override
    public void fatal(final CharSequence message, final Throwable throwable) {
        
        this.mainLogger.fatal(this.systemMarker, message, throwable);
    }
    
    @Override
    public void fatal(final Object message) {
        
        this.mainLogger.fatal(this.systemMarker, message);
    }
    
    @Override
    public void fatal(final Object message, final Throwable throwable) {
        
        this.mainLogger.fatal(this.systemMarker, message, throwable);
    }
    
    @Override
    public void fatal(final String message) {
        
        this.mainLogger.fatal(this.systemMarker, message);
    }
    
    @Override
    public void fatal(final String message, final Object... params) {
        
        this.mainLogger.fatal(this.systemMarker, message, params);
    }
    
    @Override
    public void fatal(final String message, final Supplier<?>... paramSuppliers) {
        
        this.mainLogger.fatal(this.systemMarker, message, paramSuppliers);
    }
    
    @Override
    public void fatal(final String message, final Throwable throwable) {
        
        this.mainLogger.fatal(this.systemMarker, message, throwable);
    }
    
    @Override
    public void fatal(final Supplier<?> messageSupplier) {
        
        this.mainLogger.fatal(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void fatal(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.mainLogger.fatal(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0) {
        
        this.fatal(message, p0);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1) {
        
        this.fatal(message, p0, p1);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.fatal(message, p0, p1, p2);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.fatal(message, p0, p1, p2, p3);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.fatal(message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.fatal(message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.fatal(message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void fatal(final String message, final Object p0) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.mainLogger.fatal(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public Level getLevel() {
        
        return this.mainLogger.getLevel();
    }
    
    @Override
    public <MF extends MessageFactory> MF getMessageFactory() {
        
        return this.mainLogger.getMessageFactory();
    }
    
    @Override
    public String getName() {
        
        return this.name;
    }
    
    @Override
    public void info(final Marker marker, final Message message) {
        
        this.info(message);
    }
    
    @Override
    public void info(final Marker marker, final Message message, final Throwable throwable) {
        
        this.info(message, throwable);
    }
    
    @Override
    public void info(final Marker marker, final MessageSupplier messageSupplier) {
        
        this.info(messageSupplier);
    }
    
    @Override
    public void info(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.info(messageSupplier, throwable);
    }
    
    @Override
    public void info(final Marker marker, final CharSequence message) {
        
        this.info(message);
    }
    
    @Override
    public void info(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        this.info(message, throwable);
    }
    
    @Override
    public void info(final Marker marker, final Object message) {
        
        this.info(message);
    }
    
    @Override
    public void info(final Marker marker, final Object message, final Throwable throwable) {
        
        this.info(message, throwable);
    }
    
    @Override
    public void info(final Marker marker, final String message) {
        
        this.info(message);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object... params) {
        
        this.info(message, params);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        this.info(message, paramSuppliers);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Throwable throwable) {
        
        this.info(message, throwable);
    }
    
    @Override
    public void info(final Marker marker, final Supplier<?> messageSupplier) {
        
        this.info(messageSupplier);
    }
    
    @Override
    public void info(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.info(messageSupplier, throwable);
    }
    
    @Override
    public void info(final Message message) {
        
        this.mainLogger.info(this.systemMarker, message);
    }
    
    @Override
    public void info(final Message message, final Throwable throwable) {
        
        this.mainLogger.info(this.systemMarker, message, throwable);
    }
    
    @Override
    public void info(final MessageSupplier messageSupplier) {
        
        this.mainLogger.info(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void info(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.mainLogger.info(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void info(final CharSequence message) {
        
        this.mainLogger.info(this.systemMarker, message);
    }
    
    @Override
    public void info(final CharSequence message, final Throwable throwable) {
        
        this.mainLogger.info(this.systemMarker, message, throwable);
    }
    
    @Override
    public void info(final Object message) {
        
        this.mainLogger.info(this.systemMarker, message);
    }
    
    @Override
    public void info(final Object message, final Throwable throwable) {
        
        this.mainLogger.info(this.systemMarker, message, throwable);
    }
    
    @Override
    public void info(final String message) {
        
        this.mainLogger.info(this.systemMarker, message);
    }
    
    @Override
    public void info(final String message, final Object... params) {
        
        this.mainLogger.info(this.systemMarker, message, params);
    }
    
    @Override
    public void info(final String message, final Supplier<?>... paramSuppliers) {
        
        this.mainLogger.info(this.systemMarker, message, paramSuppliers);
    }
    
    @Override
    public void info(final String message, final Throwable throwable) {
        
        this.mainLogger.info(this.systemMarker, message, throwable);
    }
    
    @Override
    public void info(final Supplier<?> messageSupplier) {
        
        this.mainLogger.info(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void info(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.mainLogger.info(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0) {
        
        this.info(message, p0);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1) {
        
        this.info(message, p0, p1);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.info(message, p0, p1, p2);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.info(message, p0, p1, p2, p3);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.info(message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.info(message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.info(message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.info(message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void info(final String message, final Object p0) {
        
        this.mainLogger.info(this.systemMarker, message, p0);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.mainLogger.info(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public boolean isDebugEnabled() {
        
        return this.mainLogger.isDebugEnabled(this.systemMarker);
    }
    
    @Override
    public boolean isDebugEnabled(final Marker marker) {
        
        return this.isDebugEnabled();
    }
    
    @Override
    public boolean isEnabled(final Level level) {
        
        return this.mainLogger.isEnabled(level, this.systemMarker);
    }
    
    @Override
    public boolean isEnabled(final Level level, final Marker marker) {
        
        return this.isEnabled(level);
    }
    
    @Override
    public boolean isErrorEnabled() {
        
        return this.mainLogger.isErrorEnabled(this.systemMarker);
    }
    
    @Override
    public boolean isErrorEnabled(final Marker marker) {
        
        return this.isErrorEnabled();
    }
    
    @Override
    public boolean isFatalEnabled() {
        
        return this.mainLogger.isFatalEnabled(this.systemMarker);
    }
    
    @Override
    public boolean isFatalEnabled(final Marker marker) {
        
        return this.isFatalEnabled();
    }
    
    @Override
    public boolean isInfoEnabled() {
        
        return this.mainLogger.isInfoEnabled(this.systemMarker);
    }
    
    @Override
    public boolean isInfoEnabled(final Marker marker) {
        
        return this.isInfoEnabled();
    }
    
    @Override
    public boolean isTraceEnabled() {
        
        return this.mainLogger.isTraceEnabled(this.systemMarker);
    }
    
    @Override
    public boolean isTraceEnabled(final Marker marker) {
        
        return this.isTraceEnabled();
    }
    
    @Override
    public boolean isWarnEnabled() {
        
        return this.mainLogger.isWarnEnabled(this.systemMarker);
    }
    
    @Override
    public boolean isWarnEnabled(final Marker marker) {
        
        return this.isWarnEnabled();
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Message message) {
        
        this.log(level, message);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Message message, final Throwable throwable) {
        
        this.log(level, message, throwable);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final MessageSupplier messageSupplier) {
        
        this.log(level, messageSupplier);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.log(level, messageSupplier, throwable);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final CharSequence message) {
        
        this.log(level, message);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final CharSequence message, final Throwable throwable) {
        
        this.log(level, message, throwable);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Object message) {
        
        this.log(level, message);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Object message, final Throwable throwable) {
        
        this.log(level, message, throwable);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message) {
        
        this.log(level, message);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object... params) {
        
        this.log(level, message, params);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        this.log(level, message, paramSuppliers);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Throwable throwable) {
        
        this.log(level, message, throwable);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Supplier<?> messageSupplier) {
        
        this.log(level, messageSupplier);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.log(level, messageSupplier, throwable);
    }
    
    @Override
    public void log(final Level level, final Message message) {
        
        this.mainLogger.log(level, this.systemMarker, message);
    }
    
    @Override
    public void log(final Level level, final Message message, final Throwable throwable) {
        
        this.mainLogger.log(level, this.systemMarker, message, throwable);
    }
    
    @Override
    public void log(final Level level, final MessageSupplier messageSupplier) {
        
        this.mainLogger.log(level, this.systemMarker, messageSupplier);
    }
    
    @Override
    public void log(final Level level, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.mainLogger.log(level, this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void log(final Level level, final CharSequence message) {
        
        this.mainLogger.log(level, this.systemMarker, message);
    }
    
    @Override
    public void log(final Level level, final CharSequence message, final Throwable throwable) {
        
        this.mainLogger.log(level, this.systemMarker, message, throwable);
    }
    
    @Override
    public void log(final Level level, final Object message) {
        
        this.mainLogger.log(level, this.systemMarker, message);
    }
    
    @Override
    public void log(final Level level, final Object message, final Throwable throwable) {
        
        this.mainLogger.log(level, this.systemMarker, message, throwable);
    }
    
    @Override
    public void log(final Level level, final String message) {
        
        this.mainLogger.log(level, this.systemMarker, message);
    }
    
    @Override
    public void log(final Level level, final String message, final Object... params) {
        
        this.mainLogger.log(level, this.systemMarker, message, params);
    }
    
    @Override
    public void log(final Level level, final String message, final Supplier<?>... paramSuppliers) {
        
        this.mainLogger.log(level, this.systemMarker, message, paramSuppliers);
    }
    
    @Override
    public void log(final Level level, final String message, final Throwable throwable) {
        
        this.mainLogger.log(level, this.systemMarker, message, throwable);
    }
    
    @Override
    public void log(final Level level, final Supplier<?> messageSupplier) {
        
        this.mainLogger.log(level, this.systemMarker, messageSupplier);
    }
    
    @Override
    public void log(final Level level, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.mainLogger.log(level, this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0) {
        
        this.log(level, message, p0);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1) {
        
        this.log(level, message, p0, p1);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.log(level, message, p0, p1, p2);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.log(level, message, p0, p1, p2, p3);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.log(level, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.log(level, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.log(level, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.mainLogger.log(level, this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void printf(final Level level, final Marker marker, final String format, final Object... params) {
        
        this.printf(level, format, params);
    }
    
    @Override
    public void printf(final Level level, final String format, final Object... params) {
        
        this.mainLogger.printf(level, this.systemMarker, format, params);
    }
    
    @Override
    public <T extends Throwable> T throwing(final Level level, final T throwable) {
        
        return this.mainLogger.throwing(level, throwable);
    }
    
    @Override
    public <T extends Throwable> T throwing(final T throwable) {
        
        return this.mainLogger.throwing(throwable);
    }
    
    @Override
    public void trace(final Marker marker, final Message message) {
        
        this.trace(message);
    }
    
    @Override
    public void trace(final Marker marker, final Message message, final Throwable throwable) {
        
        this.trace(message, throwable);
    }
    
    @Override
    public void trace(final Marker marker, final MessageSupplier messageSupplier) {
        
        this.trace(messageSupplier);
    }
    
    @Override
    public void trace(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.trace(messageSupplier, throwable);
    }
    
    @Override
    public void trace(final Marker marker, final CharSequence message) {
        
        this.trace(message);
    }
    
    @Override
    public void trace(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        this.trace(message, throwable);
    }
    
    @Override
    public void trace(final Marker marker, final Object message) {
        
        this.trace(message);
    }
    
    @Override
    public void trace(final Marker marker, final Object message, final Throwable throwable) {
        
        this.trace(message, throwable);
    }
    
    @Override
    public void trace(final Marker marker, final String message) {
        
        this.trace(message);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object... params) {
        
        this.trace(message, params);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        this.trace(message, paramSuppliers);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Throwable throwable) {
        
        this.trace(message, throwable);
    }
    
    @Override
    public void trace(final Marker marker, final Supplier<?> messageSupplier) {
        
        this.trace(messageSupplier);
    }
    
    @Override
    public void trace(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.trace(messageSupplier, throwable);
    }
    
    @Override
    public void trace(final Message message) {
        
        this.mainLogger.trace(this.systemMarker, message);
    }
    
    @Override
    public void trace(final Message message, final Throwable throwable) {
        
        this.mainLogger.trace(this.systemMarker, message, throwable);
    }
    
    @Override
    public void trace(final MessageSupplier messageSupplier) {
        
        this.mainLogger.trace(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void trace(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.mainLogger.trace(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void trace(final CharSequence message) {
        
        this.mainLogger.trace(this.systemMarker, message);
    }
    
    @Override
    public void trace(final CharSequence message, final Throwable throwable) {
        
        this.mainLogger.trace(this.systemMarker, message, throwable);
    }
    
    @Override
    public void trace(final Object message) {
        
        this.mainLogger.trace(this.systemMarker, message);
    }
    
    @Override
    public void trace(final Object message, final Throwable throwable) {
        
        this.mainLogger.trace(this.systemMarker, message, throwable);
    }
    
    @Override
    public void trace(final String message) {
        
        this.mainLogger.trace(this.systemMarker, message);
    }
    
    @Override
    public void trace(final String message, final Object... params) {
        
        this.mainLogger.trace(this.systemMarker, message, params);
    }
    
    @Override
    public void trace(final String message, final Supplier<?>... paramSuppliers) {
        
        this.mainLogger.trace(this.systemMarker, message, paramSuppliers);
    }
    
    @Override
    public void trace(final String message, final Throwable throwable) {
        
        this.mainLogger.trace(this.systemMarker, message, throwable);
    }
    
    @Override
    public void trace(final Supplier<?> messageSupplier) {
        
        this.mainLogger.trace(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void trace(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.mainLogger.trace(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0) {
        
        this.trace(message, p0);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1) {
        
        this.trace(message, p0, p1);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.trace(message, p0, p1, p2);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.trace(message, p0, p1, p2, p3);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.trace(message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.trace(message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.trace(message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.trace(message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void trace(final String message, final Object p0) {
        
        this.mainLogger.trace(this.systemMarker, message, p0);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.mainLogger.trace(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public EntryMessage traceEntry() {
        
        return this.mainLogger.traceEntry();
    }
    
    @Override
    public EntryMessage traceEntry(final String format, final Object... params) {
        
        return this.mainLogger.traceEntry(format, params);
    }
    
    @Override
    public EntryMessage traceEntry(final Supplier<?>... paramSuppliers) {
        
        return this.mainLogger.traceEntry(paramSuppliers);
    }
    
    @Override
    public EntryMessage traceEntry(final String format, final Supplier<?>... paramSuppliers) {
        
        return this.mainLogger.traceEntry(format, paramSuppliers);
    }
    
    @Override
    public EntryMessage traceEntry(final Message message) {
        
        return this.mainLogger.traceEntry(message);
    }
    
    @Override
    public void traceExit() {
        
        this.mainLogger.traceExit();
    }
    
    @Override
    public <R> R traceExit(final R result) {
        
        return this.mainLogger.traceExit(result);
    }
    
    @Override
    public <R> R traceExit(final String format, final R result) {
        
        return this.mainLogger.traceExit(format, result);
    }
    
    @Override
    public void traceExit(final EntryMessage message) {
        
        this.mainLogger.traceExit(message);
    }
    
    @Override
    public <R> R traceExit(final EntryMessage message, final R result) {
        
        return this.mainLogger.traceExit(message, result);
    }
    
    @Override
    public <R> R traceExit(final Message message, final R result) {
        
        return this.mainLogger.traceExit(message, result);
    }
    
    @Override
    public void warn(final Marker marker, final Message message) {
        
        this.warn(message);
    }
    
    @Override
    public void warn(final Marker marker, final Message message, final Throwable throwable) {
        
        this.warn(message, throwable);
    }
    
    @Override
    public void warn(final Marker marker, final MessageSupplier messageSupplier) {
        
        this.warn(messageSupplier);
    }
    
    @Override
    public void warn(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.warn(messageSupplier, throwable);
    }
    
    @Override
    public void warn(final Marker marker, final CharSequence message) {
        
        this.warn(message);
    }
    
    @Override
    public void warn(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        this.warn(message, throwable);
    }
    
    @Override
    public void warn(final Marker marker, final Object message) {
        
        this.warn(message);
    }
    
    @Override
    public void warn(final Marker marker, final Object message, final Throwable throwable) {
        
        this.warn(message, throwable);
    }
    
    @Override
    public void warn(final Marker marker, final String message) {
        
        this.warn(message);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object... params) {
        
        this.warn(message, params);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        this.warn(message, paramSuppliers);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Throwable throwable) {
        
        this.warn(message, throwable);
    }
    
    @Override
    public void warn(final Marker marker, final Supplier<?> messageSupplier) {
        
        this.warn(messageSupplier);
    }
    
    @Override
    public void warn(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.warn(messageSupplier, throwable);
    }
    
    @Override
    public void warn(final Message message) {
        
        this.mainLogger.warn(this.systemMarker, message);
    }
    
    @Override
    public void warn(final Message message, final Throwable throwable) {
        
        this.mainLogger.warn(this.systemMarker, message, throwable);
    }
    
    @Override
    public void warn(final MessageSupplier messageSupplier) {
        
        this.mainLogger.warn(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void warn(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        this.mainLogger.warn(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void warn(final CharSequence message) {
        
        this.mainLogger.warn(this.systemMarker, message);
    }
    
    @Override
    public void warn(final CharSequence message, final Throwable throwable) {
        
        this.mainLogger.warn(this.systemMarker, message, throwable);
    }
    
    @Override
    public void warn(final Object message) {
        
        this.mainLogger.warn(this.systemMarker, message);
    }
    
    @Override
    public void warn(final Object message, final Throwable throwable) {
        
        this.mainLogger.warn(this.systemMarker, message, throwable);
    }
    
    @Override
    public void warn(final String message) {
        
        this.mainLogger.warn(this.systemMarker, message);
    }
    
    @Override
    public void warn(final String message, final Object... params) {
        
        this.mainLogger.warn(this.systemMarker, message, params);
    }
    
    @Override
    public void warn(final String message, final Supplier<?>... paramSuppliers) {
        
        this.mainLogger.warn(this.systemMarker, message, paramSuppliers);
    }
    
    @Override
    public void warn(final String message, final Throwable throwable) {
        
        this.mainLogger.warn(this.systemMarker, message, throwable);
    }
    
    @Override
    public void warn(final Supplier<?> messageSupplier) {
        
        this.mainLogger.warn(this.systemMarker, messageSupplier);
    }
    
    @Override
    public void warn(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        this.mainLogger.warn(this.systemMarker, messageSupplier, throwable);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0) {
        
        this.warn(message, p0);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1) {
        
        this.warn(message, p0, p1);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        this.warn(message, p0, p1, p2);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.warn(message, p0, p1, p2, p3);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.warn(message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.warn(message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.warn(message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.warn(message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void warn(final String message, final Object p0) {
        
        this.mainLogger.warn(this.systemMarker, message, p0);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        this.mainLogger.warn(this.systemMarker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
    
    @Override
    public void logMessage(final Level level, final Marker marker, final String fqcn, final StackTraceElement location, final Message message, final Throwable throwable) {
        
        this.mainLogger.logMessage(level, this.systemMarker, fqcn, location, message, throwable);
    }
    
    @Override
    public LogBuilder atTrace() {
        
        return this.mainLogger.atTrace();
    }
    
    @Override
    public LogBuilder atDebug() {
        
        return this.mainLogger.atDebug();
    }
    
    @Override
    public LogBuilder atInfo() {
        
        return this.mainLogger.atInfo();
    }
    
    @Override
    public LogBuilder atWarn() {
        
        return this.mainLogger.atWarn();
    }
    
    @Override
    public LogBuilder atError() {
        
        return this.mainLogger.atError();
    }
    
    @Override
    public LogBuilder atFatal() {
        
        return this.mainLogger.atFatal();
    }
    
    @Override
    public LogBuilder always() {
        
        return this.mainLogger.always();
    }
    
    @Override
    public LogBuilder atLevel(final Level level) {
        
        return this.mainLogger.atLevel(level);
    }
    
}
