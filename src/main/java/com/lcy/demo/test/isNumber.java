package com.lcy.demo.test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class isNumber {
    /**
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
     *
     * 数值（按顺序）可以分成以下几个部分：
     *
     * 若干空格
     * 一个 小数 或者 整数
     * （可选）一个 'e' 或 'E' ，后面跟着一个 整数
     * 若干空格
     * 小数（按顺序）可以分成以下几个部分：
     *
     * （可选）一个符号字符（'+' 或 '-'）
     * 下述格式之一：
     * 至少一位数字，后面跟着一个点 '.'
     * 至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
     * 一个点 '.' ，后面跟着至少一位数字
     * 整数（按顺序）可以分成以下几个部分：
     *
     * （可选）一个符号字符（'+' 或 '-'）
     * 至少一位数字
     * 部分数值列举如下：
     *
     * ["+100", "5e2", "-123", "3.1416", "-1E-16", "0123"]
     * 部分非数值列举如下：
     * 提示：
     * 1 <= s.length <= 20
     * s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，空格 ' ' 或者点 '.' 。
     * @
     */
        public static boolean isNumber(String s) {
            if (s == null || s.length() == 0) return false;
            //去掉首位空格
            s = s.trim();
            boolean numFlag = false;
            boolean dotFlag = false;
            boolean eFlag = false;
            for (int i = 0; i < s.length(); i++) {
                //判定为数字，则标记numFlag
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    numFlag = true;
                    //判定为.  需要没出现过.并且没出现过e
                } else if (s.charAt(i) == '.' && !dotFlag && !eFlag) {
                    dotFlag = true;
                    //判定为e，需要没出现过e，并且出过数字了
                } else if ((s.charAt(i) == 'e' || s.charAt(i) == 'E') && !eFlag && numFlag) {
                    eFlag = true;
                    numFlag = false;//为了避免123e这种请求，出现e之后就标志为false
                    //判定为+-符号，只能出现在第一位或者紧接e后面
                } else if ((s.charAt(i) == '+' || s.charAt(i) == '-') && (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {

                    //其他情况，都是非法的
                } else {
                    return false;
                }
            }
            return numFlag;
        }


    public static void bucketSort(int[] arr) {
        // 找到数组中的最大值和最小值
        int minVal = arr[0];
        int maxVal = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < minVal) {
                minVal = arr[i];
            } else if (arr[i] > maxVal) {
                maxVal = arr[i];
            }
        }
        // 桶的数量，可以根据实际情况进行调整
        int bucketNum = 5;
        // 计算每个桶的范围
        double bucketRange = (double)(maxVal - minVal + 1) / bucketNum;
        // 创建桶
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketNum; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        // 将元素分配到对应的桶中
        for (int num : arr) {
            int index = (int)((num - minVal) / bucketRange);
            buckets.get(index).add(num);
        }
        // 对每个桶中的元素进行排序
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }
        // 将所有桶中的元素按顺序合并起来
        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {
                arr[index++] = num;
            }
        }
    }

    public static void main(String[] args) {
        isNumber("123E4..5");
    }
}
