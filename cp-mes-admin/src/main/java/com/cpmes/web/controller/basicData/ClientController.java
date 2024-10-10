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
import com.cpmes.system.domain.vo.ClientVo;
import com.cpmes.system.domain.bo.ClientBo;
import com.cpmes.system.service.IClientService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 客户信息
 *
 * @author cp-mes
 * @date 2024-03-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/client")
public class ClientController extends BaseController {

    private final IClientService iClientService;

    /**
     * 查询客户信息列表
     */
    @SaCheckPermission("system:client:list")
    @GetMapping("/list")
    public TableDataInfo<ClientVo> list(ClientBo bo, PageQuery pageQuery) {
        return iClientService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出客户信息列表
     */
    @SaCheckPermission("system:client:list")
    @Log(title = "客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ClientBo bo, HttpServletResponse response) {
        List<ClientVo> list = iClientService.queryList(bo);
        ExcelUtil.exportExcel(list, "客户信息", ClientVo.class, response);
    }

    /**
     * 获取客户信息详细信息
     *
     * @param clientId 主键
     */
    @SaCheckPermission("system:client:list")
    @GetMapping("/{clientId}")
    public R<ClientVo> getInfo(@NotNull(message = "主键不能为空")
                               @PathVariable Long clientId) {
        return R.ok(iClientService.queryById(clientId));
    }

    /**
     * 新增客户信息
     */
    @SaCheckPermission("system:client:list")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ClientBo bo) {
        return toAjax(iClientService.insertByBo(bo));
    }

    /**
     * 修改客户信息
     */
    @SaCheckPermission("system:client:list")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ClientBo bo) {
        return toAjax(iClientService.updateByBo(bo));
    }

    /**
     * 删除客户信息
     *
     * @param clientIds 主键串
     */
    @SaCheckPermission("system:client:list")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clientIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] clientIds) {
        return toAjax(iClientService.deleteWithValidByIds(Arrays.asList(clientIds), true));
    }
}
