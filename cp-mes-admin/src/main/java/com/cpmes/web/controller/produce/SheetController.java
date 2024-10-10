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
import com.cpmes.system.domain.vo.SheetVo;
import com.cpmes.system.domain.bo.SheetBo;
import com.cpmes.system.service.ISheetService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 工单
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sheet")
public class SheetController extends BaseController {

    private final ISheetService iSheetService;

    /**
     * 查询工单列表
     */
    @SaCheckPermission("system:sheet:list")
    @GetMapping("/list")
    public TableDataInfo<SheetVo> list(SheetBo bo, PageQuery pageQuery) {
        return iSheetService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询工单列表
     */
    @SaCheckPermission("system:sheet:list")
    @GetMapping("/allList")
    public R<List<SheetVo>> allList(SheetBo bo) {
        return R.ok(iSheetService.queryList(bo));
    }

    /**
     * 导出工单列表
     */
    @SaCheckPermission("system:sheet:list")
    @Log(title = "工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SheetBo bo, HttpServletResponse response) {
        List<SheetVo> list = iSheetService.queryList(bo);
        ExcelUtil.exportExcel(list, "工单", SheetVo.class, response);
    }

    /**
     * 获取工单详细信息
     *
     * @param sheetId 主键
     */
    @SaCheckPermission("system:sheet:list")
    @GetMapping("/{sheetId}")
    public R<SheetVo> getInfo(@NotNull(message = "主键不能为空")
                              @PathVariable Long sheetId) {
        return R.ok(iSheetService.queryById(sheetId));
    }

    /**
     * 获取工单详细信息
     *
     * @param sheetId 主键
     */
    @SaCheckPermission("system:sheet:list")
    @GetMapping("/mobile/{sheetId}")
    public R<SheetVo> queryByIdMobile(@NotNull(message = "主键不能为空")
                              @PathVariable Long sheetId) {
        return R.ok(iSheetService.queryByIdMobile(sheetId));
    }

    /**
     * 获取工单详细信息
     *
     * @param sheetNumber 工单编号
     */
    @SaCheckPermission("system:sheet:list")
    @GetMapping("/sheetNumber")
    public R<SheetVo> getInfoBySheetNumber(String sheetNumber) {
        return R.ok(iSheetService.queryBySheetNumber(sheetNumber));
    }

    /**
     * 新增工单
     */
    @SaCheckPermission("system:sheet:list")
    @Log(title = "工单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SheetBo bo) {
        return toAjax(iSheetService.insertByBo(bo));
    }

    /**
     * 修改工单
     */
    @SaCheckPermission("system:sheet:list")
    @Log(title = "工单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SheetBo bo) {
        return toAjax(iSheetService.updateByBo(bo));
    }

    /**
     * 删除工单
     *
     * @param sheetIds 主键串
     */
    @SaCheckPermission("system:sheet:list")
    @Log(title = "工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sheetIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] sheetIds) {
        return toAjax(iSheetService.deleteWithValidByIds(Arrays.asList(sheetIds), true));
    }
}
