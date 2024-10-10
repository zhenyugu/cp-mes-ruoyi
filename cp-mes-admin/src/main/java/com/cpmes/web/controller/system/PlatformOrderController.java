package com.cpmes.web.controller.system;

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
import com.cpmes.system.domain.vo.PlatformOrderVo;
import com.cpmes.system.domain.bo.PlatformOrderBo;
import com.cpmes.system.service.IPlatformOrderService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 平台工单信息
 *
 * @author cp-mes
 * @date 2024-01-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/PlatformOrder")
public class PlatformOrderController extends BaseController {

    private final IPlatformOrderService iPlatformOrderService;

    /**
     * 查询平台工单信息列表
     */
    @SaCheckPermission("system:PlatformOrder:query")
    @GetMapping("/list")
    public TableDataInfo<PlatformOrderVo> list(PlatformOrderBo bo, PageQuery pageQuery) {
        return iPlatformOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出平台工单信息列表
     */
    @SaCheckPermission("system:PlatformOrder:query")
    @Log(title = "平台工单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PlatformOrderBo bo, HttpServletResponse response) {
        List<PlatformOrderVo> list = iPlatformOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "平台工单信息", PlatformOrderVo.class, response);
    }

    /**
     * 获取平台工单信息详细信息
     *
     * @param platformOrderId 主键
     */
    @SaCheckPermission("system:PlatformOrder:query")
    @GetMapping("/{platformOrderId}")
    public R<PlatformOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                      @PathVariable Long platformOrderId) {
        return R.ok(iPlatformOrderService.queryById(platformOrderId));
    }

    /**
     * 新增平台工单信息
     */
//    @SaCheckPermission("system:PlatformOrder:query")
    @Log(title = "平台工单信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PlatformOrderBo bo) {
        return toAjax(iPlatformOrderService.insertByBo(bo));
    }

    /**
     * 修改平台工单信息
     */
    @SaCheckPermission("system:PlatformOrder:query")
    @Log(title = "平台工单信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PlatformOrderBo bo) {
        return toAjax(iPlatformOrderService.updateByBo(bo));
    }

    /**
     * 删除平台工单信息
     *
     * @param platformOrderIds 主键串
     */
    @SaCheckPermission("system:PlatformOrder:query")
    @Log(title = "平台工单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{platformOrderIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] platformOrderIds) {
        return toAjax(iPlatformOrderService.deleteWithValidByIds(Arrays.asList(platformOrderIds), true));
    }
}
