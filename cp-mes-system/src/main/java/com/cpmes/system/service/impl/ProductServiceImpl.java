package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.exception.ServiceException;
import com.cpmes.common.utils.DateUtils;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.ProcessRoute;
import com.cpmes.system.domain.Sheet;
import com.cpmes.system.domain.Task;
import com.cpmes.system.domain.vo.ColumnsVo;
import com.cpmes.system.mapper.SheetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.ProductBo;
import com.cpmes.system.domain.vo.ProductVo;
import com.cpmes.system.domain.Product;
import com.cpmes.system.mapper.ProductMapper;
import com.cpmes.system.service.IProductService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品Service业务层处理
 *
 * @author cp-mes
 * @date 2024-01-08
 */
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductMapper baseMapper;
    private final SheetMapper sheetMapper;

    /**
     * 查询产品
     */
    @Override
    public ProductVo queryById(Long productId) {
        return baseMapper.selectVoById(productId);
    }

    /**
     * 查询产品列表
     */
    @Override
    public TableDataInfo<ProductVo> queryPageList(ProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Product> lqw = buildQueryWrapper(bo);
        Page<ProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询产量统计
     */
    @Override
    public TableDataInfo<ProductVo> queryProductionStatisticsPageList(ProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Product> lqw = buildQueryWrapper(bo);
        Page<ProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<String> productNumbers = result.getRecords().stream().map(ProductVo::getProductNumber).collect(Collectors.toList());
        List<Sheet> sheetList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(productNumbers)) {
            sheetList = sheetMapper.selectList(new LambdaQueryWrapper<Sheet>()
                .in(Sheet::getProductNumber, productNumbers)
                .between(bo.getParams().get("reportStart") != null && bo.getParams().get("reportEnd") != null, Sheet::getPlanStart, bo.getParams().get("reportStart"), bo.getParams().get("reportEnd")));
        }
        // 找出属性 date 的最大值
        Date end = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, sheetList.stream()
            .map(Sheet::getPlanEnd)
            .max(Date::compareTo)
            .orElse(new Date())));

        // 找出属性 date 的最小值
        Date start = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, sheetList.stream()
            .map(Sheet::getPlanEnd)
            .min(Date::compareTo)
            .orElse(new Date())));

        if (bo.getParams().get("reportStart") != null && bo.getParams().get("reportEnd") != null) {
            start = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtils.parseDate(bo.getParams().get("reportStart"))));
            end = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtils.parseDate(bo.getParams().get("reportEnd"))));
        }
        while (start.before(end) || start.equals(end)) {
            for (ProductVo productVo : result.getRecords()) {
                Date finalStart = start;
                List<Sheet> currentSheet = sheetList.stream().filter(e -> (e.getPlanEnd().after(finalStart))
                    && (e.getPlanStart().before(DateUtils.addDays(finalStart, 1)))
                    && e.getProductNumber().equals(productVo.getProductNumber())).collect(Collectors.toList());
                ColumnsVo addColumn = new ColumnsVo();
                addColumn.setTimeLabel(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, start));
                addColumn.setPlannedQuantity(currentSheet.stream().mapToInt(Sheet::getPlannedQuantity).sum());
                addColumn.setGoodQuantity(currentSheet.stream().mapToInt(Sheet::getGoodQuantity).sum());
                addColumn.setDefectQuantity(currentSheet.stream().mapToInt(Sheet::getDefectQuantity).sum());
                List<ColumnsVo> columnsVos = productVo.getColumns();
                if (ObjectUtil.isEmpty(columnsVos)) {
                    columnsVos = new ArrayList<>();
                    ColumnsVo all = new ColumnsVo();
                    List<Sheet> currentAllSheet = sheetList.stream().filter(e -> e.getProductNumber().equals(productVo.getProductNumber())).collect(Collectors.toList());
                    all.setTimeLabel("合计");
                    all.setPlannedQuantity(currentAllSheet.stream().mapToInt(Sheet::getPlannedQuantity).sum());
                    all.setGoodQuantity(currentAllSheet.stream().mapToInt(Sheet::getGoodQuantity).sum());
                    all.setDefectQuantity(currentAllSheet.stream().mapToInt(Sheet::getDefectQuantity).sum());
                    columnsVos.add(all);
                }
                columnsVos.add(addColumn);
                productVo.setColumns(columnsVos);
            }
            start = DateUtils.addDays(start, 1);
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询产品列表
     */
    @Override
    public List<ProductVo> queryList(ProductBo bo) {
        LambdaQueryWrapper<Product> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Product> buildQueryWrapper(ProductBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Product> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getProductNumber()), Product::getProductNumber, bo.getProductNumber());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), Product::getProductName, bo.getProductName());
        lqw.eq(bo.getProductQuantity() != null, Product::getProductQuantity, bo.getProductQuantity());
        lqw.eq(StringUtils.isNotBlank(bo.getProductUnit()), Product::getProductUnit, bo.getProductUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecification()), Product::getSpecification, bo.getSpecification());
        lqw.eq(StringUtils.isNotBlank(bo.getProductAttribute()), Product::getProductAttribute, bo.getProductAttribute());
        lqw.eq(bo.getInventoryMax() != null, Product::getInventoryMax, bo.getInventoryMax());
        lqw.eq(bo.getInventoryMin() != null, Product::getInventoryMin, bo.getInventoryMin());
        lqw.eq(bo.getInventorySafe() != null, Product::getInventorySafe, bo.getInventorySafe());
        lqw.eq(StringUtils.isNotBlank(bo.getRouteId()), Product::getRouteId, bo.getRouteId());
        lqw.between(bo.getParams().get("startTime") != null && bo.getParams().get("endTime") != null, Product::getCreateTime, bo.getParams().get("startTime"), bo.getParams().get("endTime"));
        return lqw;
    }

    /**
     * 新增产品
     */
    @Override
    public Boolean insertByBo(ProductBo bo) {
        Product add = BeanUtil.toBean(bo, Product.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setProductId(add.getProductId());
        }
        return flag;
    }

    /**
     * 修改产品
     */
    @Override
    public Boolean updateByBo(ProductBo bo) {
        Product update = BeanUtil.toBean(bo, Product.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Product entity) {
        //TODO 做一些数据校验,如唯一约束
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<Product>().eq(Product::getProductNumber, entity.getProductNumber())
            .ne(ObjectUtil.isNotNull(entity.getProductId()), Product::getProductId, entity.getProductId()));
        if (exist) {
            throw new ServiceException("产品编号已存在");
        }
    }

    /**
     * 批量删除产品
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
