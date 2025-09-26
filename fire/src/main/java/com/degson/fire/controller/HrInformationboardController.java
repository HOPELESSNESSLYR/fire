package com.degson.fire.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.degson.common.annotation.Log;
import com.degson.common.core.controller.BaseController;
import com.degson.common.core.domain.AjaxResult;
import com.degson.common.enums.BusinessType;
import com.degson.fire.domain.HrInformationboard;
import com.degson.fire.service.IHrInformationboardService;
import com.degson.common.utils.poi.ExcelUtil;
import com.degson.common.core.page.TableDataInfo;

/**
 * 制作信息牌Controller
 * 
 * @author yara
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/board/board")
public class HrInformationboardController extends BaseController
{
    @Autowired
    private IHrInformationboardService hrInformationboardService;

    /**
     * 查询制作信息牌列表
     */
    @PreAuthorize("@ss.hasPermi('board:board:list')")
    @GetMapping("/list")
    public TableDataInfo list(HrInformationboard hrInformationboard)
    {
        startPage();
        List<HrInformationboard> list = hrInformationboardService.selectHrInformationboardList(hrInformationboard);
        return getDataTable(list);
    }

    /**
     * 导出制作信息牌列表
     */
    @PreAuthorize("@ss.hasPermi('board:board:export')")
    @Log(title = "制作信息牌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HrInformationboard hrInformationboard)
    {
        List<HrInformationboard> list = hrInformationboardService.selectHrInformationboardList(hrInformationboard);
        ExcelUtil<HrInformationboard> util = new ExcelUtil<HrInformationboard>(HrInformationboard.class);
        util.exportExcel(response, list, "制作信息牌数据");
    }

    /**
     * 获取制作信息牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('board:board:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(hrInformationboardService.selectHrInformationboardById(id));
    }

    /**
     * 新增制作信息牌
     */
    @PreAuthorize("@ss.hasPermi('board:board:add')")
    @Log(title = "制作信息牌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HrInformationboard hrInformationboard)
    {
        return toAjax(hrInformationboardService.insertHrInformationboard(hrInformationboard));
    }

    /**
     * 修改制作信息牌
     */
    @PreAuthorize("@ss.hasPermi('board:board:edit')")
    @Log(title = "制作信息牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HrInformationboard hrInformationboard)
    {
        return toAjax(hrInformationboardService.updateHrInformationboard(hrInformationboard));
    }

    /**
     * 删除制作信息牌
     */
    @PreAuthorize("@ss.hasPermi('board:board:remove')")
    @Log(title = "制作信息牌", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hrInformationboardService.deleteHrInformationboardByIds(ids));
    }
}
