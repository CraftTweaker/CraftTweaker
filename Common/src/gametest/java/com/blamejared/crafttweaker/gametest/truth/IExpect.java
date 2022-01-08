package com.blamejared.crafttweaker.gametest.truth;

import com.google.common.truth.Expect;
import com.google.common.truth.FailureStrategy;
import com.google.common.truth.StandardSubjectBuilder;
import net.minecraft.gametest.framework.GameTestAssertException;

public interface IExpect {
    
    FailureStrategy GAME_TEST_ASSERTION_FAILURE = failure -> {
        failure.printStackTrace();
        GameTestAssertException gtae = new GameTestAssertException(failure.getMessage());
        gtae.setStackTrace(failure.getStackTrace());
        throw gtae;
    };
    
    default StandardSubjectBuilder expect() {
        
        return Expect.forCustomFailureStrategy(GAME_TEST_ASSERTION_FAILURE);
    }
    
}
