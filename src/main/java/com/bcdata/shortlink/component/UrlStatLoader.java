package com.bcdata.shortlink.component;

import com.bcdata.shortlink.entity.UrlStat;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * load data when restart
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 22 12:14
 */

@Component
public class UrlStatLoader {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, UrlStat> shortLinkOps;

    private static Random random = new Random ();

    public UrlStatLoader (ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, UrlStat> shortLinkOps) {
        this.factory = factory;
        this.shortLinkOps = shortLinkOps;
    }

    @PostConstruct
    public void loadData () {
        factory.getReactiveConnection ().serverCommands ().flushAll ().thenMany (
                Flux.just ("Jet Black Redis", "Darth Redis", "Black Alert Redis")
                        .map (shortLink -> new UrlStat (shortLink, random.nextInt (1000)))
                        .flatMap (shortLink -> shortLinkOps.opsForValue ().set (shortLink.getShortLink (), shortLink)))
                .thenMany (shortLinkOps.keys ("*")
                        .flatMap (shortLinkOps.opsForValue ()::get))
                .subscribe (System.out::println);
    }
}
