import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.runtime.*;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import org.junit.Test;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.util.ZenPosition;

import java.io.File;
import java.util.List;

/**
 * Created by Admin on 13.04.2016.
 */
public class GlobalValuesTest {
    public static IScriptProvider scriptProvider;

    static {
        MineTweakerImplementationAPI.logger.addLogger(new TestLogger());
        scriptProvider = new ScriptProviderDirectory(new File("scripts"));
//        GlobalRegistry.registerBracketHandler(new IBracketHandler() {
//            @Override
//            public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
//                String name = tokens.get(2).getValue();
//                return new IZenSymbol() {
//                    @Override
//                    public IPartialExpression instance(ZenPosition position) {
//                        return new ExpressionCallStatic(position, environment,
//                                MineTweakerAPI.getJavaMethod(GlobalFunctions.class, "get", String.class),
//                                new ExpressionString(position, name));
//                    }
//                };
//            }
//        });
    }

    @Test
    public void test_scripts() {
        ITweaker tweaker = new MTTweaker();
        tweaker.setScriptProvider(scriptProvider);
        tweaker.load();
    }


    public static class TestLogger implements ILogger {
        private void log(String msg) {
            System.out.println(msg);
        }

        @Override
        public void logCommand(String message) {
            log(message);
        }

        @Override
        public void logInfo(String message) {
            log(message);
        }

        @Override
        public void logWarning(String message) {
            log(message);
        }

        @Override
        public void logError(String message) {
            log(message);
        }

        @Override
        public void logError(String message, Throwable exception) {
            log(message);
            if (exception != null)
                exception.printStackTrace();
        }
    }
}