#include <jni.h>
//#include <string>

#ifndef _Included_com_tao_wei_testjni_ShellHolderView
#define _Included_com_tao_wei_testjni_ShellHolderView
//extern "C" JNIEXPORT jstring JNICALL
#ifdef __cplusplus
extern "C" {
#endif

jint native_add__II(JNIEnv *env, jobject thiz, jint a, jint b){
    return a + b;
}

jfloat native_add__FF(JNIEnv *env, jobject thiz, jfloat a, jfloat b){
    return a + b;
}

JNINativeMethod method[]={

        //{"funStatic","(II)I",(void*)native_funStatic},
        {"nativeAdd","(II)I",(int*)native_add__II},
        {"nativeAdd","(FF)F",(void*)native_add__FF},
        //{"createObject","()Lcom/skyworthauto/testjni/JavaObject;",(int*)native_createObject},
        //{"createObjectAndSet","()Lcom/skyworthauto/testjni/JavaObject;",(void*)native_createObjectAndSet},
        //{"getField","()I",(int*)native_getField},
        //{"getStaticField","()I",(void*)native_getStaticField},
        //{"invokeMethod","()I",(int*)native_invokeMethod},
        //{"invokeStaticMethod","()I",(int*)native_invokeStaticMethod},

};

//注册相应的类以及方法
jint registerNativeMethod(JNIEnv *env){
//_Included_com_tao_wei_testjni_ShellHolderView
    jclass cl=env->FindClass("com/tao/wei/testjni/ShellHolderView");
    if((env->RegisterNatives(cl,method,sizeof(method)/sizeof(method[0])))<0){
        return -1;
    }
    return 0;
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env = NULL;
    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }
    if(registerNativeMethod(env)!=JNI_OK){//注册方法
        return -1;
    }
    return JNI_VERSION_1_4;//必须返回这个值
}

#ifdef __cplusplus
}
#endif
#endif