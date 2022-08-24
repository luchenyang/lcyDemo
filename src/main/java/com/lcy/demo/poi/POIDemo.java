package com.lcy.demo.poi;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POIDemo {

    public static String[] arr = new String[]{"东风岚图","(?<!东风)岚图(?!MPV|SUV|FREE|梦想家|轿车)","竺延风","尤峥","卢放","雷新","沉军","蒋焘","贾守平","秦捷","VOYAH","岚图MPV","岚图SUV","岚图FREE","岚图梦想家","岚图轿车","特斯拉","理想汽车","蔚来汽车","小鹏","问界M5","极氪","理想One","蔚来ES6","奥迪Q5L","奔驰GLC","沃尔沃XC60","唐新能源","宝马X3","极氪001","领克09","大众ID 6","腾势D9","别克GL8","丰田塞纳","埃尔法","奔驰威霆","嘉华","威然","传祺M8","理想L9"};

    public static String[] arrName = new String[]{"东风岚图","岚图","竺延风","尤峥","卢放","雷新","沉军","蒋焘","贾守平","秦捷","VOYAH","岚图MPV","岚图SUV","岚图FREE","岚图梦想家","岚图轿车","特斯拉","理想汽车","蔚来汽车","小鹏","问界M5","极氪","理想One","蔚来ES6","奥迪Q5L","奔驰GLC","沃尔沃XC60","唐新能源","宝马X3","极氪001","领克09","大众ID 6","腾势D9","别克GL8","丰田塞纳","埃尔法","奔驰威霆","嘉华","威然","传祺M8","理想L9"};
    public static List<String> mediaArr = Arrays.asList("新浪微博","微信");

	public static void main(String[] args) throws IOException {
        JSONObject jsonObject = new JSONObject();
//        endpoint: http://127.0.0.1
//        port: 9000
//        accessKey: minioadmin
//        secretKey: minioadmin
//        bucketName: asiatrip
        jsonObject.put("endpoint","http://127.0.0.1");
        jsonObject.put("port","9000");
        jsonObject.put("accessKey","minioadmin");
        jsonObject.put("secretKey","minioadmin");
        jsonObject.put("bucketName","asiatrip");

    }

    private static void extracted() throws IOException {
        List<ResultPoi> wisersOriginModels = getWisersOriginModels();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/lu_cy/Desktop/aa.json")));
        for(ResultPoi resultPoi:wisersOriginModels){
            writer.write(JSON.toJSONString(resultPoi));
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }


    private static List<ResultPoi> getWisersOriginModels() {
        List<WisersOriginModel> wisersOriginModel = readExcel("/Users/lu_cy/Desktop/东风集团_岚图_样例数据_0727更新.xlsx");
        List<ResultPoi> resultPois = new LinkedList<>();
        for(WisersOriginModel model : wisersOriginModel){
            ResultPoi data = new ResultPoi();
//            data.setRowId(encrypt2ToMD5(model.getDocId()));
            data.setRowId(model.getDocId()+"HK");
            data.setMediaType(model.getMediaType());
            if(StringUtils.endsWithIgnoreCase(model.getCompetitor(),"竞品")){
                data.setClassify("/竞品动态/"+model.getFolderName());
            }else{
                data.setClassify("/企业品牌/子公司新闻/岚图");
            }
            data.setEmtlTendency(model.getSentiment());
            data.setTitle(model.getTitle());
            data.setKeywordInTitle(!StringUtils.isEmpty(hitKeywod(model.getTitle()))?"是":"否");
//            data.setKeywordInTitle(hitKeywod(model.getTitle()));
            data.setKeywordFrequency(hitKeywod(model.getContent()));
            data.setPublishDate(model.getPubTime());
            data.setMedia(model.getMediaName());
            data.setSocialMedia(mediaArr.contains(model.getMediaName())?"自媒体":"");
            data.setChannel(model.getSection());
            data.setSource(model.getMediaName());
            data.setAuthor(model.getAccountName());
            data.setUrl(model.getDocUrl());
            data.setMediaRank("");
            resultPois.add(data);
        }
        return resultPois;
    }


    private static String hitKeywod(String msg){
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<arr.length ;i++){
            String keyword = arr[i];
            Integer num = 0;
            Pattern compile = Pattern.compile(keyword);
            Matcher matcher = compile.matcher(msg);
            while (matcher.find()){
                num++;
            }
            if(num>0){
                sb.append(arrName[i]);
                sb.append(":");
                sb.append(num);
                sb.append(",");
            }
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();
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

    
    public static final String[] titles = new String[] {
        "序号",
        "ID",
        "title",
        "摘要",
        "media_name",
        "媒体名称（英文）",
        "section",
        "pub_time",
        "media_type", 
        "媒体类别",
        "account_name",
        "sentiment",
        "收藏",
        "字数",
        "link_type_id",
        "聚类编号",
        "doc_url",
        "链接状态",
        "广告价值",
        "广告价值更新时间",
        "微博认证",
        "fans_cnt",
        "view_cnt",
        "like_cnt",
        "reply_cnt",
        "forward_cnt",
        "播放数",
        "时长",
        "是否竞品",
        "folder_name"
    };
    


    public static List<WisersOriginModel>   readExcel(String path) {
        ExcelReader reader = ExcelUtil.getReader(path);
        setHeaderAlias(reader);
        //List<TableProjectMapping> projects = reader.readAll(TableProjectMapping.class);
        
        return reader.readAll(WisersOriginModel.class);

    }
    
    private static void setHeaderAlias(ExcelReader reader) {
    	
    	//序号
    	reader.addHeaderAlias(titles[0], "seriesId");
    	
    	//ID
        reader.addHeaderAlias(titles[1], "docId");
        
        reader.addHeaderAlias(titles[2], "title");
        
        //摘要
        reader.addHeaderAlias(titles[3], "content");
        reader.addHeaderAlias(titles[4], "mediaName");
        
        //媒体名称（英文）
        reader.addHeaderAlias(titles[5], "mediaNameEn");
        
        reader.addHeaderAlias(titles[6], "section");
        
        reader.addHeaderAlias(titles[7], "pubTime");
        reader.addHeaderAlias(titles[8], "mediaType");
        
        //媒体类别
        reader.addHeaderAlias(titles[9], "mediaSourceType");
        
        reader.addHeaderAlias(titles[10], "accountName");
        reader.addHeaderAlias(titles[11], "sentiment");
        
        //收藏
        reader.addHeaderAlias(titles[12], "fork");
        
        //字数
        reader.addHeaderAlias(titles[13], "wordCount");
        
        //原创类型
        reader.addHeaderAlias(titles[14], "linkTypeId");
        
        //聚类编号
        reader.addHeaderAlias(titles[15], "code");
        
        reader.addHeaderAlias(titles[16], "docUrl");
        
        //链接状态
        reader.addHeaderAlias(titles[17], "urlStatus");
        
        //广告价值
        reader.addHeaderAlias(titles[18], "adPrice");
        
        //广告价值更新时间
        reader.addHeaderAlias(titles[19], "adUpdateTime");
        
        //微博认证
        reader.addHeaderAlias(titles[20], "WbAuth");
        
        reader.addHeaderAlias(titles[21], "fansCnt");
        reader.addHeaderAlias(titles[22], "viewCnt");
        reader.addHeaderAlias(titles[23], "likeCnt");
        reader.addHeaderAlias(titles[24], "replyCnt");
        reader.addHeaderAlias(titles[25], "forwardCnt");
        
        //播放数
        reader.addHeaderAlias(titles[26], "videoCnt");
        
        //时长
        reader.addHeaderAlias(titles[27], "broadcastTime");
        
        //是否竞品
        reader.addHeaderAlias(titles[28], "competitor");
        reader.addHeaderAlias(titles[29], "folderName");
    }



}
