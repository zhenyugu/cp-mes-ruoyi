package com.cpmes.web.controller.produce;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

import cn.dev33.satoken.annotation.SaIgnore;
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
import com.cpmes.system.domain.vo.TaskVo;
import com.cpmes.system.domain.bo.TaskBo;
import com.cpmes.system.service.ITaskService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 任务
 *
 * @author cp-mes
 * @date 2024-01-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task")
public class TaskController extends BaseController {

    private final ITaskService iTaskService;

    /**
     * 查询任务列表
     */
    @SaCheckPermission("system:task:list")
    @GetMapping("/list")
    public TableDataInfo<TaskVo> list(TaskBo bo, PageQuery pageQuery) {
        return iTaskService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出任务列表
     */
    @SaCheckPermission("system:task:list")
    @Log(title = "任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TaskBo bo, HttpServletResponse response) {
        List<TaskVo> list = iTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "任务", TaskVo.class, response);
    }

    /**
     * 获取任务详细信息
     *
     * @param taskId 主键
     */
    @SaCheckPermission("system:task:list")
    @GetMapping("/{taskId}")
    public R<TaskVo> getInfo(@NotNull(message = "主键不能为空")
                             @PathVariable Long taskId) {
        return R.ok(iTaskService.queryById(taskId));
    }

    /**
     * 新增任务
     */
    @SaCheckPermission("system:task:list")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TaskBo bo) {
        return toAjax(iTaskService.insertByBo(bo));
    }

    /**
     * 修改任务
     */
    @SaCheckPermission("system:task:list")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TaskBo bo) {
        return toAjax(iTaskService.updateByBo(bo));
    }

    /**
     * 删除任务
     *
     * @param taskIds 主键串
     */
    @SaCheckPermission("system:task:list")
    @Log(title = "任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] taskIds) {
        return toAjax(iTaskService.deleteWithValidByIds(Arrays.asList(taskIds), true));
    }

    /**
     * 查询数据概览echars
     */
//    @SaCheckPermission("system:task:list")
    @SaIgnore
    @PostMapping("/overviewCharts")
    public R<Map<String, Object>> overviewCharts(@RequestBody TaskBo bo) {
        return R.ok(iTaskService.overviewCharts(bo));
    }

    /**
     * 查询数据看板
     */
    @SaIgnore
    @PostMapping("/dataDashboards")
    public R<Map<String, Object>> dataDashboards() {
        return R.ok(iTaskService.dataDashboards());
    }
}
