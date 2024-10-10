package com.cpmes.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cpmes.common.annotation.DataColumn;
import com.cpmes.common.annotation.DataPermission;
import com.cpmes.common.core.mapper.BaseMapperPlus;
import com.cpmes.demo.domain.TestDemo;
import com.cpmes.demo.domain.vo.TestDemoVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 测试单表Mapper接口
 *
 * @author cp-mes
 * @date 2021-07-26
 */
public interface TestDemoMapper extends BaseMapperPlus<TestDemoMapper, TestDemo, TestDemoVo> {

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    Page<TestDemoVo> customPageList(@Param("page") Page<TestDemo> page, @Param("ew") Wrapper<TestDemo> wrapper);

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    <P extends IPage<TestDemo>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<TestDemo> queryWrapper);

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    List<TestDemo> selectList(@Param(Constants.WRAPPER) Wrapper<TestDemo> queryWrapper);

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    int updateById(@Param(Constants.ENTITY) TestDemo entity);

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    int deleteBatchIds(@Param(Constants.COLL) Collection<?> idList);
}
