package com.cpmes.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.cpmes.common.core.controller.BaseController;
import com.cpmes.common.core.domain.R;
import com.cpmes.common.core.domain.model.RegisterBody;
import com.cpmes.system.service.ISysConfigService;
import com.cpmes.web.service.SysRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author cp-mes
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysRegisterController extends BaseController {

    private final SysRegisterService registerService;
    private final ISysConfigService configService;

    /**
     * 用户注册
     */
    @SaIgnore
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return R.fail("当前系统没有开启注册功能！");
        }
        registerService.register(user);
        return R.ok();
    }
}
