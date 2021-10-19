package com.yzm.security.config;

import com.yzm.security.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final SecPermissionEvaluator permissionEvaluator;

    public SecurityConfig(@Qualifier("secUserDetailsServiceImpl") UserDetailsService userDetailsService, DataSource dataSource, SecPermissionEvaluator permissionEvaluator) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.permissionEvaluator = permissionEvaluator;
    }

    /**
     * 密码编码器
     * passwordEncoder.encode是用来加密的,passwordEncoder.matches是用来解密的
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 使用“记住我”功能，默认使用InMemoryTokenRepositoryImpl将token存在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建persistent_logins数据库表，并把token存入表
     */
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //系统启动时会自动创建表，只需要在第一次系统启动时创建即可，这行代码在需要创建表时才启动
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * session 管理
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }

    /**
     * 配置用户
     * 指定默认从哪里获取认证用户的信息，即指定一个UserDetailsService接口的实现类
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 从内存创建用户
/*        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN")
                .authorities("CREATE", "UPDATE", "DELETE", "SELECT")
                .and()
                .withUser("yzm")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .authorities("SELECT")
        ;
*/
        // 从数据库获取用户
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(new SecAuthenticationProvider(userDetailsService, passwordEncoder()));
    }

    //配置资源权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                // 登录
                .formLogin()
                .loginPage(SysConstant.LOGIN_PAGE) //指定登录页的路径，默认/login
                .loginProcessingUrl("/login") //指定自定义form表单请求的路径(必须跟login.html中的form action=“url”一致)
//                .defaultSuccessUrl("/home", true) //登录成功跳转
//                .failureUrl("/user/login?error")//登录失败跳转
//                .failureUrl("/login/error")//查看登录失败原因
                .successHandler(new SecLoginSuccessHandler()) //登录成功后的处理
                .failureHandler(new SecLoginFailureHandler()) //登录失败后的处理
                .permitAll()
                .and()

                // 退出登录
                .logout()
//                .logoutUrl("/signout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl(SysConstant.LOGIN_PAGE + "?logout")
                .logoutSuccessHandler(new SecLogoutSuccessHandler())
                .permitAll()
                .and()

                // 自动登录
                .rememberMe()
                .tokenValiditySeconds(1800) // 有效时间，单位秒，默认30分钟
                //.tokenRepository(getPersistentTokenRepository()) // 默认保存在内存中，这里将token存到数据库
                //.userDetailsService(userDetailsService)
                .and()

                // 异常处理
                .exceptionHandling()
                // .authenticationEntryPoint(new SecAuthenticationEntryPoint())
                .accessDeniedHandler(new SecAccessDeniedHandler())
                .and()

                // 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
                .authorizeRequests()
                .antMatchers("/home", "/user/register", "/user/login", "/auth/login", "/invalid", "/kick").permitAll() //指定url放行
                .antMatchers("/swagger**/**", "/v2/**", "/webjars/**").permitAll() //swagger文档
                .antMatchers("/druid/**").permitAll() //查看SQL监控（druid）
                .antMatchers("/auth/getVerifyCode").permitAll() //验证码
                .anyRequest().authenticated() //其他任何请求都需要身份认证
                .and()

                // 添加过滤器 addFilterBefore() ，具有两个参数，作用是在参数二之前执行参数一设置的过滤器。
                // 验证码过滤器
                //.addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)

                // session 管理
                .sessionManagement()
                // 1.session 超时，默认60秒，即60秒内无操作就会过期
                // server.servlet.session.timeout=60
//                .invalidSessionUrl("/invalid")
                .invalidSessionStrategy(new SecSessionInvalidStrategy())
                // 2.session 并发控制：控制一个账号同一时刻最多能登录多少个
                .maximumSessions(1) // 限制最大登陆数
                .maxSessionsPreventsLogin(false) // 当达到最大值时，是否保留已经登录的用户 true：第一个浏览器登录的用户还在，第二个浏览器试图登录但无法登录；false：第一个浏览器登录的用户会提示被迫下线，第二个浏览器试图登录能成功登录
//                .expiredUrl("/invalid")
                .expiredSessionStrategy(new SecSessionExpiredStrategy()) // 当达到最大值时，旧用户被踢出后的操作
                // 3.手动使Session立即失效
                .sessionRegistry(sessionRegistry())
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

}
