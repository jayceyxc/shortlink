package com.bcdata.shortlink;

import com.bcdata.shortlink.entity.ShortLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * The main application for short link project
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 12:27
 */

@SpringBootApplication
//@EnableWebSecurity
@EnableCaching
public class Application implements CommandLineRunner {

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    private static final Logger logger = LoggerFactory.getLogger (Application.class);

    public Application () {
        logger.info ("constructor of app.Application");
    }

    public static void main (String[] args) {
        SpringApplication.run (Application.class, args);
    }
    @Override
    public void run (String... args) throws Exception {
        logger.info ("start main application");
    }
}
