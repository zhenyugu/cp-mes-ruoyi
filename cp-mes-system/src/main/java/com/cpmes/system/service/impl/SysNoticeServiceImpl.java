package com.cpmes.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cpmes.common.core.domain.PageQuery;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.system.domain.SysNotice;
import com.cpmes.system.domain.vo.SysNoticeVo;
import com.cpmes.system.mapper.SysNoticeMapper;
import com.cpmes.system.service.ISysNoticeService;
import com.cpmes.system.service.ISysOssService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author cp-mes
 */
@RequiredArgsConstructor
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

    private final SysNoticeMapper baseMapper;
    private final ISysOssService ossService;

    @Override
    public TableDataInfo<SysNoticeVo> selectPageNoticeList(SysNotice notice, PageQuery pageQuery) {
        LambdaQueryWrapper<SysNotice> lqw = new LambdaQueryWrapper<SysNotice>()
            .like(StringUtils.isNotBlank(notice.getNoticeTitle()), SysNotice::getNoticeTitle, notice.getNoticeTitle())
            .eq(StringUtils.isNotBlank(notice.getNoticeType()), SysNotice::getNoticeType, notice.getNoticeType())
            .like(StringUtils.isNotBlank(notice.getCreateBy()), SysNotice::getCreateBy, notice.getCreateBy())
            .orderByDesc(SysNotice::getReleaseTime);
        Page<SysNoticeVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<SysNoticeVo> temp = page.getRecords();
        for (SysNoticeVo vo :temp){
            vo.setNoticeContent("");
            if (ObjectUtil.isNotEmpty(vo.getCoverOssId())) {
                vo.setCover(ossService.getById(vo.getCoverOssId()));
            }
        }
        return TableDataInfo.build(page);
    }

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return baseMapper.selectById(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysNotice>()
            .like(StringUtils.isNotBlank(notice.getNoticeTitle()), SysNotice::getNoticeTitle, notice.getNoticeTitle())
            .eq(StringUtils.isNotBlank(notice.getNoticeType()), SysNotice::getNoticeType, notice.getNoticeType())
            .like(StringUtils.isNotBlank(notice.getCreateBy()), SysNotice::getCreateBy, notice.getCreateBy()));
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice) {
        return baseMapper.insert(notice);
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice) {
        return baseMapper.updateById(notice);
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId) {
        return baseMapper.deleteById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return baseMapper.deleteBatchIds(Arrays.asList(noticeIds));
    }
}
