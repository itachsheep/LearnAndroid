package com.tao.javalib

/**
 * Created by SDT14324 on 2017/11/23.
 */
class RunKotlin(val name: String) {
    fun greet() {
        println("Hello 你好, $name")
    }
}

fun main(args: Array<String>) {
    RunKotlin(args[0]).greet()
}
