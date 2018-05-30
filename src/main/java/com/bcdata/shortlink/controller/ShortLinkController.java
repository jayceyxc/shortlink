package com.bcdata.shortlink.controller;

import com.bcdata.shortlink.config.ShortLinkWebConfig;
import com.bcdata.shortlink.entity.ShortLink;
import com.bcdata.shortlink.entity.ShortLinkRepository;
import com.bcdata.shortlink.utils.ShortUrlGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * short link app.controller
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 13:30
 */
//@RestController    // This means that this class is a RestController
@Controller
public class ShortLinkController {

    private static final Logger logger = LoggerFactory.getLogger (ShortLinkController.class);

    @Autowired // This means to get the bean called shortLinkRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private ShortLinkRepository shortLinkRepository;

    private Map<String, ShortLink> shortLinkCache = new ConcurrentHashMap<> ();

    private HashSet<String> updatedUri = new HashSet<> ();

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock ();

    ExecutorService exec = Executors.newFixedThreadPool (1);

    @Autowired
    private ShortLinkWebConfig conf;

    private String domain;

    @PostConstruct
    public void loadData() {
        List<ShortLink> results = shortLinkRepository.findAll ();
        for (ShortLink shortLink : results) {
            logger.info ("init short for uri: " + shortLink.getUri ());
            shortLinkCache.put (shortLink.getUri (), shortLink);
        }

        domain = conf.getDomain ();
        if (domain == null || domain.isEmpty ()) {
            logger.warn ("use default domain configuration");
            domain = "t.0hi.cn";
        }
        logger.info ("read domain from configuration: " + domain);
    }

    @PostConstruct
    public void createFlushThread() {
        logger.info ("create flush data thread");
        Runnable flushTask = () -> {
            while (true) {
                try {
                    logger.info ("run the update task");
                    try {
                        lock.writeLock ().lock ();
                        logger.info ("flush thread get write lock");
                        for (String uri : updatedUri) {
                            ShortLink shortLink = shortLinkCache.get (uri);
                            logger.info ("flush the data for uri: " + uri + ", content is " + shortLink);
                            shortLinkRepository.saveAndFlush (shortLink);
                        }
                        updatedUri.clear ();
                    } finally {
                        lock.writeLock ().unlock ();
                        logger.info ("flush thread release write lock");
                    }
                    TimeUnit.SECONDS.sleep (60);
                } catch (InterruptedException ie) {
                    ie.printStackTrace ();
                }
            }
        };
        exec.submit (flushTask);
    }

    @PreDestroy
    public void destroy() {
        logger.warn ("Before destroy, flush the data");
        for (String uri : updatedUri) {
            ShortLink shortLink = shortLinkCache.get (uri);
            logger.warn ("flush the data for uri: " + uri + ", content is " + shortLink);
            shortLinkRepository.saveAndFlush (shortLink);
        }
        updatedUri.clear ();
    }

    @PostMapping(path="/shortlink/add")    // Map ONLY GET Requests
    public String addShortLink(@RequestParam(name = "url", required = true) String url, @RequestParam(name = "name", required = true) String name, Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        logger.info ("have short link add request, url is: " + url);
        ShortLink shortLink;
        if ((shortLink = shortLinkRepository.findByFullLink (url)) != null) {
            logger.info ("This url already have short link");
            model.addAttribute ("shortLink", shortLink.getShortLink ());
            return "shortlink/short";
        }
        ShortLink shortLinkObj = new ShortLink (url);
        String[] shortLinkArray = ShortUrlGenerator.shortUrl (url);
        String shortLinkStr;
        for (String tempShortLink : shortLinkArray) {
            if (shortLinkRepository.findByUri (tempShortLink) == null) {
                shortLinkStr = tempShortLink;
                shortLinkObj.setUri (shortLinkStr);
                shortLinkStr = String.format ("%s/%s", domain, shortLinkStr);
                shortLinkObj.setShortLink (shortLinkStr);
                shortLinkObj.setName (name);
                ShortLink savedShortLink = shortLinkRepository.save (shortLinkObj);
                logger.info (savedShortLink.toString ());

//                if (format.equals ("json")) {
//                    Map<String, String> results = new HashMap<> ();
//                    results.put ("err", "");
//                    results.put ("url", savedShortLink.getShortLink ());
//
//                    JSONObject jsonObject = new JSONObject (results);
//
//                    return jsonObject.toString ();
//                } else {
//                    return savedShortLink.getShortLink ();
//                }
                model.addAttribute ("shortLink", savedShortLink.getShortLink ());
                return "shortlink/short";
            }
        }

        // 到这里说明根据这个url生成的uri已经都被使用了
//        if (format.equals ("json")) {
//            Map<String, String> results = new HashMap<> ();
//            results.put ("err", "No available short link for url: " + url + ". You can delete the following uri to make the space: " + String.join (",", shortLinkArray));
//            results.put ("url", "");
//
//            JSONObject jsonObject = new JSONObject (results);
//
//            return jsonObject.toString ();
//        } else {
//            return "";
//        }
        model.addAttribute ("message",
                "No available short link for url: " + url + ". You can delete the following uri to make the space: " + String.join (",", shortLinkArray));
        return "shortlink/alreadyExists";
    }

