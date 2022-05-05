package com.nowander.starter;

import java.util.Scanner;

/**
 * @author wang tengkun
 * @date 2022/5/5
 */
public class NextDateTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入年月日，系统将输出你所输入的下一天");
        System.out.println("请输入年");
        int year = sc.nextInt();
        System.out.println("请输入月");
        int month = sc.nextInt();
        System.out.println("请输入日");
        int day = sc.nextInt();

        NextDateCalculator nextDateCalculator = new NextDateCalculator(year, month, day);
        String nextDate = nextDateCalculator.getNextDate();
        System.out.println("后一天是：" + nextDate);

    }

}

/**
 * 计算下一天的日期的计算器
 */
class NextDateCalculator {
    private final int year;
    private final int month;
    private final int day;
    /**
     * 当月最大天数
     */
    private final int maxday;

    public NextDateCalculator(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        // 检查输入
        checkInput();
        // 获取当月的最大值
        maxday = getMaxDayInMonth();
        checkMaxDay();
    }

    /**
     * 检查输入是否有误
     */
    private void checkInput() {
        if (year <= 0 || month < 0 || month > 12 || day < 0 || day > 31) {
            throw new IllegalArgumentException("日期信息错误，请重新输入");
        }
    }

    /**
     * 检查输入的天数大于在当月的最大天数
     */
    private void checkMaxDay() {
        if (day > maxday) {
            throw new IllegalArgumentException("输入的日期超过了当月的最大日期，请重新输入");
        }
    }

    public String getNextDate() {
        // 下一天
        int nextDay = this.day + 1;
        int monthOfNextDay = this.month;
        int yearOfNextDay = this.year;
        // 判断天需要不需要进位
        if (nextDay > maxday) {
            monthOfNextDay++;
            nextDay = 1;
        }
        // 判断月需要不需要进位
        if (monthOfNextDay > 12) {
            yearOfNextDay++;
            monthOfNextDay = 1;
        }

        return yearOfNextDay + "年" + monthOfNextDay + "月" + nextDay + "日";
    }

    /**
     * 根据月份获取当月的天数
     * @return
     */
    private int getMaxDayInMonth() {
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            // 34、6、9、11月有30天
            return 30;
        }
        if (month == 2) {
            // 2月需要判断是否闰年
            if (isLeapYear()) {
                return 29;
            } else {
                return 28;
            }
        }
        // 其他月份有31天
        return 31;
    }

    /**
     * 判断是否闰年
     * @return
     */
    private boolean isLeapYear() {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }
}