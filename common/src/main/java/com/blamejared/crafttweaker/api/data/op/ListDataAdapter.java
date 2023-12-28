package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;

import java.util.function.Function;

interface ListDataAdapter extends Function<IData, ListDataAdapter> {
    IData finish();
}
