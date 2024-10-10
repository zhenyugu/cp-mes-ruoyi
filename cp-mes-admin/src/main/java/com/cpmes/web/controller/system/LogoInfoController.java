package com.cpmes.web.controller.system;

import java.util.Arrays;

import com.cpmes.system.domain.bo.LogoInfoBo;
import com.cpmes.system.domain.vo.LogoInfoVo;
import com.cpmes.system.service.ILogoInfoService;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.cpmes.common.annotation.RepeatSubmit;
import com.cpmes.common.annotation.Log;
import com.cpmes.common.core.controller.BaseController;
import com.cpmes.common.core.domain.PageQuery;
import com.cpmes.common.core.domain.R;
import com.cpmes.common.core.validate.AddGroup;
import com.cpmes.common.enums.BusinessType;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * logo信息
 *
 * @author cp-mes
 * @date 2023-11-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/logo")
public class LogoInfoController extends BaseController {

    private final ILogoInfoService iLogoInfoService;

    /**
     * 查询logo信息列表
     */
//    @SaCheckPermission("system:logo:list")
    @GetMapping("/list")
    public TableDataInfo<LogoInfoVo> list(LogoInfoBo bo, PageQuery pageQuery) {
        return iLogoInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取logo信息详细信息
     *
     * @param id 主键
     */
//    @SaCheckPermission("system:logo:query")
    @GetMapping("/{id}")
    public R<LogoInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long id) {
        return R.ok(iLogoInfoService.queryById(id));
    }

    /**
     * 新增logo信息
     */
//    @SaCheckPermission("system:logo:add")
    @Log(title = "logo信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LogoInfoBo bo) {
        return toAjax(iLogoInfoService.insertByBo(bo));
    }

    /**
     * 删除logo信息
     *
     * @param ids 主键串
     */
//    @SaCheckPermission("system:logo:remove")
    @Log(title = "logo信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iLogoInfoService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
