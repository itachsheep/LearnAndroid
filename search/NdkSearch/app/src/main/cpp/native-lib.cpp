#include <jni.h>
#include <string>

//extern "C" JNIEXPORT jstring



#ifdef __cplusplus
extern "C" {
#endif



//jint native_funStatic(JNIEnv *env, jclass clazz, jint a, jint b){
//    return a + b;
//}

JNIEXPORT jstring Java_com_tao_ndksearch_MainActivity_stringFromJNI(
        JNIEnv *env, jobject thiz/* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    funStatic
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_funStatic
        (JNIEnv *env, jclass clazz, jint a, jint b){
    return a+b;
}

/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    fun
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_fun__II
        (JNIEnv *env, jobject object, jint a , jint b){
    return a+b;
}

/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    fun
 * Signature: (FF)F
 */
JNIEXPORT jfloat JNICALL Java_com_tao_ndksearch_TestJni_fun__FF
        (JNIEnv *env, jobject object, jfloat a, jfloat b){
    return (a+b);
}

/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    createObject
 * Signature: ()Lcom/tao/ndksearch/JavaObject;
 */
JNIEXPORT jobject JNICALL Java_com_tao_ndksearch_TestJni_createObject
        (JNIEnv *env, jobject object){
    jclass targetClass = env->FindClass("com/tao/ndksearch/JavaObject");
    jmethodID constructId = env->GetMethodID(targetClass,"<init>","(I)V");
    jobject newObject = env->NewObject(targetClass,constructId,12345);
    return newObject;
}
/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    createObjectAndSet
 * Signature: ()Lcom/tao/ndksearch/JavaObject;
 */
JNIEXPORT jobject JNICALL Java_com_tao_ndksearch_TestJni_createObjectAndSet
        (JNIEnv *env, jobject thiz){
    jclass targetClass = env->FindClass("com/tao/ndksearch/JavaObject");
    jmethodID constructId = env->GetMethodID(targetClass,"<init>","()V");
    jobject newObject = env->NewObject(targetClass,constructId);


    jfieldID fId = env->GetFieldID(targetClass,"mNum","I");
    env->SetIntField(newObject,fId,12345);
    jfieldID fIdStatic = env->GetStaticFieldID(targetClass,"sNum","I");
//    env->SetIntField(newObject,fIdStatic,22345);
    env->SetStaticIntField(targetClass,fIdStatic,22345);
    return newObject ;


}

/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    getField
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_getField
        (JNIEnv *, jobject);

/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    getStaticField
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_getStaticField
        (JNIEnv *, jobject);

/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    invokeMethod
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_invokeMethod
        (JNIEnv *, jobject);

/*
 * Class:     com_tao_ndksearch_TestJni
 * Method:    invokeStaticMethod
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_tao_ndksearch_TestJni_invokeStaticMethod
        (JNIEnv *, jobject);




//JNINativeMethod method[] = {
//        {"funStatic","(II)I",(void*)native_funStatic},
//};

#ifdef __cplusplus
}
#endif
