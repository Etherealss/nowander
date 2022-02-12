package com;

import java.util.Arrays;

/**
 * @author wtk
 * @date 2022-02-07
 */
public class Test {
    public static void main(String[] args) {
        new Solution().countKDifference(new int[]{1,2,2,1}, 1);
    }
}
class Solution {
    public int countKDifference(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; i < nums.length; j++) {
                if (Math.abs(nums[i] - nums[j]) == k) {
                    count++;
                }
            }
        }
        return count;
    }
}