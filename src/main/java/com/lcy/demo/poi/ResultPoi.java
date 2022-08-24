package com.lcy.demo.poi;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/7/27
 */
@Data
public class ResultPoi {

    @JSONField(name = "id",ordinal = 1)
    private String rowId;
    @JSONField(name="media_type",ordinal = 2)
    private String mediaType;
    @JSONField(ordinal = 3)
    private String classify;
    @JSONField(name ="org_id",ordinal = 4)
    private String orgId = "20000486";
    @JSONField(ordinal = 5)
    private String org ="岚图汽车科技有限公司";
    @JSONField(ordinal = 6)
    private String brand1 ="岚图";
    @JSONField(ordinal = 7)
    private String brand ="岚图";
    @JSONField(name ="emtl_tendency",ordinal = 8)
    private String emtlTendency;
    @JSONField(ordinal = 9)
    private String title;

    @JSONField(name ="keyword_in_title",ordinal = 10)
    private String keywordInTitle;

    @JSONField(name ="keyword_frequency",ordinal = 11)
    private String keywordFrequency;

    @JSONField(name ="publish_date",ordinal = 12)
    private String publishDate;

    @JSONField(ordinal = 13)
    private String media;
    @JSONField(name ="social_media",ordinal = 14)
    private String socialMedia;
    @JSONField(ordinal = 15)
    private String channel;
    @JSONField(ordinal = 16)
    private String source;
    @JSONField(ordinal = 17)
    private String author;
    @JSONField(ordinal = 18)
    private String url;

    @JSONField(name ="media_rank",ordinal = 19)
    private String mediaRank;
    @JSONField(ordinal = 20)
    private String dataof = "HK";

}