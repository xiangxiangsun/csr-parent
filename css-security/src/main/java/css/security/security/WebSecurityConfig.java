package css.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //自定义JSON格式返回
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHander myAuthenticationFailHander;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers(new String[]{"/assets/**","/css/**","/dist/**","/images/**","/img/**","/js/**","/MP3/**","/plugins/**","/template/**","/**/favicon.ico"}).permitAll()//
                //前端页面所需axios请求
                .antMatchers(new String[]{"/dept/**","/menu/**","/pwd/**","/permission/**","/role/**","/user/**"}).permitAll()

                //任何请求链接的访问均需验证权限
//                .anyRequest().authenticated()

                .and()
                //对请求进行授权
                .authorizeRequests()
                //必须经过权限验证才能访问
                .anyRequest().access("@rbacService.hasPermission(request,authentication)")

                .and()
                // 设置登陆页
                .formLogin().loginPage("/login")
                //提交表单后执行，对应login的action
                .loginProcessingUrl("/login")
                //登录成功默认页面
                .successHandler(myAuthenticationSuccessHandler)
                //登录失败提示页
                .failureHandler(myAuthenticationFailHander)
                .failureUrl("/login?error")
                .permitAll()

                .and()
                // 配置被拦截时的处理  这个位置很关键
                .exceptionHandling()
                //添加无权限时的处理
                .accessDeniedHandler(myAccessDeniedHandler)

                .and()
                //添加 /logout访问点，能退出
                .logout()
                //退出后访问
                .logoutSuccessUrl("/login")

//                .and()
//                //自动登录
//                .rememberMe()

                .and()
                //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
                .csrf().disable();

        //开启跨域访问
        http.cors().disable();
        // 设置可以iframe访问，内部刷新用到的
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
//        web.ignoring().antMatchers("/static/assets/**","/static/css/**","/static/images/**","/static/img/**","/static/js/**","/static/plugins/**","/static/template/**","**.ico");
//        web.ignoring().antMatchers("/static/**");
    }

    @Autowired
    private AuthenticationProvider provider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

}
