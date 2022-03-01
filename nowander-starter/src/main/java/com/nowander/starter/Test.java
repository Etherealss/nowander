package com.nowander.starter;

/**
 * @author wang tengkun
 * @date 2022/3/1
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(new Solution().convert("PAYPALISHIRING", 4));
    }
}
class Solution {
    public String convert(String s, int numRows) {
        StringBuilder builder = new StringBuilder();
        int len = s.length();
        int flag = (numRows - 1) * 2, t = flag;
        for (int i = 0; i < numRows; i++) {
            for (int j = i; j < len; j += flag) {
                builder.append(s.charAt(j));
                if (t != flag && t != 0 && j + flag - t < len) {
                    builder.append(s.charAt(j + flag - t));
                }
            }
            t -= 2;
        }
        return builder.toString();
    }
}