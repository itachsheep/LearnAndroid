package com.skyworthdigital.upgrade.upload;

import com.skyworthdigital.upgrade.db.AreaInfoTaobao;

public class AreaInfoTaobaoResult {

    // {"code":0,
    // "data":
    // {"country":"\u4e2d\u56fd",
    // "country_id":"CN",
    // "area":"\u534e\u5357",
    // "area_id":"800000",
    // "region":"\u5e7f\u4e1c\u7701",
    // "region_id":"440000",
    // "city":"\u6df1\u5733\u5e02",
    // "city_id":"440300",
    // "county":"",
    // "county_id":"-1",
    // "isp":"\u7535\u4fe1",
    // "isp_id":"100017",
    // "ip":"119.123.113.48"}
    // }

    private int code;
    private AreaInfoTaobao data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public AreaInfoTaobao getData() {
        return data;
    }

    public void setData(AreaInfoTaobao data) {
        this.data = data;
    }
}
