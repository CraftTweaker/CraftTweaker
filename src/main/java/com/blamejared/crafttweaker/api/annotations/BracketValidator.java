package com.blamejared.crafttweaker.api.annotations;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BracketValidator {
    String value();
}
