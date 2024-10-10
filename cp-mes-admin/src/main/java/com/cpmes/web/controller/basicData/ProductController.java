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
import com.cpmes.system.domain.vo.ProductVo;
import com.cpmes.system.domain.bo.ProductBo;
import com.cpmes.system.service.IProductService;
import com.cpmes.common.core.page.TableDataInfo;

/**
 * 产品
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/product")
public class ProductController extends BaseController {

    private final IProductService iProductService;

    /**
     * 查询产品列表
     */
    @SaCheckPermission("system:product:list")
    @GetMapping("/list")
    public TableDataInfo<ProductVo> list(ProductBo bo, PageQuery pageQuery) {
        return iProductService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询产量统计
     */
    @SaCheckPermission("system:product:list")
    @GetMapping("/productionStatistics")
    public TableDataInfo<ProductVo> listProductionStatistics(ProductBo bo, PageQuery pageQuery) {
        return iProductService.queryProductionStatisticsPageList(bo, pageQuery);
    }


    /**
     * 导出产品列表
     */
    @SaCheckPermission("system:product:list")
    @Log(title = "产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProductBo bo, HttpServletResponse response) {
        List<ProductVo> list = iProductService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品", ProductVo.class, response);
    }


    /**
     * 获取产品详细信息
     *
     * @param productId 主键
     */
    @SaCheckPermission("system:product:list")
    @GetMapping("/{productId}")
    public R<ProductVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long productId) {
        return R.ok(iProductService.queryById(productId));
    }

    /**
     * 新增产品
     */
    @SaCheckPermission("system:product:list")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProductBo bo) {
        return toAjax(iProductService.insertByBo(bo));
    }

    /**
     * 修改产品
     */
    @SaCheckPermission("system:product:list")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProductBo bo) {
        return toAjax(iProductService.updateByBo(bo));
    }

    /**
     * 删除产品
     *
     * @param productIds 主键串
     */
    @SaCheckPermission("system:product:list")
    @Log(title = "产品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] productIds) {
        return toAjax(iProductService.deleteWithValidByIds(Arrays.asList(productIds), true));
    }
}
