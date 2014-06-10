package stanhebben.zenscript.docs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

/**
 *
 * @author Stan
 */
public class ZenScriptDoclet {
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; ++i) {
            System.out.println(classes[i]);
        }
        return true;
    }
}
