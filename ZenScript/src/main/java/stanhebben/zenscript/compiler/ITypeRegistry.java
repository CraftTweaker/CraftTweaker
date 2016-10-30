/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import java.lang.reflect.Type;
import stanhebben.zenscript.type.ZenType;

/**
 *
 * @author Stan
 */
public interface ITypeRegistry {
	ZenType getType(Type type);
}
