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
import com.cpmes.system.domain.vo.AttachmentInfoVo;
import com.cpmes.system.domain.bo.AttachmentInfoBo;
import com.cpmes.system.service.IAttachmentInfoService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 备件库管理
 *
 * @author cp-mes
 * @date 2023-09-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/attachment")
public class AttachmentInfoController extends BaseController {

    private final IAttachmentInfoService iAttachmentInfoService;

    /**
     * 查询备件库管理列表
     */
    @SaCheckPermission("system:attachment:list")
    @GetMapping("/list")
    public TableDataInfo<AttachmentInfoVo> list(AttachmentInfoBo bo, PageQuery pageQuery) {
        return iAttachmentInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出备件库管理列表
     */
    @SaCheckPermission("system:attachment:list")
    @Log(title = "备件库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AttachmentInfoBo bo, HttpServletResponse response) {
        List<AttachmentInfoVo> list = iAttachmentInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "备件库管理", AttachmentInfoVo.class, response);
    }

    /**
     * 获取备件库管理详细信息
     *
     * @param attachmentId 主键
     */
    @SaCheckPermission("system:attachment:list")
    @GetMapping("/{attachmentId}")
    public R<AttachmentInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long attachmentId) {
        return R.ok(iAttachmentInfoService.queryById(attachmentId));
    }

    /**
     * 新增备件库管理
     */
    @SaCheckPermission("system:attachment:list")
    @Log(title = "备件库管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AttachmentInfoBo bo) {
        return toAjax(iAttachmentInfoService.insertByBo(bo));
    }

    /**
     * 修改备件库管理
     */
    @SaCheckPermission("system:attachment:list")
    @Log(title = "备件库管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AttachmentInfoBo bo) {
        return toAjax(iAttachmentInfoService.updateByBo(bo));
    }

    /**
     * 删除备件库管理
     *
     * @param attachmentIds 主键串
     */
    @SaCheckPermission("system:attachment:list")
    @Log(title = "备件库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{attachmentIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] attachmentIds) {
        return toAjax(iAttachmentInfoService.deleteWithValidByIds(Arrays.asList(attachmentIds), true));
    }
}

