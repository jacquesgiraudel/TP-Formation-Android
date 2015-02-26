#include <jni.h>
 
// Implémentation de la méthode native sayHello()
// thisObj correspond à l'objet java this
jstring Java_com_jgl_jni_NativeCalls_sayHello(JNIEnv *env, jobject thisObj) {
   
   return (*env)->NewStringUTF(env, "Hello from JNI !");
}