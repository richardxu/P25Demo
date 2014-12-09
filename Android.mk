
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := eng
LOCAL_SRC_FILES := $(call all-subdir-java-files)
LOCAL_PACKAGE_NAME := printer_serial

LOCAL_SDK_VERSION := current
LOCAL_CERTIFICATE := platform
LOCAL_JNI_SHARED_LIBRARIES := libserial_port

include $(BUILD_PACKAGE)
include $(call all-makefiles-under,$(LOCAL_PATH))
