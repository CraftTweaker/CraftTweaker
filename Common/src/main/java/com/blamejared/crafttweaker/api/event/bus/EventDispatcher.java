package com.blamejared.crafttweaker.api.event.bus;

import java.util.function.Consumer;

sealed interface EventDispatcher<T> extends Consumer<T> permits CancelingEventDispatcher, DirectEventDispatcher {}
