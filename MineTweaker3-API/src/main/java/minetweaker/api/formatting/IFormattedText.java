package minetweaker.api.formatting;

import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.formatting.IFormattedText")
public interface IFormattedText {

    @ZenOperator(OperatorType.ADD)
    IFormattedText add(IFormattedText other);

    @ZenOperator(OperatorType.CAT)
    IFormattedText cat(IFormattedText other);
}
