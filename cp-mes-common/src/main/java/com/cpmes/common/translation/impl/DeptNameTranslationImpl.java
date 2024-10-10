package com.cpmes.common.translation.impl;

import com.cpmes.common.annotation.TranslationType;
import com.cpmes.common.constant.TransConstant;
import com.cpmes.common.core.service.DeptService;
import com.cpmes.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 部门翻译实现
 *
 * @author cp-mes
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class DeptNameTranslationImpl implements TranslationInterface<String> {

    private final DeptService deptService;

    public String translation(Object key, String other) {
        return deptService.selectDeptNameByIds(key.toString());
    }
}
