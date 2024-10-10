package com.cpmes.demo.mapper;

import com.cpmes.common.annotation.DataColumn;
import com.cpmes.common.annotation.DataPermission;
import com.cpmes.common.core.mapper.BaseMapperPlus;
import com.cpmes.demo.domain.TestTree;
import com.cpmes.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author cp-mes
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTreeMapper, TestTree, TestTreeVo> {

}
