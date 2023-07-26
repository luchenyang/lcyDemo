package com.lcy.demo.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description:
 * @author: luchenyang
 * @date: 2022/8/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestModel<T> {
    private T param;

    private String companyCode;

    private String sign;
}