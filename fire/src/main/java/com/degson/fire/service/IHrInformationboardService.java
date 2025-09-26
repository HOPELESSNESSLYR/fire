package com.degson.fire.service;

import java.util.List;
import com.degson.fire.domain.HrInformationboard;

/**
 * 制作信息牌Service接口
 * 
 * @author yara
 * @date 2025-03-05
 */
public interface IHrInformationboardService 
{
    /**
     * 查询制作信息牌
     * 
     * @param id 制作信息牌主键
     * @return 制作信息牌
     */
    public HrInformationboard selectHrInformationboardById(Long id);

    /**
     * 查询制作信息牌列表
     * 
     * @param hrInformationboard 制作信息牌
     * @return 制作信息牌集合
     */
    public List<HrInformationboard> selectHrInformationboardList(HrInformationboard hrInformationboard);

    /**
     * 新增制作信息牌
     * 
     * @param hrInformationboard 制作信息牌
     * @return 结果
     */
    public int insertHrInformationboard(HrInformationboard hrInformationboard);

    /**
     * 修改制作信息牌
     * 
     * @param hrInformationboard 制作信息牌
     * @return 结果
     */
    public int updateHrInformationboard(HrInformationboard hrInformationboard);

    /**
     * 批量删除制作信息牌
     * 
     * @param ids 需要删除的制作信息牌主键集合
     * @return 结果
     */
    public int deleteHrInformationboardByIds(Long[] ids);

    /**
     * 删除制作信息牌信息
     * 
     * @param id 制作信息牌主键
     * @return 结果
     */
    public int deleteHrInformationboardById(Long id);
}
