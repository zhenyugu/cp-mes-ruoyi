package com.cpmes.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cpmes.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cpmes.system.domain.bo.MaterialBo;
import com.cpmes.system.domain.vo.MaterialVo;
import com.cpmes.system.domain.Material;
import com.cpmes.system.mapper.MaterialMapper;
import com.cpmes.system.service.IMaterialService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物料Service业务层处理
 *
 * @author cp-mes
 * @date 2024-03-14
 */
@RequiredArgsConstructor
@Service
public class MaterialServiceImpl implements IMaterialService {

    private final MaterialMapper baseMapper;

    /**
     * 查询物料
     */
    @Override
    public MaterialVo queryById(Long materialId){
        return baseMapper.selectVoById(materialId);
    }

    /**
     * 查询物料列表
     */
    @Override
    public TableDataInfo<MaterialVo> queryPageList(MaterialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Material> lqw = buildQueryWrapper(bo);
        Page<MaterialVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询物料列表
     */
    @Override
    public List<MaterialVo> queryList(MaterialBo bo) {
        LambdaQueryWrapper<Material> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Material> buildQueryWrapper(MaterialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Material> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMaterialNumber()), Material::getMaterialNumber, bo.getMaterialNumber());
        lqw.like(StringUtils.isNotBlank(bo.getMaterialName()), Material::getMaterialName, bo.getMaterialName());
        lqw.eq(bo.getMaterialQuantity() != null, Material::getMaterialQuantity, bo.getMaterialQuantity());
        lqw.eq(StringUtils.isNotBlank(bo.getMaterialUnit()), Material::getMaterialUnit, bo.getMaterialUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecification()), Material::getSpecification, bo.getSpecification());
        lqw.eq(StringUtils.isNotBlank(bo.getMaterialAttribute()), Material::getMaterialAttribute, bo.getMaterialAttribute());
        lqw.eq(bo.getInventoryMax() != null, Material::getInventoryMax, bo.getInventoryMax());
        lqw.eq(bo.getInventoryMin() != null, Material::getInventoryMin, bo.getInventoryMin());
        lqw.eq(bo.getInventorySafe() != null, Material::getInventorySafe, bo.getInventorySafe());
        return lqw;
    }

    /**
     * 新增物料
     */
    @Override
    public Boolean insertByBo(MaterialBo bo) {
        Material add = BeanUtil.toBean(bo, Material.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMaterialId(add.getMaterialId());
        }
        return flag;
    }

    /**
     * 修改物料
     */
    @Override
    public Boolean updateByBo(MaterialBo bo) {
        Material update = BeanUtil.toBean(bo, Material.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Material entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除物料
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
