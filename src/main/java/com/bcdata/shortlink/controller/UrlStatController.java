package com.bcdata.shortlink.controller;

import com.bcdata.shortlink.entity.UrlStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * url statitics controller
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 22 16:23
 */

@RestController
@RequestMapping(path = "/stat")
public class UrlStatController {

    private static final Logger logger = LoggerFactory.getLogger (UrlStatController.class);

    private final ReactiveRedisOperations<String, UrlStat> urlStatOps;

    UrlStatController(ReactiveRedisOperations<String, UrlStat> urlStatOps) {
        logger.info ("initialize the url stat controller");
        this.urlStatOps = urlStatOps;
    }

    @GetMapping("/all")
    public Flux<UrlStat> all() {
        logger.info ("stat all request");
        return urlStatOps.keys ("*").flatMap (urlStatOps.opsForValue ()::get);
    }

    @GetMapping("/query")
    public @ResponseBody String query(@RequestParam(name = "uri", required = true) String uri) {
        logger.info ("uri parameter is: " + uri);
        Flux<UrlStat> results = urlStatOps.keys (uri).flatMap (urlStatOps.opsForValue ()::get);
        StringBuilder resultBuilder = new StringBuilder ();
        for (UrlStat urlStat : results.toIterable ()) {
            logger.info ("url stat: " + urlStat);
            resultBuilder.append (urlStat.getShortLink ()).append (": ").append (urlStat.getCount ()).append ("\n");
        }

        logger.info ("return result: " + resultBuilder.toString ());
        return resultBuilder.toString ();
    }
}
