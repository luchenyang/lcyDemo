package com.lcy.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;


public class test {
    public void importAbnormalAct() throws FileNotFoundException {
        JSONObject acts = new JSONObject();
        File file = new File("src/main/resources/1.xlsx");
        FileInputStream outputStream = new FileInputStream(file);

        XSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new XSSFWorkbook(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            XSSFSheet sheetAt1 = hssfWorkbook.getSheetAt(0);
            int lastRowNum = sheetAt1.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = sheetAt1.getRow(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void jj(List<JSONObject> jsonObjects){
        LinkedList<JSONObject> objects = new LinkedList<>();

        for(JSONObject jsonObject:jsonObjects){
            JSONObject js = new JSONObject();

            String id = String.valueOf(jsonObject.get("B"));

            js.put("row_id", encrypt2ToMD5(id));
            js.put("media_type",String.valueOf(jsonObject.get("I")));



        }
    }

    /**
     * MD5加密之方法二
     * @explain java实现
     * @param str
     *            待加密字符串
     * @return 16进制加密字符串
     */
    public static String encrypt2ToMD5(String str) {
        // 加密后的16进制字符串
        String hexStr = "";
        try {
            // 此 MessageDigest 类为应用程序提供信息摘要算法的功能
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 转换为MD5码
            byte[] digest = md5.digest(str.getBytes("utf-8"));
            hexStr = String.format("%032X", new BigInteger(1, digest));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String one = hexStr.substring(0,8);
        String two = hexStr.substring(8, 12);
        String three = hexStr.substring(12, 16);
        String four = hexStr.substring(16, 20);
        String five = hexStr.substring(20);
        return one+"-"+two+"-"+three+"-"+four+"-"+five;
    }


}
