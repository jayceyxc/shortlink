package com.bcdata.shortlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The statistics of the url visit
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 20:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlStat {
    private String shortLink;
    private String count;
}
