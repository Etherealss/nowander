package com.nowander.starter;

import java.util.Scanner;

/**
 * @author wang tengkun
 * @date 2022/3/7
 */
@SuppressWarnings({"ALL", "AlibabaAvoidCommentBehindStatement"})
public class Test {

}
class Trigon {
    private double a;
    private double b;
    private double c;

    public Trigon(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double c = scan.nextDouble();
        scan.close();
        Trigon.isTrigon(a, b, c);
    }

    private static void check(double a) {
        if (!(0 <= a && a <= 200)) {
            System.out.println("边大于0小于200");
        }
    }

    public static void isTrigon(double a, double b, double c) {
        check(a);
        check(b);
        check(c);
        if (a + b > c && a + c > b && b + c > a) {
            if (a == b || a == c || b == c) {
                if (a == b && b == c) {
                    System.out.println("可以组成等边三角形");
                } else {
                    System.out.println("可以组成等腰三角形");
                }
                return;
            }
            System.out.println("可以组成普通三角形");
            return;
        }
        System.out.println("不能够组成三角形!");
    }
}
