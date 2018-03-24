#include <jni.h>
#include "com_example_ndkdemo_JniClient.h"
#include <stdlib.h>
#include <stdio.h>

#define TAG "jniCLient"
//#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
//#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
//#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)

//#ifdef __cplusplus
//extern "C"
//{
//#endif

jstring Java_com_example_ndkdemo_JniClient_printStr
	(JNIEnv *env,jclass arg)
{
	jstring str = (*env)->NewStringUTF(env, "HelloWorld from JNI !");
	//LOGD("log from jni");
	return str;
}

