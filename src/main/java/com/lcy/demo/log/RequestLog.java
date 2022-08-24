package com.lcy.demo.log;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName winser_request_log
 */
@Data
public class RequestLog implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private String customerCd;
    

    /**
     *
     */
    private String resStatus;

    /**
     *
     */
    private String orginReqData;

    /**
     *
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}