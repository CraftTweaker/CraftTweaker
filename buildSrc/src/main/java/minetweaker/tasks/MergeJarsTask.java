/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.tasks;

import java.io.File;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

/**
 *
 * @author Stan
 */
public class MergeJarsTask extends DefaultTask {
	@InputFile
	public File mainInput;
	
	@InputFile
	public File[] inputs;
	
	@OutputFile
	public File output;
	
	@TaskAction
	public void doTask() {
		System.out.println("Main: " + mainInput.toString());
		System.out.println("Inputs: " + inputs.length);
		System.out.println("Output: " + output.toString());
	}
}
