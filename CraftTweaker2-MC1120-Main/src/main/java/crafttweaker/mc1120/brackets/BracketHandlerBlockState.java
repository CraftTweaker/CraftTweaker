package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.mc1120.block.MCBlockState;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.impl.BracketHandler;
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
            for (String propertyPair : properties.split(",")) {
                String[] splitPair = propertyPair.split("=");
                if (splitPair.length != 2)
                    return null;
                iBlock = iBlock.withProperty(splitPair[0], splitPair[1]);
            }
        }

        return iBlock;
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        IZenSymbol zenSymbol = null;

        if (tokens.size() > 2) {
            if ("blockstate".equalsIgnoreCase(tokens.get(0).getValue())) {
                String blockName;
                String properties = null;
                if (tokens.size() >= 5) {
                    blockName = tokens.get(2).getValue() + ":" + tokens.get(4).getValue();
                } else {
                    blockName = tokens.get(2).getValue();
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
