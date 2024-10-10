package com.cpmes.web.controller.basicData;

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
import com.cpmes.system.domain.vo.DefectVo;
import com.cpmes.system.domain.bo.DefectBo;
import com.cpmes.system.service.IDefectService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 不良项
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/defect")
public class DefectController extends BaseController {

    private final IDefectService iDefectService;

    /**
     * 查询不良项列表
     */
    @SaCheckPermission("system:defect:list")
    @GetMapping("/list")
    public TableDataInfo<DefectVo> list(DefectBo bo, PageQuery pageQuery) {
        return iDefectService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询不良项列表
     */
    @SaCheckPermission("system:defect:list")
    @GetMapping("/defectiveItems")
    public TableDataInfo<DefectVo> listDefectiveItems(DefectBo bo, PageQuery pageQuery) {
        return iDefectService.listDefectiveItems(bo, pageQuery);
    }

    /**
     * 导出不良项列表
     */
    @SaCheckPermission("system:defect:list")
    @Log(title = "不良项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(DefectBo bo, HttpServletResponse response) {
        List<DefectVo> list = iDefectService.listDefectiveItems(bo, new PageQuery()).getRows();
        ExcelUtil.exportExcel(list, "不良项", DefectVo.class, response);
    }

    /**
     * 获取不良项详细信息
     *
     * @param defectId 主键
     */
    @SaCheckPermission("system:defect:list")
    @GetMapping("/{defectId}")
    public R<DefectVo> getInfo(@NotNull(message = "主键不能为空")
                               @PathVariable Long defectId) {
        return R.ok(iDefectService.queryById(defectId));
    }

    /**
     * 新增不良项
     */
    @SaCheckPermission("system:defect:list")
    @Log(title = "不良项", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DefectBo bo) {
        return toAjax(iDefectService.insertByBo(bo));
    }

    /**
     * 修改不良项
     */
    @SaCheckPermission("system:defect:list")
    @Log(title = "不良项", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DefectBo bo) {
        return toAjax(iDefectService.updateByBo(bo));
    }

    /**
     * 删除不良项
     *
     * @param defectIds 主键串
     */
    @SaCheckPermission("system:defect:list")
    @Log(title = "不良项", businessType = BusinessType.DELETE)
    @DeleteMapping("/{defectIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] defectIds) {
        return toAjax(iDefectService.deleteWithValidByIds(Arrays.asList(defectIds), true));
    }
}
