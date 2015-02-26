# Chemin du répertoire courant "my-dir"
LOCAL_PATH := $(call my-dir)

# Réinitialisation de toutes les variables LOCAL_XXX sauf LOCAL_PATH (à passer avant chaque build)
include $(CLEAR_VARS)

# Réinitialisation pour le build
include $(CLEAR_VARS)

# Déclaration du module à construire
LOCAL_MODULE    := hello-jni
# Fichiers C et C++ à construire et assembler au module
LOCAL_SRC_FILES := hello-jni.c

# Détermine le type de bibliothèque à construire SHARED ou STATIC
# Utilise toutes les variables LOCAL_XXX utilisées depuis le dernier include
include $(BUILD_SHARED_LIBRARY)
