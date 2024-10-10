package com.cpmes.system.service;

import com.cpmes.system.domain.Task;
import com.cpmes.system.domain.vo.TaskVo;
import com.cpmes.system.domain.bo.TaskBo;
import com.cpmes.common.core.page.TableDataInfo;
import com.cpmes.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 任务Service接口
 *
 * @author cp-mes
 * @date 2024-01-11
 */
public interface ITaskService {

    /**
     * 查询任务
     */
    TaskVo queryById(Long taskId);

    /**
     * 查询任务列表
     */
    TableDataInfo<TaskVo> queryPageList(TaskBo bo, PageQuery pageQuery);

    /**
     * 查询任务列表
     */
    List<TaskVo> queryList(TaskBo bo);

    /**
     * 新增任务
     */
    Boolean insertByBo(TaskBo bo);

    /**
     * 修改任务
     */
    Boolean updateByBo(TaskBo bo);

    /**
     * 校验并批量删除任务信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 数据概览界面charts
     */
    Map<String,Object> overviewCharts(TaskBo bo);

    /**
     * 数据看板接口
     */
    Map<String,Object> dataDashboards();
}
