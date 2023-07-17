package com.blamejared.crafttweaker.natives.util.collection;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import java.util.Collection;

@ZenRegister
@Document("vanilla/api/util/collection/Collection")
@NativeTypeRegistration(value = Collection.class, zenCodeName = "crafttweaker.api.util.collection.Collection")
public class ExpandCollection {

}
