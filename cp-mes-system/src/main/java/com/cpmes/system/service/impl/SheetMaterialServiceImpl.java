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
import com.cpmes.system.domain.bo.SheetMaterialBo;
import com.cpmes.system.domain.vo.SheetMaterialVo;
import com.cpmes.system.domain.SheetMaterial;
import com.cpmes.system.mapper.SheetMaterialMapper;
import com.cpmes.system.service.ISheetMaterialService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 工单物料Service业务层处理
 *
 * @author cp-mes
 * @date 2024-03-29
 */
@RequiredArgsConstructor
@Service
public class SheetMaterialServiceImpl implements ISheetMaterialService {

    private final SheetMaterialMapper baseMapper;

    /**
     * 查询工单物料
     */
    @Override
    public SheetMaterialVo queryById(Long attachmentId){
        return baseMapper.selectVoById(attachmentId);
    }

    /**
     * 查询工单物料列表
     */
    @Override
    public TableDataInfo<SheetMaterialVo> queryPageList(SheetMaterialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SheetMaterial> lqw = buildQueryWrapper(bo);
        Page<SheetMaterialVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询工单物料列表
     */
    @Override
    public List<SheetMaterialVo> queryList(SheetMaterialBo bo) {
        LambdaQueryWrapper<SheetMaterial> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SheetMaterial> buildQueryWrapper(SheetMaterialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SheetMaterial> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getAttachmentCode()), SheetMaterial::getAttachmentCode, bo.getAttachmentCode());
        lqw.like(StringUtils.isNotBlank(bo.getAttachmentName()), SheetMaterial::getAttachmentName, bo.getAttachmentName());
        lqw.eq(StringUtils.isNotBlank(bo.getModel()), SheetMaterial::getModel, bo.getModel());
        lqw.eq(bo.getQuantity() != null, SheetMaterial::getQuantity, bo.getQuantity());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), SheetMaterial::getUnit, bo.getUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getPurveyorId()), SheetMaterial::getPurveyorId, bo.getPurveyorId());
        lqw.like(StringUtils.isNotBlank(bo.getPurveyorName()), SheetMaterial::getPurveyorName, bo.getPurveyorName());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), SheetMaterial::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getSheetId()), SheetMaterial::getSheetId, bo.getSheetId());
        lqw.eq(bo.getDosage() != null, SheetMaterial::getDosage, bo.getDosage());
        return lqw;
    }

    /**
     * 新增工单物料
     */
    @Override
    public Boolean insertByBo(SheetMaterialBo bo) {
        SheetMaterial add = BeanUtil.toBean(bo, SheetMaterial.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAttachmentId(add.getAttachmentId());
        }
        return flag;
    }

    /**
     * 修改工单物料
     */
    @Override
    public Boolean updateByBo(SheetMaterialBo bo) {
        SheetMaterial update = BeanUtil.toBean(bo, SheetMaterial.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SheetMaterial entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除工单物料
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
