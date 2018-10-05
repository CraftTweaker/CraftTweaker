package crafttweaker.mc1120.brackets;

import crafttweaker.*;
import crafttweaker.annotations.*;
import crafttweaker.api.block.IBlockState;
import crafttweaker.mc1120.block.MCBlockState;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerBlockState implements IBracketHandler {
    private final IJavaMethod method;

    public BracketHandlerBlockState() {
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerBlockState.class, "getBlockState", String.class, String.class);
    }

    public static IBlockState getBlockState(String name, String properties) {
        IBlockState iBlock = null;
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));

        if (block != null) {
            iBlock = new MCBlockState(block.getDefaultState());
            if (properties != null && properties.length() > 0) {
                for (String propertyPair : properties.split(",")) {
                    String[] splitPair = propertyPair.split("=");
                    if (splitPair.length != 2)
                        return null;
                    iBlock = iBlock.withProperty(splitPair[0], splitPair[1]);
                }
            }
        }

        return iBlock;
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        IZenSymbol zenSymbol = null;

        String bracketString = tokens.stream().map(Token::getValue).reduce("", String::concat);

        String[] split = bracketString.split(":", 4);

        if (split.length > 1) {
            if ("blockstate".equalsIgnoreCase(split[0])) {
                String blockName;
                String properties = "";
                if (split.length > 2) {
                    blockName = split[1] + ":" + split[2];
                    if (split.length > 3) {
                        properties = split[3];
                    }
                } else {
                    blockName = split[1];
                }
                zenSymbol = new BlockStateReferenceSymbol(environment, blockName, properties);
            }
        }

        return zenSymbol;
    }

    @Override
    public String getRegexMatchingString() {
        return "blockstate:.*";
    }

    @Override
    public Class<?> getReturnedClass() {
        return IBlockState.class;
    }

    private class BlockStateReferenceSymbol implements IZenSymbol {
        private final IEnvironmentGlobal environment;
        private final String name;
        private final String properties;

        public BlockStateReferenceSymbol(IEnvironmentGlobal environment, String name, String properties) {
            this.environment = environment;
            this.name = name;
            this.properties = properties;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name), new ExpressionString(position, properties));
        }
    }
}
