package com.xxl.pojo;

import java.util.*;

/**
 * @author xxl
 * @create 2021-12-31 13:38
 */
public class Main {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);

        int num = s.nextInt();

        LinkedHashSet<String> list;

        for(int i=0;i<num;i++){
            list = new LinkedHashSet<>();

            int n =s.nextInt();
            s.useDelimiter("\n");   //用\n作为分隔符
            for(int j=0;j<n;j++){
                String s1 = s.next().toLowerCase();
                if(!list.contains(s1))
                    list.add(s1);//转为小写比较
            }
            System.out.println(list);
            System.out.println(list.size());

        }


    }

}
