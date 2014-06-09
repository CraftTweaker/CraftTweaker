/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.transformer;

import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 * @author Stanneke
 */
public class MineTweakerASM implements IClassTransformer {
	
	@Override
	public byte[] transform(String name, String newName, byte[] bytecode) {
		ClassVisitor visitor = new AnnotationDetector();
		ClassReader reader = new ClassReader(bytecode);
		reader.accept(visitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
		return bytecode;
	}
	
	private class AnnotationDetector extends ClassVisitor {
		public AnnotationDetector() {
			super(Opcodes.ASM4);
		}
		
		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			System.out.println("Annotation: " + desc);
			
			return super.visitAnnotation(desc, visible);
		}
	}
}
