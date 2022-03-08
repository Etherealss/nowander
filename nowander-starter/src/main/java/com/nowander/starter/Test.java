package com.nowander.starter;

import java.util.Arrays;

/**
 * @author wang tengkun
 * @date 2022/3/7
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(new Solution().lengthOfLongestSubstring("abcabcbb"));
    }

}
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] last = new int[128];
        Arrays.fill(last, -1);
        int max = 0, start = 0, len = s.length();
        for (int i = 0; i < len; i++) {
            int index = s.charAt(i);
            if (start == last[index]) {
                start = i;
            }
            max = Math.max(max, i - start + 1);
            last[index] = i;
        }
        return max;
    }
}
