package com.skyworthdigital.upgrade.device;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "deviceinfo")
public class DeviceInfo {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "wiredMac")
    private String wiredMac;
    @Column(name = "activeTime")
    private long activeTime;
    @Column(name = "hardVersion")
    private String hardVersion;
    @Column(name = "deviceNo")
    private String deviceNo;
    @Column(name = "orderNo")
    private String orderNo;
    @Column(name = "customerSnNo")
    private String customerSnNo;
    @Column(name = "originFactoryId")
    private String originFactoryId;
    @Column(name = "originDeviceTypeId")
    private String originDeviceTypeId;
    @Column(name = "vendorId")
    private String vendorId;
    @Column(name = "deviceTypeName")
    private String deviceTypeName;
    @Column(name = "softVersion")
    private String softVersion;
    @Column(name = "customerName")
    private String customerName;
    @Column(name = "snNo")
    private String snNo;
    @Column(name = "offerInfoId")
    private int offerInfoId;
    @Column(name = "area")
    private String area;
    @Column(name = "customerId")
    private String customerId;
    @Column(name = "originSnNO")
    private String originSnNO;
    @Column(name = "lastConnectTime")
    private long lastConnectTime;
    @Column(name = "network")
    private String network;
    @Column(name = "lastConnectIp")
    private String lastConnectIp;
    @Column(name = "wirelessMac")
    private String wirelessMac;
    @Column(name = "deviceTypeId")
    private String deviceTypeId;
    @Column(name = "boxNo")
    private String boxNo;
    @Column(name = "originCustomerId")
    private String originCustomerId;
    @Column(name = "deviceTypeNameDesc")
    private String deviceTypeNameDesc;

    public String getDeviceTypeNameDesc() {
        return deviceTypeNameDesc;
    }

    public void setDeviceTypeNameDesc(String deviceTypeNameDesc) {
        this.deviceTypeNameDesc = deviceTypeNameDesc;
    }

    public String getWiredMac() {
        return wiredMac;
    }

    public void setWiredMac(String wiredMac) {
        this.wiredMac = wiredMac;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public String getHardVersion() {
        return hardVersion;
    }

    public void setHardVersion(String hardVersion) {
        this.hardVersion = hardVersion;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerSnNo() {
        return customerSnNo;
    }

    public void setCustomerSnNo(String customerSnNo) {
        this.customerSnNo = customerSnNo;
    }

    public String getOriginFactoryId() {
        return originFactoryId;
    }

    public void setOriginFactoryId(String originFactoryId) {
        this.originFactoryId = originFactoryId;
    }

    public String getOriginDeviceTypeId() {
        return originDeviceTypeId;
    }

    public void setOriginDeviceTypeId(String originDeviceTypeId) {
        this.originDeviceTypeId = originDeviceTypeId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public int getOfferInfoId() {
        return offerInfoId;
    }

    public void setOfferInfoId(int offerInfoId) {
        this.offerInfoId = offerInfoId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOriginSnNO() {
        return originSnNO;
    }

    public void setOriginSnNO(String originSnNO) {
        this.originSnNO = originSnNO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastConnectTime() {
        return lastConnectTime;
    }

    public void setLastConnectTime(long lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getLastConnectIp() {
        return lastConnectIp;
    }

    public void setLastConnectIp(String lastConnectIp) {
        this.lastConnectIp = lastConnectIp;
    }

    public String getWirelessMac() {
        return wirelessMac;
    }

    public void setWirelessMac(String wirelessMac) {
        this.wirelessMac = wirelessMac;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getOriginCustomerId() {
        return originCustomerId;
    }

    public void setOriginCustomerId(String originCustomerId) {
        this.originCustomerId = originCustomerId;
    }

}
