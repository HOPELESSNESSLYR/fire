package com.degson.fire.domain.vo;

/**
 * Monthly summary per device group.
 * 中文说明：按设备组的月度汇总统计结果 VO。
 * 字段含义：
 * - deviceGroup：设备组（如 灭火器/消防栓/阀组间/水泵房）
 * - totalCount：该设备组的设备总数
 * - inspectedCount：本期内存在检查记录的设备数量
 * - uninspectedCount：本期内无检查记录的设备数量
 * - qualifiedCount：本期检查为“合格”的设备数量
 * - unqualifiedCount：本期检查为“不合格”的设备数量
 */
public class MonthlySummaryVO {
    private String deviceGroup;
    private Long totalCount;
    private Long inspectedCount;
    private Long uninspectedCount;
    private Long qualifiedCount;
    private Long unqualifiedCount;

    public String getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(String deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getInspectedCount() {
        return inspectedCount;
    }

    public void setInspectedCount(Long inspectedCount) {
        this.inspectedCount = inspectedCount;
    }

    public Long getUninspectedCount() {
        return uninspectedCount;
    }

    public void setUninspectedCount(Long uninspectedCount) {
        this.uninspectedCount = uninspectedCount;
    }

    public Long getQualifiedCount() {
        return qualifiedCount;
    }

    public void setQualifiedCount(Long qualifiedCount) {
        this.qualifiedCount = qualifiedCount;
    }

    public Long getUnqualifiedCount() {
        return unqualifiedCount;
    }

    public void setUnqualifiedCount(Long unqualifiedCount) {
        this.unqualifiedCount = unqualifiedCount;
    }
}


