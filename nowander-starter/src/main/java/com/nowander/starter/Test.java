package com.nowander.starter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @date 2022-03-06
 */
public class Test {
    public static void main(String[] args) {
        new Solution().isPalindrome("A man, a plan, a canal: Panama");
    }
}
class Solution {
    public boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        int i = 0, j = len - 1;
        while (i < j) {
            if (!isLetter(chars, i)) {
                i++;
            } else if (!isLetter(chars, j)) {
                j--;
            } else if (chars[i] == chars[j] || (chars[i] ^ 32) == chars[j]) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isLetter(char[] chars, int i) {
        return chars[i] >= '0' || chars[i] <= '9' ||
                Character.isLetter(chars[i]);
    }
}