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
import com.cpmes.system.domain.vo.MaterialVo;
import com.cpmes.system.domain.bo.MaterialBo;
import com.cpmes.system.service.IMaterialService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 物料
 *
 * @author cp-mes
 * @date 2024-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/material")
public class MaterialController extends BaseController {

    private final IMaterialService iMaterialService;

    /**
     * 查询物料列表
     */
    @SaCheckPermission("system:material:list")
    @GetMapping("/list")
    public TableDataInfo<MaterialVo> list(MaterialBo bo, PageQuery pageQuery) {
        return iMaterialService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物料列表
     */
    @SaCheckPermission("system:material:list")
    @Log(title = "物料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MaterialBo bo, HttpServletResponse response) {
        List<MaterialVo> list = iMaterialService.queryList(bo);
        ExcelUtil.exportExcel(list, "物料", MaterialVo.class, response);
    }

    /**
     * 获取物料详细信息
     *
     * @param materialId 主键
     */
    @SaCheckPermission("system:material:list")
    @GetMapping("/{materialId}")
    public R<MaterialVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long materialId) {
        return R.ok(iMaterialService.queryById(materialId));
    }

    /**
     * 新增物料
     */
    @SaCheckPermission("system:material:list")
    @Log(title = "物料", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MaterialBo bo) {
        return toAjax(iMaterialService.insertByBo(bo));
    }

    /**
     * 修改物料
     */
    @SaCheckPermission("system:material:list")
    @Log(title = "物料", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MaterialBo bo) {
        return toAjax(iMaterialService.updateByBo(bo));
    }

    /**
     * 删除物料
     *
     * @param materialIds 主键串
     */
    @SaCheckPermission("system:material:list")
    @Log(title = "物料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{materialIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] materialIds) {
        return toAjax(iMaterialService.deleteWithValidByIds(Arrays.asList(materialIds), true));
    }
}