    @GetMapping(path="/shortlink/del")    // Map ONLY GET Requests
    public String delShortLink(@RequestParam(name = "url", required = true) String url, Model model) {
        logger.info ("have short link delete request, url is: " + url);
        String origUrl = url;
        if (shortLinkRepository.findByShortLink (url) == null) {
//            Map<String, String> results = new HashMap<> ();
//            results.put ("err", "short link uri not exists: " + uri);
//            results.put ("result", "");
//
//            JSONObject jsonObject = new JSONObject (results);
//
//            return jsonObject.toString ();
            model.addAttribute ("message", "短链接不存在: " + origUrl);
            return "shortlink/delete";
        }

        try {
            if (!url.startsWith ("http://") && !url.startsWith ("https://")) {
                url = "http://" + url;
            }
            URL netUrl = new URL (url);
            String uri = netUrl.getPath ();
            if (uri.startsWith ("/")) {
                uri = StringUtils.strip (uri,"/");
            }
            if (updatedUri.contains (uri)) {
                try {
                    lock.writeLock ().lock ();
                    logger.info ("remove the uri " + uri + " from updated uri");
                    updatedUri.remove (url);
                } finally {
                    lock.writeLock ().unlock ();
                }
            }
            ShortLink shortLink = shortLinkCache.get (uri);
            if (shortLink != null) {
                logger.info ("flush the data to mysql: " + shortLink);
                shortLinkRepository.saveAndFlush (shortLink);
                logger.info ("remove " + uri + " from cache");
                shortLinkCache.remove (uri);
            } else {
                logger.info ("uri: " + uri + " not in cache");
            }
            shortLinkRepository.deleteByUri (uri);

            model.addAttribute ("message", "删除短链接成功: " + origUrl);
            return "shortlink/delete";
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        }

        model.addAttribute ("message", "短链接不是合法的url " + origUrl);
        return "shortlink/delete";
    }

    @GetMapping(path="/shortlink") // Map ONLY GET Requests
    public String getAllShortLink(Model model) {
        Sort sort = new Sort (Sort.Direction.DESC, "id");
        List<ShortLink> results = shortLinkRepository.findAll (sort);
        for (ShortLink shortLink : results) {
            logger.debug ("record: " + shortLink);
        }
        model.addAttribute ("records", results);

        return "shortlink/index";
    }

    @RequestMapping(value="/{uri}")
    void handleShortLink(HttpServletResponse response, @PathVariable("uri") String uri) throws IOException {
        logger.info ("uri is: " + uri);

        ShortLink savedShortLink = null;
        if (shortLinkCache.containsKey (uri)) {
            savedShortLink = shortLinkCache.get (uri);
        } else {
            savedShortLink = shortLinkRepository.findByUri (uri);
            if (savedShortLink != null) {
                shortLinkCache.put (uri, savedShortLink);
            }
        }
        if (savedShortLink != null) {
            logger.debug ("before saved short link: " + savedShortLink);
            savedShortLink.incrementCount ();
            logger.debug ("after saved short link: " + savedShortLink);
//            shortLinkRepository.saveAndFlush (savedShortLink);
            shortLinkCache.put (uri, savedShortLink);
            if (!updatedUri.contains (uri)) {
                try {
                    if (lock.writeLock ().tryLock (100, TimeUnit.MILLISECONDS)) {
                        logger.info ("main thread get the write lock");
                        try {
                            logger.info ("add the uri to updated uri: " + uri);
                            updatedUri.add (uri);
                        } finally {
                            lock.writeLock ().unlock ();
                            logger.info ("main thread release the write lock");
                        }
                    }
                } catch (InterruptedException ie) {
                    logger.warn (ie.getMessage ());
                }
            }
            logger.info ("Get full link " + savedShortLink.getFullLink ());
            response.sendRedirect (savedShortLink.getFullLink ());
        } else {
            logger.warn ("There is no full link for: " + uri);
//            response.sendError (400);
            response.setStatus (200);
        }
    }
}
