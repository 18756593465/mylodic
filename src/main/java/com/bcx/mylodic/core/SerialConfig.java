package com.bcx.mylodic.core;

import java.io.Serializable;

public class SerialConfig implements Serializable {

    private static final long  serialVersionUID = 2424354690153546422L;

    /**
     * 序列化配置
     */
    private Configuration configuration;

    /**
     * 上次更新时间
     */
    private String lastUpdateTime;

    /**
     * 最近一次的序列化值
     */
    private String value;


    /**
     * 序列化代码
     */
    private String code;


    public Configuration getConfiguration() {
        return configuration;
    }

    public SerialConfig setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public SerialConfig setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SerialConfig setValue(String value) {
        this.value = value;
        return this;
    }

    public String getCode() {
        return code;
    }

    public SerialConfig setCode(String code) {
        this.code = code;
        return this;
    }
}
