package com;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wtk
 * @date 2022-02-07
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution().printNumbers(2)));
    }
}
class Solution {
    char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    StringBuilder builder = new StringBuilder();
    String[] strs;
    // index是strs的下标
    int index = 0, n;
    public int[] printNumbers(int n) {
        this.n = n;
        int size = (int) Math.pow(10, n);
        strs = new String[size];
        // 数字的每一位递归，得到所有排列
        dfs(n);
        return toIntArr();
    }

    void dfs(int level) {
        if (level == 0) {
            strs[index++] = builder.toString();
            return;
        }
        for (int i = 0; i < num.length; i++) {
            builder.append(num[i]);
            dfs(level-1);
            builder.deleteCharAt(builder.length()-1);
        }
    }

    int[] toIntArr() {
        int[] arr = new int[strs.length - 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(strs[i+1]);
        }
        return arr;
    }
}