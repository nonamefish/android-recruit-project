package com.hahow.androidRecruitProject.util

import com.hahow.androidRecruitProject.MyApplication

object ResourceUtil {

    fun getString(resourceId: Int, vararg args: Any): String {
        return MyApplication.appContext.getString(resourceId, args)
    }
}