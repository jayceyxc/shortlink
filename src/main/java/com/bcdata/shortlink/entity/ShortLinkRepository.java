package com.bcdata.shortlink.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * the short link repository
 *
 * @author yuxuecheng
 * @version 1.0
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 13:29
 */
@Component
//@CacheConfig(cacheManager = "short_link")
public interface ShortLinkRepository extends JpaRepository<ShortLink, Integer> {

//    @Cacheable(key = "#p0")
    ShortLink findByUri(String uri);

//    @Cacheable
//    ShortLink findById(String id);
//
//    @CachePut(key = "#p0")
//    @Override
//    ShortLink save(ShortLink shortLink);

    ShortLink findByFullLink(String fullLink);

    ShortLink findByShortLink(String shortLink);

    @Transactional
//    @Modifying
//    @CacheEvict
    void deleteByUri(String uri);

    @Transactional
    void deleteByShortLink(String shortLink);

    @Transactional
    void deleteByFullLink(String fullLink);
}
