package com.skyworthdigital.upgrade.device;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by SDT13292 on 2017/7/10.
 */
@Table(name = "AreaPconline", onCreated = "")
public class AreaPconline {
    @Column(name = "id", isId = true, autoGen = false)
    private int id;
    @Column(name = "ip")
    private String ip;
    @Column(name = "pro")
    private String pro;
    @Column(name = "proCode")
    private String proCode;
    @Column(name = "city")
    private String city;
    @Column(name = "cityCode")
    private String cityCode;
    @Column(name = "region")
    private String region;
    @Column(name = "regionCode")
    private String regionCode;
    @Column(name = "addr")
    private String addr;
    @Column(name = "regionNames")
    private String regionNames;
    @Column(name = "")
    private String err;
    @Column(name = "createtime")
    private long createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRegionNames() {
        return regionNames;
    }

    public void setRegionNames(String regionNames) {
        this.regionNames = regionNames;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
