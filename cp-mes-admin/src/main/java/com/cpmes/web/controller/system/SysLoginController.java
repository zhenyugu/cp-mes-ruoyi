package com.cpmes.web.controller.system;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.collection.CollUtil;
import cn.dev33.satoken.annotation.SaIgnore;
import com.cpmes.common.constant.Constants;
import com.cpmes.common.constant.UserConstants;
import com.cpmes.common.core.domain.R;
import com.cpmes.common.core.domain.entity.SysMenu;
import com.cpmes.common.core.domain.entity.SysUser;
import com.cpmes.common.core.domain.model.LoginBody;
import com.cpmes.common.core.domain.model.LoginUser;
import com.cpmes.common.core.domain.model.SmsLoginBody;
import com.cpmes.common.helper.LoginHelper;
import com.cpmes.common.utils.MapstructUtils;
import com.cpmes.common.utils.StreamUtils;
import com.cpmes.common.utils.StringUtils;
import com.cpmes.common.utils.DateUtils;
import com.cpmes.common.helper.TenantHelper;
import com.cpmes.system.domain.bo.SysTenantBo;
import com.cpmes.system.domain.vo.RouterVo;
import com.cpmes.system.domain.vo.SysTenantVo;
import com.cpmes.system.service.ISysMenuService;
import com.cpmes.system.service.ISysTenantService;
import com.cpmes.system.service.ISysUserService;
import com.cpmes.web.service.SysLoginService;
import com.cpmes.system.utils.WeChatUtil;
import com.cpmes.web.vo.LoginTenantVo;
import com.cpmes.web.vo.LoginVo;
import com.cpmes.web.vo.TenantListVo;
import com.cpmes.web.vo.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录验证
 *
 * @author cp-mes
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysLoginController {

    private final SysLoginService loginService;
    private final ISysMenuService menuService;
    private final ISysUserService userService;
    private final ISysTenantService tenantService;

    /**
     * 小程序模拟登录方法
     *
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/mockLogin")
    public R<Map<String, Object>> mockLogin(@Validated @RequestBody LoginBody loginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.mockLogin("000000",loginBody.getUsername(), loginBody.getPassword(), loginBody.getWxCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 登录方法
     *
     * @param body 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/login")
    public R<LoginVo> login(@Validated @RequestBody LoginBody body) {
        LoginVo loginVo = new LoginVo();

        // 生成令牌
        String token = loginService.login(
            body.getUsername(), body.getPassword(),
            body.getCode(), body.getUuid());
        loginVo.setToken(token);
        return R.ok(loginVo);
    }

    /**
     * 短信登录(示例)
     *
     * @param body 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/smsLogin")
    public R<LoginVo> smsLogin(@Validated @RequestBody SmsLoginBody body) {
        LoginVo loginVo = new LoginVo();
        // 生成令牌
        String token = loginService.smsLogin(body.getTenantId(), body.getPhonenumber(), body.getSmsCode());
        loginVo.setToken(token);
        return R.ok(loginVo);
    }

    /**
     * 小程序登录(示例)
     *
     * @param xcxCode 小程序code
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/xcxLogin")
    public R<LoginVo> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
        LoginVo loginVo = new LoginVo();
        // 生成令牌
        String token = loginService.xcxLogin(xcxCode);
        loginVo.setToken(token);
        return R.ok(loginVo);
    }

    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 登录页面租户下拉框
     *
     * @return 租户列表
     */
    @SaIgnore
    @GetMapping("/tenant/list")
    public R<LoginTenantVo> tenantList(HttpServletRequest request) throws Exception {
        List<SysTenantVo> tenantList = tenantService.queryList(new SysTenantBo());
        List<TenantListVo> voList = MapstructUtils.convert(tenantList, TenantListVo.class);
        // 获取域名
        String host;
        String referer = request.getHeader("referer");
        if (StringUtils.isNotBlank(referer)) {
            // 这里从referer中取值是为了本地使用hosts添加虚拟域名，方便本地环境调试
            host = referer.split("//")[1].split("/")[0];
        } else {
            host = new URL(request.getRequestURL().toString()).getHost();
        }
        // 根据域名进行筛选
        List<TenantListVo> list = StreamUtils.filter(voList, vo ->
            StringUtils.equals(vo.getDomain(), host));
        // 返回对象
        LoginTenantVo vo = new LoginTenantVo();
        vo.setVoList(CollUtil.isNotEmpty(list) ? list : voList);
        vo.setTenantEnabled(TenantHelper.isEnable());
        return R.ok(vo);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<UserInfoVo> getInfo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUserId());
        if (loginUser.getUserId().equals(UserConstants.SUPER_ADMIN_ID)){
            user=userService.selectSuperAdmin(loginUser.getUserId());
        }
        userInfoVo.setUser(user);
        userInfoVo.setPermissions(loginUser.getMenuPermission());
        userInfoVo.setRoles(loginUser.getRolePermission());
        return R.ok(userInfoVo);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(LoginHelper.getUserId());
        return R.ok(menuService.buildMenus(menus));
    }
}
