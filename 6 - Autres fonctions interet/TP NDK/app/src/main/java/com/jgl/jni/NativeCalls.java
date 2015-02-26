package com.jgl.jni;

/**
 * Created by Jacques Giraudel on 01/02/2015.
 */
public class NativeCalls {
    static {
        // Attention à ne pas mettre "lib" dans le nom de bibliothèque ni le format de fichier (.so)
        System.loadLibrary("hello-jni");
    }

    public static native String sayHello(String name);
}
