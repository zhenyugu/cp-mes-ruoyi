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
import com.cpmes.system.domain.vo.ProcedureVo;
import com.cpmes.system.domain.bo.ProcedureBo;
import com.cpmes.system.service.IProcedureService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 工序
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/procedure")
public class ProcedureController extends BaseController {

    private final IProcedureService iProcedureService;

    /**
     * 查询工序列表
     */
    @SaCheckPermission("system:procedure:list")
    @GetMapping("/list")
    public TableDataInfo<ProcedureVo> list(ProcedureBo bo, PageQuery pageQuery) {
        return iProcedureService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出工序列表
     */
    @SaCheckPermission("system:procedure:list")
    @Log(title = "工序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProcedureBo bo, HttpServletResponse response) {
        List<ProcedureVo> list = iProcedureService.queryList(bo);
        ExcelUtil.exportExcel(list, "工序", ProcedureVo.class, response);
    }

    /**
     * 获取工序详细信息
     *
     * @param procedureId 主键
     */
    @SaCheckPermission("system:procedure:list")
    @GetMapping("/{procedureId}")
    public R<ProcedureVo> getInfo(@NotNull(message = "主键不能为空")
                                  @PathVariable Long procedureId) {
        return R.ok(iProcedureService.queryById(procedureId));
    }

    /**
     * 新增工序
     */
    @SaCheckPermission("system:procedure:list")
    @Log(title = "工序", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProcedureBo bo) {
        return toAjax(iProcedureService.insertByBo(bo));
    }

    /**
     * 修改工序
     */
    @SaCheckPermission("system:procedure:list")
    @Log(title = "工序", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProcedureBo bo) {
        return toAjax(iProcedureService.updateByBo(bo));
    }

    /**
     * 删除工序
     *
     * @param procedureIds 主键串
     */
    @SaCheckPermission("system:procedure:list")
    @Log(title = "工序", businessType = BusinessType.DELETE)
    @DeleteMapping("/{procedureIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] procedureIds) {
        return toAjax(iProcedureService.deleteWithValidByIds(Arrays.asList(procedureIds), true));
    }
}
