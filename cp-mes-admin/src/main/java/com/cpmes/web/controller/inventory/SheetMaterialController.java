package com.cpmes.web.controller.inventory;

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
import com.cpmes.system.domain.vo.SheetMaterialVo;
import com.cpmes.system.domain.bo.SheetMaterialBo;
import com.cpmes.system.service.ISheetMaterialService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 工单物料
 *
 * @author cp-mes
 * @date 2024-03-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sheetMaterial")
public class SheetMaterialController extends BaseController {

    private final ISheetMaterialService iSheetMaterialService;

    /**
     * 查询工单物料列表
     */
    @SaCheckPermission("system:sheetMaterial:list")
    @GetMapping("/list")
    public TableDataInfo<SheetMaterialVo> list(SheetMaterialBo bo, PageQuery pageQuery) {
        return iSheetMaterialService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出工单物料列表
     */
    @SaCheckPermission("system:sheetMaterial:export")
    @Log(title = "工单物料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SheetMaterialBo bo, HttpServletResponse response) {
        List<SheetMaterialVo> list = iSheetMaterialService.queryList(bo);
        ExcelUtil.exportExcel(list, "工单物料", SheetMaterialVo.class, response);
    }

    /**
     * 获取工单物料详细信息
     *
     * @param attachmentId 主键
     */
    @SaCheckPermission("system:sheetMaterial:query")
    @GetMapping("/{attachmentId}")
    public R<SheetMaterialVo> getInfo(@NotNull(message = "主键不能为空")
                                      @PathVariable Long attachmentId) {
        return R.ok(iSheetMaterialService.queryById(attachmentId));
    }

    /**
     * 新增工单物料
     */
    @SaCheckPermission("system:sheetMaterial:add")
    @Log(title = "工单物料", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SheetMaterialBo bo) {
        return toAjax(iSheetMaterialService.insertByBo(bo));
    }

    /**
     * 修改工单物料
     */
    @SaCheckPermission("system:sheetMaterial:edit")
    @Log(title = "工单物料", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SheetMaterialBo bo) {
        return toAjax(iSheetMaterialService.updateByBo(bo));
    }

    /**
     * 删除工单物料
     *
     * @param attachmentIds 主键串
     */
    @SaCheckPermission("system:sheetMaterial:remove")
    @Log(title = "工单物料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{attachmentIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] attachmentIds) {
        return toAjax(iSheetMaterialService.deleteWithValidByIds(Arrays.asList(attachmentIds), true));
    }
}
