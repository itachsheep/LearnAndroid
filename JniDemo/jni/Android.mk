LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hellow
LOCAL_SRC_FILES := hellow.cpp

include $(BUILD_SHARED_LIBRARY)
