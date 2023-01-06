package com.blamejared.crafttweaker.api.logging;

import org.apache.logging.log4j.Logger;

public interface ILoggerRegistry {
    
    Logger getLoggerFor(final String system);
    
}
