package stanhebben.zenscript.value.builtin.expansion;

import stanhebben.zenscript.value.IAny;

/**
 * @author Stan
 */
public interface IByteExpansion {

    IAny member(byte value, String member);
}
