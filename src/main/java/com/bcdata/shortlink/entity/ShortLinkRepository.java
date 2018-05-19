package com.bcdata.shortlink.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * the short link repository
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 13:29
 */
public interface ShortLinkRepository extends JpaRepository<ShortLink, Integer> {

    ShortLink findByUri(String uri);
}
