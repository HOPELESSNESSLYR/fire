package com.degson.fire.service.impl;

import java.util.List;
import com.degson.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.degson.fire.mapper.HrInformationboardMapper;
import com.degson.fire.domain.HrInformationboard;
import com.degson.fire.service.IHrInformationboardService;

/**
 * 制作信息牌Service业务层处理
 * 
 * @author yara
 * @date 2025-03-05
 */
@Service
public class HrInformationboardServiceImpl implements IHrInformationboardService 
{
    @Autowired
    private HrInformationboardMapper hrInformationboardMapper;

    /**
     * 查询制作信息牌
     * 
     * @param id 制作信息牌主键
     * @return 制作信息牌
     */
    @Override
    public HrInformationboard selectHrInformationboardById(Long id)
    {
        return hrInformationboardMapper.selectHrInformationboardById(id);
    }

    /**
     * 查询制作信息牌列表
     * 
     * @param hrInformationboard 制作信息牌
     * @return 制作信息牌
     */
    @Override
    public List<HrInformationboard> selectHrInformationboardList(HrInformationboard hrInformationboard)
    {
        return hrInformationboardMapper.selectHrInformationboardList(hrInformationboard);
    }

    /**
     * 新增制作信息牌
     * 
     * @param hrInformationboard 制作信息牌
     * @return 结果
     */
    @Override
    public int insertHrInformationboard(HrInformationboard hrInformationboard)
    {
        hrInformationboard.setCreateTime(DateUtils.getNowDate());
        return hrInformationboardMapper.insertHrInformationboard(hrInformationboard);
    }

    /**
     * 修改制作信息牌
     * 
     * @param hrInformationboard 制作信息牌
     * @return 结果
     */
    @Override
    public int updateHrInformationboard(HrInformationboard hrInformationboard)
    {
        hrInformationboard.setUpdateTime(DateUtils.getNowDate());
        return hrInformationboardMapper.updateHrInformationboard(hrInformationboard);
    }

    /**
     * 批量删除制作信息牌
     * 
     * @param ids 需要删除的制作信息牌主键
     * @return 结果
     */
    @Override
    public int deleteHrInformationboardByIds(Long[] ids)
    {
        return hrInformationboardMapper.deleteHrInformationboardByIds(ids);
    }

    /**
     * 删除制作信息牌信息
     * 
     * @param id 制作信息牌主键
     * @return 结果
     */
    @Override
    public int deleteHrInformationboardById(Long id)
    {
        return hrInformationboardMapper.deleteHrInformationboardById(id);
    }
}
