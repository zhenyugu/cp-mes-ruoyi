package com.cpmes.web.controller.produce;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.cpmes.common.annotation.RepeatSubmit;
import com.cpmes.common.annotation.Log;
import com.cpmes.common.core.controller.BaseController;
import com.cpmes.common.core.domain.PageQuery;
import com.cpmes.common.core.domain.R;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.core.validate.EditGroup;
import com.cpmes.common.enums.BusinessType;
import com.cpmes.common.utils.poi.ExcelUtil;
import com.cpmes.system.domain.vo.JobBookingVo;
import com.cpmes.system.domain.bo.JobBookingBo;
import com.cpmes.system.service.IJobBookingService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 报工
 *
 * @author cp-mes
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/jobBooking")
public class JobBookingController extends BaseController {

    private final IJobBookingService iJobBookingService;

    /**
     * 查询报工列表
     */
    @SaCheckPermission("system:jobBooking:list")
    @GetMapping("/list")
    public TableDataInfo<JobBookingVo> list(JobBookingBo bo, PageQuery pageQuery) {
        return iJobBookingService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询不良品项明细
     */
    @SaCheckPermission("system:jobBooking:list")
    @GetMapping("/defectDetails")
    public TableDataInfo<JobBookingVo> defectDetails(JobBookingBo bo, PageQuery pageQuery) {
        return iJobBookingService.defectDetails(bo, pageQuery);
    }

    /**
     * 导出报工列表
     */
    @SaCheckPermission("system:jobBooking:list")
    @Log(title = "报工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(JobBookingBo bo, HttpServletResponse response) {
        List<JobBookingVo> list = iJobBookingService.queryList(bo);
        ExcelUtil.exportExcel(list, "报工", JobBookingVo.class, response);
    }

    /**
     * 获取报工详细信息
     *
     * @param jobBookingId 主键
     */
    @SaCheckPermission("system:jobBooking:list")
    @GetMapping("/{jobBookingId}")
    public R<JobBookingVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable Long jobBookingId) {
        return R.ok(iJobBookingService.queryById(jobBookingId));
    }

    /**
     * 新增报工
     */
    @SaCheckPermission("system:jobBooking:list")
    @Log(title = "报工", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody JobBookingBo bo) {
        return toAjax(iJobBookingService.insertByBo(bo));
    }

    /**
     * 修改报工
     */
    @SaCheckPermission("system:jobBooking:list")
    @Log(title = "报工", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody JobBookingBo bo) {
        return toAjax(iJobBookingService.updateByBo(bo));
    }

    /**
     * 删除报工
     *
     * @param jobBookingIds 主键串
     */
    @SaCheckPermission("system:jobBooking:list")
    @Log(title = "报工", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobBookingIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] jobBookingIds) {
        return toAjax(iJobBookingService.deleteWithValidByIds(Arrays.asList(jobBookingIds), true));
    }
}
