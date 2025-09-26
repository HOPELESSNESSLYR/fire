package com.degson.fire.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.degson.common.annotation.Excel;
import com.degson.common.core.domain.BaseEntity;

/**
 * 制作信息牌对象 hr_informationboard
 * 
 * @author yara
 * @date 2025-03-05
 */
public class HrInformationboard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 工号 */
    @Excel(name = "工号")
    private String jobNumber;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 公民身份证号 */
    private String IDnumber;

    /** 岗位 */
    @Excel(name = "岗位")
    private String job;

    /** 抬头 */
    @Excel(name = "抬头")
    private String company;

    /** 入职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date liveDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setJobNumber(String jobNumber) 
    {
        this.jobNumber = jobNumber;
    }

    public String getJobNumber() 
    {
        return jobNumber;
    }
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIDnumber(String IDnumber) 
    {
        this.IDnumber = IDnumber;
    }

    public String getIDnumber() 
    {
        return IDnumber;
    }
    public void setJob(String job) 
    {
        this.job = job;
    }

    public String getJob() 
    {
        return job;
    }
    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }
    public void setLiveDate(Date liveDate) 
    {
        this.liveDate = liveDate;
    }

    public Date getLiveDate() 
    {
        return liveDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jobNumber", getJobNumber())
            .append("department", getDepartment())
            .append("name", getName())
            .append("IDnumber", getIDnumber())
            .append("job", getJob())
            .append("company", getCompany())
            .append("liveDate", getLiveDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
