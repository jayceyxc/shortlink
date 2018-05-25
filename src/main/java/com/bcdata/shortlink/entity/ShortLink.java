package com.bcdata.shortlink.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The short link app.entity
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 12:29
 */

@Entity
public class ShortLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "uri", unique = true)
    @org.hibernate.annotations.Index (name = "uri_index")
    private String uri;

    @NotNull
    @Size(min = 2, max=64)
    @Column(name = "short_link", updatable = false)
    private String shortLink;

    @NotNull
    @Column(name = "full_link", updatable = false, length = 2048)
    private String fullLink;

    @Column(name = "count")
    private int count;

    public ShortLink() {

    }

    public ShortLink (@NotNull String fullLink) {
        this.shortLink = "";
        this.fullLink = fullLink;
        this.count = 0;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getUri () {
        return uri;
    }

    public void setUri (String uri) {
        this.uri = uri;
    }

    public String getShortLink () {
        return shortLink;
    }

    public void setShortLink (String shortLink) {
        this.shortLink = shortLink;
    }

    public String getFullLink () {
        return fullLink;
    }

    public void setFullLink (String fullLink) {
        this.fullLink = fullLink;
    }

    public int getCount () {
        return count;
    }

    public void setCount (int count) {
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        ShortLink shortLink1 = (ShortLink) o;
        return Objects.equals (getId (), shortLink1.getId ()) &&
                Objects.equals (getUri (), shortLink1.getUri ()) &&
                Objects.equals (getShortLink (), shortLink1.getShortLink ()) &&
                Objects.equals (getFullLink (), shortLink1.getFullLink ()) &&
                Objects.equals (getCount (), shortLink1.getCount ());
    }

    @Override
    public int hashCode () {

        return Objects.hash (getId (), getUri (), getShortLink (), getFullLink (), getCount ());
    }

    @Override
    public String toString () {
        return "ShortLink{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", shortLink='" + shortLink + '\'' +
                ", fullLink='" + fullLink + '\'' +
                ", count=" + count +
                '}';
    }
}
