package com.blamejared.crafttweaker.impl.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.EntryMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

import java.util.Arrays;

final class ForwardingLogger implements Logger {
    
    private final String name;
    private final Logger[] loggers;
    
    private ForwardingLogger(final String name, final Logger[] loggers) {
        
        this.name = name;
        this.loggers = loggers;
    }
    
    static Logger of(@SuppressWarnings("SameParameterValue") final String name, final Logger... delegates) {
        
        if(delegates.length == 0) {
            throw new IllegalArgumentException("Not enough delegates");
        }
        if(delegates.length == 1) {
            return delegates[0];
        }
        return new ForwardingLogger(name, Arrays.copyOf(delegates, delegates.length));
    }
    
    @Override
    public void catching(final Level level, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.catching(level, throwable);
        }
    }
    
    @Override
    public void catching(final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.catching(throwable);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, throwable);
        }
    }
    
    @Override
    public void debug(final Marker marker, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, messageSupplier);
        }
    }
    
    @Override
    public void debug(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void debug(final Marker marker, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message);
        }
    }
    
    @Override
    public void debug(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, throwable);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, throwable);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, params);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, paramSuppliers);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, throwable);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, messageSupplier);
        }
    }
    
    @Override
    public void debug(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void debug(final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message);
        }
    }
    
    @Override
    public void debug(final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, throwable);
        }
    }
    
    @Override
    public void debug(final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(messageSupplier);
        }
    }
    
    @Override
    public void debug(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(messageSupplier, throwable);
        }
    }
    
    @Override
    public void debug(final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message);
        }
    }
    
    @Override
    public void debug(final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, throwable);
        }
    }
    
    @Override
    public void debug(final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message);
        }
    }
    
    @Override
    public void debug(final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, throwable);
        }
    }
    
    @Override
    public void debug(final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message);
        }
    }
    
    @Override
    public void debug(final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, params);
        }
    }
    
    @Override
    public void debug(final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, paramSuppliers);
        }
    }
    
    @Override
    public void debug(final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, throwable);
        }
    }
    
    @Override
    public void debug(final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(messageSupplier);
        }
    }
    
    @Override
    public void debug(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(messageSupplier, throwable);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void debug(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.debug(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Deprecated
    @Override
    public void entry() {
        
        for(final Logger logger : this.loggers) {
            logger.entry();
        }
    }
    
    @Deprecated
    @Override
    public void entry(final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.entry(params);
        }
    }
    
    @Override
    public void error(final Marker marker, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message);
        }
    }
    
    @Override
    public void error(final Marker marker, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, throwable);
        }
    }
    
    @Override
    public void error(final Marker marker, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, messageSupplier);
        }
    }
    
    @Override
    public void error(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void error(final Marker marker, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message);
        }
    }
    
    @Override
    public void error(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, throwable);
        }
    }
    
    @Override
    public void error(final Marker marker, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message);
        }
    }
    
    @Override
    public void error(final Marker marker, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, throwable);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, params);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, paramSuppliers);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, throwable);
        }
    }
    
    @Override
    public void error(final Marker marker, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, messageSupplier);
        }
    }
    
    @Override
    public void error(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void error(final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message);
        }
    }
    
    @Override
    public void error(final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, throwable);
        }
    }
    
    @Override
    public void error(final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.error(messageSupplier);
        }
    }
    
    @Override
    public void error(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(messageSupplier, throwable);
        }
    }
    
    @Override
    public void error(final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message);
        }
    }
    
    @Override
    public void error(final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, throwable);
        }
    }
    
    @Override
    public void error(final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message);
        }
    }
    
    @Override
    public void error(final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, throwable);
        }
    }
    
    @Override
    public void error(final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message);
        }
    }
    
    @Override
    public void error(final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, params);
        }
    }
    
    @Override
    public void error(final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, paramSuppliers);
        }
    }
    
    @Override
    public void error(final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, throwable);
        }
    }
    
    @Override
    public void error(final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.error(messageSupplier);
        }
    }
    
    @Override
    public void error(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.error(messageSupplier, throwable);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void error(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void error(final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void error(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.error(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Deprecated
    @Override
    public void exit() {
        
        for(final Logger logger : this.loggers) {
            logger.exit();
        }
    }
    
    @Deprecated
    @Override
    public <R> R exit(final R result) {
        
        for(final Logger logger : this.loggers) {
            logger.exit(result);
        }
        return result;
    }
    
    @Override
    public void fatal(final Marker marker, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, throwable);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, messageSupplier);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, throwable);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, throwable);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, params);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, paramSuppliers);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, throwable);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, messageSupplier);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void fatal(final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message);
        }
    }
    
    @Override
    public void fatal(final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, throwable);
        }
    }
    
    @Override
    public void fatal(final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(messageSupplier);
        }
    }
    
    @Override
    public void fatal(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(messageSupplier, throwable);
        }
    }
    
    @Override
    public void fatal(final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message);
        }
    }
    
    @Override
    public void fatal(final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, throwable);
        }
    }
    
    @Override
    public void fatal(final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message);
        }
    }
    
    @Override
    public void fatal(final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, throwable);
        }
    }
    
    @Override
    public void fatal(final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message);
        }
    }
    
    @Override
    public void fatal(final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, params);
        }
    }
    
    @Override
    public void fatal(final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, paramSuppliers);
        }
    }
    
    @Override
    public void fatal(final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, throwable);
        }
    }
    
    @Override
    public void fatal(final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(messageSupplier);
        }
    }
    
    @Override
    public void fatal(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(messageSupplier, throwable);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void fatal(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void fatal(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.fatal(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public Level getLevel() {
        
        Level maxLevel = Level.OFF;
        for(final Logger logger : this.loggers) {
            final Level level = logger.getLevel();
            if(level.intLevel() > maxLevel.intLevel()) {
                maxLevel = level;
            }
        }
        return maxLevel;
    }
    
    @Override
    public <MF extends MessageFactory> MF getMessageFactory() {
        
        return this.loggers[0].getMessageFactory();
    }
    
    @Override
    public String getName() {
        
        return this.name;
    }
    
    @Override
    public void info(final Marker marker, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message);
        }
    }
    
    @Override
    public void info(final Marker marker, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, throwable);
        }
    }
    
    @Override
    public void info(final Marker marker, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, messageSupplier);
        }
    }
    
    @Override
    public void info(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void info(final Marker marker, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message);
        }
    }
    
    @Override
    public void info(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, throwable);
        }
    }
    
    @Override
    public void info(final Marker marker, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message);
        }
    }
    
    @Override
    public void info(final Marker marker, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, throwable);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, params);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, paramSuppliers);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, throwable);
        }
    }
    
    @Override
    public void info(final Marker marker, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, messageSupplier);
        }
    }
    
    @Override
    public void info(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void info(final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message);
        }
    }
    
    @Override
    public void info(final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, throwable);
        }
    }
    
    @Override
    public void info(final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.info(messageSupplier);
        }
    }
    
    @Override
    public void info(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(messageSupplier, throwable);
        }
    }
    
    @Override
    public void info(final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message);
        }
    }
    
    @Override
    public void info(final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, throwable);
        }
    }
    
    @Override
    public void info(final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message);
        }
    }
    
    @Override
    public void info(final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, throwable);
        }
    }
    
    @Override
    public void info(final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message);
        }
    }
    
    @Override
    public void info(final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, params);
        }
    }
    
    @Override
    public void info(final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, paramSuppliers);
        }
    }
    
    @Override
    public void info(final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, throwable);
        }
    }
    
    @Override
    public void info(final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.info(messageSupplier);
        }
    }
    
    @Override
    public void info(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.info(messageSupplier, throwable);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void info(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void info(final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void info(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.info(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public boolean isDebugEnabled() {
        
        for(final Logger logger : this.loggers) {
            if(logger.isDebugEnabled()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isDebugEnabled(final Marker marker) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isDebugEnabled(marker)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isEnabled(final Level level) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isEnabled(level)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isEnabled(final Level level, final Marker marker) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isEnabled(level, marker)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isErrorEnabled() {
        
        for(final Logger logger : this.loggers) {
            if(logger.isErrorEnabled()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isErrorEnabled(final Marker marker) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isErrorEnabled(marker)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isFatalEnabled() {
        
        for(final Logger logger : this.loggers) {
            if(logger.isFatalEnabled()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isFatalEnabled(final Marker marker) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isFatalEnabled(marker)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isInfoEnabled() {
        
        for(final Logger logger : this.loggers) {
            if(logger.isInfoEnabled()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isInfoEnabled(final Marker marker) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isInfoEnabled(marker)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isTraceEnabled() {
        
        for(final Logger logger : this.loggers) {
            if(logger.isTraceEnabled()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isTraceEnabled(final Marker marker) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isTraceEnabled(marker)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isWarnEnabled() {
        
        for(final Logger logger : this.loggers) {
            if(logger.isWarnEnabled()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isWarnEnabled(final Marker marker) {
        
        for(final Logger logger : this.loggers) {
            if(logger.isWarnEnabled(marker)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, messageSupplier);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, params);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, paramSuppliers);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, messageSupplier);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message);
        }
    }
    
    @Override
    public void log(final Level level, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, messageSupplier);
        }
    }
    
    @Override
    public void log(final Level level, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, messageSupplier, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message);
        }
    }
    
    @Override
    public void log(final Level level, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message);
        }
    }
    
    @Override
    public void log(final Level level, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, params);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, paramSuppliers);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, messageSupplier);
        }
    }
    
    @Override
    public void log(final Level level, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, messageSupplier, throwable);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void log(final Level level, final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void log(final Level level, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.log(level, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void printf(final Level level, final Marker marker, final String format, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.printf(level, marker, format, params);
        }
    }
    
    @Override
    public void printf(final Level level, final String format, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.printf(level, format, params);
        }
    }
    
    @Override
    public <T extends Throwable> T throwing(final Level level, final T throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.throwing(level, throwable);
        }
        return throwable;
    }
    
    @Override
    public <T extends Throwable> T throwing(final T throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.throwing(throwable);
        }
        return throwable;
    }
    
    @Override
    public void trace(final Marker marker, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, throwable);
        }
    }
    
    @Override
    public void trace(final Marker marker, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, messageSupplier);
        }
    }
    
    @Override
    public void trace(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void trace(final Marker marker, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message);
        }
    }
    
    @Override
    public void trace(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, throwable);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, throwable);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, params);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, paramSuppliers);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, throwable);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, messageSupplier);
        }
    }
    
    @Override
    public void trace(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void trace(final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message);
        }
    }
    
    @Override
    public void trace(final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, throwable);
        }
    }
    
    @Override
    public void trace(final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(messageSupplier);
        }
    }
    
    @Override
    public void trace(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(messageSupplier, throwable);
        }
    }
    
    @Override
    public void trace(final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message);
        }
    }
    
    @Override
    public void trace(final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, throwable);
        }
    }
    
    @Override
    public void trace(final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message);
        }
    }
    
    @Override
    public void trace(final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, throwable);
        }
    }
    
    @Override
    public void trace(final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message);
        }
    }
    
    @Override
    public void trace(final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, params);
        }
    }
    
    @Override
    public void trace(final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, paramSuppliers);
        }
    }
    
    @Override
    public void trace(final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, throwable);
        }
    }
    
    @Override
    public void trace(final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(messageSupplier);
        }
    }
    
    @Override
    public void trace(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(messageSupplier, throwable);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void trace(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.trace(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public EntryMessage traceEntry() {
        
        EntryMessage message = null;
        for(final Logger logger : this.loggers) {
            final EntryMessage m = logger.traceEntry();
            if(message == null) {
                message = m;
            }
        }
        return message;
    }
    
    @Override
    public EntryMessage traceEntry(final String format, final Object... params) {
        
        EntryMessage message = null;
        for(final Logger logger : this.loggers) {
            final EntryMessage m = logger.traceEntry(format, params);
            if(message == null) {
                message = m;
            }
        }
        return message;
    }
    
    @Override
    public EntryMessage traceEntry(final Supplier<?>... paramSuppliers) {
        
        EntryMessage message = null;
        for(final Logger logger : this.loggers) {
            final EntryMessage m = logger.traceEntry(paramSuppliers);
            if(message == null) {
                message = m;
            }
        }
        return message;
    }
    
    @Override
    public EntryMessage traceEntry(final String format, final Supplier<?>... paramSuppliers) {
        
        EntryMessage message = null;
        for(final Logger logger : this.loggers) {
            final EntryMessage m = logger.traceEntry(format, paramSuppliers);
            if(message == null) {
                message = m;
            }
        }
        return message;
    }
    
    @Override
    public EntryMessage traceEntry(final Message message) {
        
        EntryMessage msg = null;
        for(final Logger logger : this.loggers) {
            final EntryMessage m = logger.traceEntry(message);
            if(msg == null) {
                msg = m;
            }
        }
        return msg;
    }
    
    @Override
    public void traceExit() {
        
        for(final Logger logger : this.loggers) {
            logger.traceExit();
        }
    }
    
    @Override
    public <R> R traceExit(final R result) {
        
        for(final Logger logger : this.loggers) {
            logger.traceExit(result);
        }
        return result;
    }
    
    @Override
    public <R> R traceExit(final String format, final R result) {
        
        for(final Logger logger : this.loggers) {
            logger.traceExit(format, result);
        }
        return result;
    }
    
    @Override
    public void traceExit(final EntryMessage message) {
        
        for(final Logger logger : this.loggers) {
            logger.traceExit(message);
        }
    }
    
    @Override
    public <R> R traceExit(final EntryMessage message, final R result) {
        
        for(final Logger logger : this.loggers) {
            logger.traceExit(message, result);
        }
        return result;
    }
    
    @Override
    public <R> R traceExit(final Message message, final R result) {
        
        for(final Logger logger : this.loggers) {
            logger.traceExit(message, result);
        }
        return result;
    }
    
    @Override
    public void warn(final Marker marker, final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, throwable);
        }
    }
    
    @Override
    public void warn(final Marker marker, final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, messageSupplier);
        }
    }
    
    @Override
    public void warn(final Marker marker, final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void warn(final Marker marker, final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message);
        }
    }
    
    @Override
    public void warn(final Marker marker, final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, throwable);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, throwable);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, params);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, paramSuppliers);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, throwable);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, messageSupplier);
        }
    }
    
    @Override
    public void warn(final Marker marker, final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, messageSupplier, throwable);
        }
    }
    
    @Override
    public void warn(final Message message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message);
        }
    }
    
    @Override
    public void warn(final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, throwable);
        }
    }
    
    @Override
    public void warn(final MessageSupplier messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(messageSupplier);
        }
    }
    
    @Override
    public void warn(final MessageSupplier messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(messageSupplier, throwable);
        }
    }
    
    @Override
    public void warn(final CharSequence message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message);
        }
    }
    
    @Override
    public void warn(final CharSequence message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, throwable);
        }
    }
    
    @Override
    public void warn(final Object message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message);
        }
    }
    
    @Override
    public void warn(final Object message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, throwable);
        }
    }
    
    @Override
    public void warn(final String message) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message);
        }
    }
    
    @Override
    public void warn(final String message, final Object... params) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, params);
        }
    }
    
    @Override
    public void warn(final String message, final Supplier<?>... paramSuppliers) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, paramSuppliers);
        }
    }
    
    @Override
    public void warn(final String message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, throwable);
        }
    }
    
    @Override
    public void warn(final Supplier<?> messageSupplier) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(messageSupplier);
        }
    }
    
    @Override
    public void warn(final Supplier<?> messageSupplier, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(messageSupplier, throwable);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2, p3);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2, p3, p4);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2, p3, p4, p5);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2, p3, p4, p5, p6);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2, p3, p4, p5, p6, p7);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        }
    }
    
    @Override
    public void warn(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5, final Object p6, final Object p7, final Object p8, final Object p9) {
        
        for(final Logger logger : this.loggers) {
            logger.warn(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }
    }
    
    
    @Override
    public void logMessage(final Level level, final Marker marker, final String fqcn, final StackTraceElement location, final Message message, final Throwable throwable) {
        
        for(final Logger logger : this.loggers) {
            logger.logMessage(level, marker, fqcn, location, message, throwable);
        }
    }
    
    @Override
    public LogBuilder atTrace() {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.atTrace();
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
    @Override
    public LogBuilder atDebug() {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.atDebug();
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
    @Override
    public LogBuilder atInfo() {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.atInfo();
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
    @Override
    public LogBuilder atWarn() {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.atWarn();
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
    @Override
    public LogBuilder atError() {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.atError();
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
    @Override
    public LogBuilder atFatal() {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.atFatal();
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
    @Override
    public LogBuilder always() {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.always();
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
    @Override
    public LogBuilder atLevel(final Level level) {
        
        for(final Logger logger : this.loggers) {
            final LogBuilder b = logger.atLevel(level);
            if(b != LogBuilder.NOOP) {
                return b;
            }
        }
        
        return LogBuilder.NOOP;
    }
    
}
