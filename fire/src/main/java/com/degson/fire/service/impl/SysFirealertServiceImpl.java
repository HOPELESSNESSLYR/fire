package com.degson.fire.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.degson.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.degson.fire.mapper.SysFirealertMapper;
import com.degson.fire.domain.SysFirealert;
import com.degson.fire.service.ISysFirealertService;

/**
 * 预警报警Service业务层处理
 * 
 * @author yara
 * @date 2025-02-07
 */
@Service
public class SysFirealertServiceImpl implements ISysFirealertService 
{
    private static final Logger log = LoggerFactory.getLogger(SysFirealertServiceImpl.class);

    @Autowired
    private SysFirealertMapper sysFirealertMapper;

    /**
     * 查询预警报警
     * 
     * @param id 预警报警主键
     * @return 预警报警
     */
    @Override
    public SysFirealert selectSysFirealertById(Long id)
    {
        return sysFirealertMapper.selectSysFirealertById(id);
    }

    /**
     * 查询预警报警列表
     * 
     * @param sysFirealert 预警报警
     * @return 预警报警
     */
    @Override
    public List<SysFirealert> selectSysFirealertList(SysFirealert sysFirealert)
    {
        return sysFirealertMapper.selectSysFirealertList(sysFirealert);
    }

    /**
     * 新增预警报警
     * 
     * @param sysFirealert 预警报警
     * @return 结果
     */
    @Override
    public int insertSysFirealert(SysFirealert sysFirealert)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // 设置时间为当前时间
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 将小时设置为0
        calendar.set(Calendar.MINUTE, 0); // 将分钟设置为0
        calendar.set(Calendar.SECOND, 0); // 将秒设置为0
        calendar.set(Calendar.MILLISECOND, 0); // 将毫秒设置为0
        Date nowDate = calendar.getTime(); // 获取没有时间的日期

        sysFirealert.setAlertTime(nowDate);
        sysFirealert.setCreateTime(DateUtils.getNowDate());
        return sysFirealertMapper.insertSysFirealert(sysFirealert);
    }

    /**
     * 修改预警报警
     * 
     * @param sysFirealert 预警报警
     * @return 结果
     */
    @Override
    public int updateSysFirealert(SysFirealert sysFirealert)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // 设置时间为当前时间
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 将小时设置为0
        calendar.set(Calendar.MINUTE, 0); // 将分钟设置为0
        calendar.set(Calendar.SECOND, 0); // 将秒设置为0
        calendar.set(Calendar.MILLISECOND, 0); // 将毫秒设置为0
        Date nowDate = calendar.getTime(); // 获取没有时间的日期

//        Date alertDate = sysFirealert.getAlertTime();
//        if (alertDate != nowDate) {
//        }
        sysFirealert.setAlertTime(nowDate);
        sysFirealert.setUpdateTime(DateUtils.getNowDate());
//        log.info("修改预警报警：{}",sysFirealert);
//        return sysFirealertMapper.updateSysFirealert(sysFirealert);
        return sysFirealertMapper.updateLert(sysFirealert);
    }
    @Override
    public int updateSysFirealert2(SysFirealert sysFirealert)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // 设置时间为当前时间
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 将小时设置为0
        calendar.set(Calendar.MINUTE, 0); // 将分钟设置为0
        calendar.set(Calendar.SECOND, 0); // 将秒设置为0
        calendar.set(Calendar.MILLISECOND, 0); // 将毫秒设置为0
        Date nowDate = calendar.getTime(); // 获取没有时间的日期

        Date alertDate = sysFirealert.getAlertTime();

        if (alertDate != nowDate) {
            sysFirealert.setAlertTime(nowDate);
            sysFirealert.setUpdateTime(DateUtils.getNowDate());
        }
        return sysFirealertMapper.updateLert(sysFirealert);
    }


    /**vdf
     * 批量删除预警报警
     * 
     * @param ids 需要删除的预警报警主键
     * @return 结果
     */
    @Override
    public int deleteSysFirealertByIds(Long[] ids)
    {
        return sysFirealertMapper.deleteSysFirealertByIds(ids);
    }

    /**
     * 删除预警报警信息
     * 
     * @param id 预警报警主键
     * @return 结果
     */
    @Override
    public int deleteSysFirealertById(Long id)
    {
        return sysFirealertMapper.deleteSysFirealertById(id);
    }
}
