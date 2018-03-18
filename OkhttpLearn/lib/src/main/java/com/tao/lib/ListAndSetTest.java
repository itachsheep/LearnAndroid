package com.tao.lib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class ListAndSetTest {

    public static void main(String[] args){
        /*List<String> list;
        Set<String> set;

        ArrayList<String> arrayList = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        Vector<String> vector = new Vector<>();
        arrayList.add("taowei0");
        arrayList.add("taowei1");
        arrayList.add("taowei2");
        arrayList.add("taowei3");
        arrayList.add(2,"xiaoming");
        arrayList.add(2,null);

        vector.add("haha");
        vector.set(0,"dd");
        vector.add(1,"ddd");*/

        /*for(int i = 0; i < arrayList.size(); i++){
            System.out.println("i = "+i+", value = "+arrayList.get(i));
        }*/
        HashSet<String> hashSet = new HashSet<>();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet();
       // TreeSet<String> treeSet = new TreeSet<>();
        hashSet.add("tao0");
        hashSet.add("tao1");
        hashSet.add("tao2");
        hashSet.add("tao3");
        hashSet.add("tao4");


        linkedHashSet.add("taowe0");
        linkedHashSet.add("taowe1");
        linkedHashSet.add("taowe2");
        linkedHashSet.add("taowe3");
        linkedHashSet.add("taowe4");

       /* Iterator<String> iterator = hashSet.iterator();
        while (iterator.hasNext()){
            String element = iterator.next();
            System.out.println("hashset element = "+element);
        }

        Iterator<String> iterator2 = linkedHashSet.iterator();
        while (iterator2.hasNext()){
            String element = iterator2.next();
            System.out.println("linkedHashSet element = "+element);
        }*/


       TreeSet<Test> treeSet = new TreeSet<>();
       treeSet.add(new Test("jimi1",100));
       treeSet.add(new Test("jimi2",23));
       treeSet.add(new Test("jimi3",45));
       treeSet.add(new Test("jimi4",67));
       treeSet.add(new Test("jimi5",90));
       Iterator<Test> iterator = treeSet.iterator();
       while (iterator.hasNext()){
           Test next = iterator.next();
           System.out.println("TreeSet element = "+next.name+", age = "+next.age);
       }

    }

    private static class Test implements Comparable{
        String name;

        int age;
        public Test(String name,int age){
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Object o) {
            Test t = (Test) o;
            int res = this.age - t.age;
            return res;
        }
    }
}
