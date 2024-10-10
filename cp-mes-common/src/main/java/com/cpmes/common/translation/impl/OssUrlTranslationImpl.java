package com.cpmes.common.translation.impl;

import com.cpmes.common.annotation.TranslationType;
import com.cpmes.common.constant.TransConstant;
import com.cpmes.common.core.service.OssService;
import com.cpmes.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * OSS翻译实现
 *
 * @author cp-mes
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    public String translation(Object key, String other) {
        return ossService.selectUrlByIds(key.toString());
    }
}
