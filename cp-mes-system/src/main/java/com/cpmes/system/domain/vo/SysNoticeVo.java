package com.cpmes.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cpmes.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Author cp-mes
 * @Date 2023/3/21 14:39
 */
@Data
@ExcelIgnoreUnannotated
public class SysNoticeVo extends BaseEntity {
    /**
     * 公告ID
     */
    @TableId(value = "notice_id")
    private Long noticeId;

    /**
     * 封面图片
     */
    private Long coverOssId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    /**
     * 发布时间
     */
    private String releaseTime;

    /**
     * 段落头
     */
    private String header;

    /**
     * 备注
     */
    private String remark;

    /**
     * 封面
     */
    private SysOssVo cover;
}
