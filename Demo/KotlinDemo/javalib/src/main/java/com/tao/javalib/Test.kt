package com.tao.javalib

import jdk.nashorn.internal.objects.Global.print

/**
 * Created by SDT14324 on 2017/11/23.
 */
class Test{
    companion object {
        public fun say(){
            print("companion say ...")
        }
    }

    public fun say(){
        print("say normal...")
    }
}