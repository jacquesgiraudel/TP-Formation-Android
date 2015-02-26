#include <jni.h>
 
// Implémentation de la méthode native sayHello()
// thisObj correspond à l'objet java this
jstring Java_com_jgl_jni_NativeCalls_sayHello(JNIEnv *env, jobject thisObj, jstring name) {

   char *hello = "Hello ";
   // Retourne un pointeur sur le tableau de bytes correspondant jusqu'à ReleaseStringUTFChars
   const jbyte *nameBytes  = (*env)->GetStringUTFChars(env, name, JNI_FALSE);

   // Allocation mémoire pour jstring retourné
   char *concatenated = malloc(strlen(hello) + strlen(nameBytes) + 1);

   strcpy(concatenated, hello);
   strcat(concatenated, nameBytes);

   // Création d'un jstring (pour Java String) à partir du format C char*
   jstring retval = (*env)->NewStringUTF(env, concatenated);

   // Libère le pointeur
   (*env)->ReleaseStringUTFChars(env, name, nameBytes);
   // Libère la mémoire
   free(concatenated);

   return retval;
}