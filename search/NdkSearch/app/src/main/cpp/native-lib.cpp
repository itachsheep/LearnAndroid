#include <jni.h>
#include <string>

//extern "C" JNIEXPORT jstring



#ifdef __cplusplus
extern "C" {
#endif


JNIEXPORT jstring Java_com_tao_ndksearch_MainActivity_stringFromJNI(
        JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_funStatic
        (JNIEnv *env, jclass clazz, jint a, jint b){
    return a+b;
}


JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_fun
        (JNIEnv *env, jobject object, jint a , jint b){
    return a+b;
}
#ifdef __cplusplus
}
#endif
