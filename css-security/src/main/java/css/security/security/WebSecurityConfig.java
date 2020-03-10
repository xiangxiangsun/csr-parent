package css.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //自定义JSON格式返回
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHander myAuthenticationFailHander;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers(new String[]{"/assets/**","/css/**","/dist/**","/images/**","/img/**","/js/**","/MP3/**","/plugins/**","/template/**","/**/favicon.ico"}).permitAll()//
//                .antMatchers("/css/*.css").permitAll() //登陆所需资源
                .antMatchers(new String[]{"/dept/**","/menu/**","/pwd/**","/permission/**","/role/**","/user/**","/index"}).permitAll()


                //下面三行设置后   访问都需要登录，除放行外请求都将被拦截


                .and()
                // 设置登陆页
                .formLogin() //form提交登陆
                .loginPage("/login")  //登陆页
                // 设置登陆成功页
                //.defaultSuccessUrl("/index")
                //.successForwardUrl("/index")
                //登录成功默认页面
                .successHandler(myAuthenticationSuccessHandler)
                //登录失败提示页
                .failureHandler(myAuthenticationFailHander)
                .failureUrl("/login?error")
                .permitAll()
                .and()
                //对请求进行授权
                .authorizeRequests()
                //任何请求链接的访问均需验证权限
//                .anyRequest().authenticated()

                //必须经过验证才能访问
                .anyRequest().access("@rbacService.hasPermission(request,authentication)")
                // 自定义登陆用户名和密码参数，默认为username和password
//                .usernameParameter("username")
//                .passwordParameter("password")


                .and()
                .logout()  //添加 /logout访问点，能退出
                .logoutSuccessUrl("/login")  //退出后访问
                .and().rememberMe();    //自动登录

        //开启跨域访问
        http.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
        // 设置可以iframe访问，内部刷新用到的
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/static/assets/**","/static/css/**","/static/images/**","/static/img/**","/static/js/**","/static/plugins/**","/static/template/**","**.ico");
//        web.ignoring().antMatchers("/static/**");
    }

    @Autowired
    private AuthenticationProvider provider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

}
