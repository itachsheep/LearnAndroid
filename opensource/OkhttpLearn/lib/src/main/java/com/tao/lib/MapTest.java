package com.tao.lib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by taowei on 2018/3/18.
 * 2018-03-18 14:47
 * OkhttpLearn
 * com.tao.lib
 */

public class MapTest {
    public static void main(String[] args){
        Map<String,String> map;
        HashMap<String,String> hashMap = new HashMap<>();
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();

        concurrentHashMap.put("taowei","haha");
    }
}
