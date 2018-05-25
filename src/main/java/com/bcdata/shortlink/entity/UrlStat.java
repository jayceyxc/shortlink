package com.bcdata.shortlink.entity;

import lombok.*;

import java.util.Objects;

/**
 * The statistics of the url visit
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 20:10
 */
@Data
public class UrlStat {
    private String shortLink;
    private int count;

    public UrlStat () {
    }

    public UrlStat (String shortLink, int count) {
        this.shortLink = shortLink;
        this.count = count;
    }

    public String getShortLink () {
        return shortLink;
    }

    public void setShortLink (String shortLink) {
        this.shortLink = shortLink;
    }

    public int getCount () {
        return count;
    }

    public void setCount (int count) {
        this.count = count;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        UrlStat urlStat = (UrlStat) o;
        return getCount () == urlStat.getCount () &&
                Objects.equals (getShortLink (), urlStat.getShortLink ());
    }

    @Override
    public int hashCode () {

        return Objects.hash (getShortLink (), getCount ());
    }

    @Override
    public String toString () {
        return "UrlStat{" +
                "shortLink='" + shortLink + '\'' +
                ", count=" + count +
                '}';
    }
}
