package com.cpmes.system.service;

import com.cpmes.system.domain.Product;
import com.cpmes.system.domain.vo.ProductVo;
import com.cpmes.system.domain.bo.ProductBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品Service接口
 *
 * @author cp-mes
 * @date 2024-01-08
 */
public interface IProductService {

    /**
     * 查询产品
     */
    ProductVo queryById(Long productId);

    /**
     * 查询产品列表
     */
    TableDataInfo<ProductVo> queryPageList(ProductBo bo, PageQuery pageQuery);

    /**
     * 查询产量统计
     */
    TableDataInfo<ProductVo> queryProductionStatisticsPageList(ProductBo bo, PageQuery pageQuery);

    /**
     * 查询产品列表
     */
    List<ProductVo> queryList(ProductBo bo);

    /**
     * 新增产品
     */
    Boolean insertByBo(ProductBo bo);

    /**
     * 修改产品
     */
    Boolean updateByBo(ProductBo bo);

    /**
     * 校验并批量删除产品信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
