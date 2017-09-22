package com.skyworthdigital.upgrade.device;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by SDT13292 on 2017/3/14.
 */
@Table(name = "AreaInfoTaobao", onCreated = "")
public class AreaInfoTaobao {
    @Column(name = "id", isId = true, autoGen = false)
    private int id;
    @Column(name = "country")
    private String country;
    @Column(name = "countryId")
    private String countryId;
    @Column(name = "area")
    private String area;
    @Column(name = "areaId")
    private String areaId;
    @Column(name = "region")
    private String region;
    @Column(name = "regionId")
    private String regionId;
    @Column(name = "city")
    private String city;
    @Column(name = "cityId")
    private String cityId;
    @Column(name = "county")
    private String county;
    @Column(name = "countyId")
    private String countyId;
    @Column(name = "isp")
    private String isp;
    @Column(name = "ispId")
    private String ispId;
    @Column(name = "ip")
    private String ip;
    @Column(name = "createtime")
    private long createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getIspId() {
        return ispId;
    }

    public void setIspId(String ispId) {
        this.ispId = ispId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
