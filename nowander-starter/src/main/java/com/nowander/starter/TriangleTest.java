package com.nowander.starter;

import java.util.Scanner;

/**
 * @author wang tengkun
 */
class TriangleTest {
    public static void main(String[] args) {
        // 输入三角形三条边的边长
        double[] sides = inputSides();
        Triangle trigon = new Triangle(sides[0], sides[1], sides[2]);
        // 检查输入的边长能否组成三角形
        trigon.identifyTrigon();
    }

    private static double[] inputSides() {
        try (Scanner scan = new Scanner(System.in)) {
            double[] sides = new double[3];
            // 输入三条边的长度
            for (int i = 0; i < Triangle.SIDE_COUNT; ) {
                sides[i] = scan.nextDouble();
                if (!Triangle.isEffectiveLength(sides[i])) {
                    // 不是有效的边长则要重新输入
                    System.out.println("边应该大于0且小于200，请重新输入");
                    continue;
                }
                i++;
            }
            return sides;
        } catch (Throwable inputException) {
            System.err.println("输入异常，程序终止！");
            throw inputException;
        }
    }

}

/**
 * 三角形
 */
class Triangle {

    /**
     * 三角形有三条边
     */
    public static final int SIDE_COUNT = 3;

    /**
     * 三角形的三条边
     */
    private double side1;
    private double side2;
    private double side3;

    public Triangle(double side1, double side2, double side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    /**
     * 检查是否为题目规定的有效的边长
     * @param length
     * @return length在区间[0, 200]内返回true，否则返回false
     */
    public static boolean isEffectiveLength(double length) {
        return 0 <= length && length <= 200;
    }

    /**
     * 识别输入的三条边长能组成的三角形
     */
    public void identifyTrigon() {
        if (side1 + side2 > side3 && side1 + side3 > side2 && side2 + side3 > side1) {
            // 三角形两边只和大于第三边
            if (isEqual(side1, side2) || isEqual(side1, side3) || isEqual(side2, side3)) {
                // 有两条边相等
                if (isEqual(side1, side2) && isEqual(side2, side3)) {
                    // 三条边都相等
                    System.out.println("可以组成等边三角形");
                } else {
                    // 只有两条边相等
                    System.out.println("可以组成等腰三角形");
                }
            } else {
                System.out.println("可以组成普通三角形");
            }
        } else {
            System.out.println("不能够组成三角形!");
        }
    }

    private boolean isEqual(double d1, double d2) {
        return Double.compare(d1, d2) == 0;
    }
}
