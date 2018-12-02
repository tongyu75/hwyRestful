package com.czz.hwy.bean.user;

public class EmployeeConfig {
    private int id;

    // 监督专家数量
    private int expert;

    // 财务类数量
    private int finance;

    // 技术类数量
    private int technology;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpert() {
        return expert;
    }

    public void setExpert(int expert) {
        this.expert = expert;
    }

    public int getFinance() {
        return finance;
    }

    public void setFinance(int finance) {
        this.finance = finance;
    }

    public int getTechnology() {
        return technology;
    }

    public void setTechnology(int technology) {
        this.technology = technology;
    }

}