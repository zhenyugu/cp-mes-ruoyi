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
import com.cpmes.system.domain.vo.ProcessRouteVo;
import com.cpmes.system.domain.bo.ProcessRouteBo;
import com.cpmes.system.service.IProcessRouteService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 工艺路线
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/processRoute")
public class ProcessRouteController extends BaseController {

    private final IProcessRouteService iProcessRouteService;

    /**
     * 查询工艺路线列表
     */
    @SaCheckPermission("system:processRoute:list")
    @GetMapping("/list")
    public TableDataInfo<ProcessRouteVo> list(ProcessRouteBo bo, PageQuery pageQuery) {
        return iProcessRouteService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出工艺路线列表
     */
    @SaCheckPermission("system:processRoute:list")
    @Log(title = "工艺路线", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProcessRouteBo bo, HttpServletResponse response) {
        List<ProcessRouteVo> list = iProcessRouteService.queryList(bo);
        ExcelUtil.exportExcel(list, "工艺路线", ProcessRouteVo.class, response);
    }

    /**
     * 获取工艺路线详细信息
     *
     * @param processRouteId 主键
     */
    @SaCheckPermission("system:processRoute:list")
    @GetMapping("/{processRouteId}")
    public R<ProcessRouteVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long processRouteId) {
        return R.ok(iProcessRouteService.queryById(processRouteId));
    }

    /**
     * 新增工艺路线
     */
    @SaCheckPermission("system:processRoute:list")
    @Log(title = "工艺路线", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProcessRouteBo bo) {
        return toAjax(iProcessRouteService.insertByBo(bo));
    }

    /**
     * 修改工艺路线
     */
    @SaCheckPermission("system:processRoute:list")
    @Log(title = "工艺路线", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProcessRouteBo bo) {
        return toAjax(iProcessRouteService.updateByBo(bo));
    }

    /**
     * 删除工艺路线
     *
     * @param processRouteIds 主键串
     */
    @SaCheckPermission("system:processRoute:list")
    @Log(title = "工艺路线", businessType = BusinessType.DELETE)
    @DeleteMapping("/{processRouteIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] processRouteIds) {
        return toAjax(iProcessRouteService.deleteWithValidByIds(Arrays.asList(processRouteIds), true));
    }
}
