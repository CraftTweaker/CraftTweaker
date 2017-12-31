package crafttweaker.api.formatting;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.formatting.IFormattedText")
@ZenRegister
public interface IFormattedText {

    @ZenOperator(OperatorType.ADD)
    IFormattedText add(IFormattedText other);

    @ZenOperator(OperatorType.CAT)
    IFormattedText cat(IFormattedText other);

    String getText();
}
