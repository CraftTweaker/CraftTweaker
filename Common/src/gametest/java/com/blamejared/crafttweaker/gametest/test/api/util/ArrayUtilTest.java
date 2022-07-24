package com.blamejared.crafttweaker.gametest.test.api.util;

import com.blamejared.crafttweaker.api.util.ArrayUtil;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.mojang.datafixers.util.Pair;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;

@CraftTweakerGameTestHolder
public class ArrayUtilTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testMirrorWorksForEvenNumberOfItemsInArray(GameTestHelper helper) {
        
        getArraysWithEvenNumberOfParameters().forEach(pair -> doTheTest(helper, pair.getFirst(), pair.getSecond()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testMirrorWorksForOddNumberOfItemsInArray(GameTestHelper helper) {
        
        getArraysWithOddNumberOfParameters().forEach(pair -> doTheTest(helper, pair.getFirst(), pair.getSecond()));
    }
    
    private Stream<Pair<String[], String[]>> getArraysWithEvenNumberOfParameters() {
        
        return Stream.<Pair<String[], String[]>> builder()
                .add(new Pair<>(new String[] {"a", "b"}, new String[] {"b", "a"}))
                .add(new Pair<>(new String[] {"a", "b", "c", "d"}, new String[] {"d", "c", "b", "a"}))
                .add(new Pair<>(new String[] {"a", "b", "c", "d", "e", "f"}, new String[] {"f", "e", "d", "c", "b", "a"}))
                .add(new Pair<>(new String[] {"a", null, "c", null, "e", "f"}, new String[] {"f", "e", null, "c", null, "a"}))
                .build();
    }
    
    private Stream<Pair<String[], String[]>> getArraysWithOddNumberOfParameters() {
        
        return Stream.<Pair<String[], String[]>> builder()
                .add(new Pair<>(new String[] {"a"}, new String[] {"a"}))
                .add(new Pair<>(new String[] {"a", "b", "c"}, new String[] {"c", "b", "a"}))
                .add(new Pair<>(new String[] {"a", "b", "c", "d", "e"}, new String[] {"e", "d", "c", "b", "a"}))
                .add(new Pair<>(new String[] {"a", null, "c", null, "e"}, new String[] {"e", null, "c", null, "a"}))
                .build();
    }
    
    
    private void doTheTest(GameTestHelper helper, String[] before, String[] expectedMirrored) {
        
        final String[] beforeClone = before.clone();
        final String[] mirrored = ArrayUtil.mirror(before);
        
        assertThat("ArrayUtil may not modify the actual array", before, arrayContaining(beforeClone));
        
        assertThat("mirred must match expectedMirrored", mirrored, arrayContaining(expectedMirrored));
        
        assertThat("Mirroring twice must restore order", ArrayUtil.mirror(mirrored), arrayContaining(before));
    }
    
}
