package com.cpmes.common.translation.impl;

import com.cpmes.common.annotation.TranslationType;
import com.cpmes.common.constant.TransConstant;
import com.cpmes.common.core.service.DictService;
import com.cpmes.common.translation.TranslationInterface;
import com.cpmes.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 字典翻译实现
 *
 * @author cp-mes
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
public class DictTypeTranslationImpl implements TranslationInterface<String> {

    private final DictService dictService;

    public String translation(Object key, String other) {
        if (key instanceof String && StringUtils.isNotBlank(other)) {
            return dictService.getDictLabel(other, key.toString());
        }
        return null;
    }
}
