package com.example.ssm_demo.enums;


public enum AreaRnkEnum {
    country("国家","0"),
    province("省份/直辖市","1"),
    city("市/州","2"),
    county("县/区","3"),
    town("镇/乡/街道","4"),
    village("村/社区","5");
    private String name;
    private String value;

    AreaRnkEnum() {
    }

    AreaRnkEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
