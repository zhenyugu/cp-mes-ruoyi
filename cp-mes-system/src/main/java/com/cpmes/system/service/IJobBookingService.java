package com.cpmes.system.service;

import com.cpmes.system.domain.JobBooking;
import com.cpmes.system.domain.vo.JobBookingVo;
import com.cpmes.system.domain.bo.JobBookingBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 报工Service接口
 *
 * @author cp-mes
 * @date 2024-01-10
 */
public interface IJobBookingService {

    /**
     * 查询报工
     */
    JobBookingVo queryById(Long jobBookingId);

    /**
     * 查询报工列表
     */
    TableDataInfo<JobBookingVo> queryPageList(JobBookingBo bo, PageQuery pageQuery);

    /**
     * 查询不良品项明细
     */
    TableDataInfo<JobBookingVo> defectDetails(JobBookingBo bo, PageQuery pageQuery);

    /**
     * 查询报工列表
     */
    List<JobBookingVo> queryList(JobBookingBo bo);

    /**
     * 新增报工
     */
    Boolean insertByBo(JobBookingBo bo);

    /**
     * 修改报工
     */
    Boolean updateByBo(JobBookingBo bo);

    /**
     * 校验并批量删除报工信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
