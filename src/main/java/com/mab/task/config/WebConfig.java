package com.mab.task.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@PropertySource({"classpath:messages.properties"})
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private DatabaseProperties databaseProperties;
	
    //step 1
    @Bean(name = "uat_datasource")
    public DataSource dataSource() {
    	System.out.println(databaseProperties.getUat().get("username"));
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
		   dataSource.setUrl(databaseProperties.getUat().get("url"));
	       dataSource.setUsername(databaseProperties.getUat().get("username"));
	       dataSource.setPassword(databaseProperties.getUat().get("password"));
	       dataSource.setDriverClassName(databaseProperties.getUat().get("driverClassName"));
	       Properties prop=new Properties();
	       prop.setProperty("useUnicode","true");
	       prop.setProperty("characterEncoding","utf8");
	       dataSource.setConnectionProperties(prop);
		return  dataSource;
    }

    //step 2
    @Bean(name = "uat_namedJdbcTemplate")
	public NamedParameterJdbcTemplate uat_namedJdbcTemplate(@Qualifier("uat_datasource") DataSource ds) {
	 return new NamedParameterJdbcTemplate(ds);
	}
	
    //step 2
	@Bean(name = "uat_jdbcTemplate")
	public JdbcTemplate uat_jdbcTemplate(@Qualifier("uat_datasource") DataSource ds) {
	 return new JdbcTemplate(ds);
	}

    //step 3
	@Bean(name="uat_platformTransactionManager")
	public PlatformTransactionManager txManager(){
	    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
	    return transactionManager;
	}
	@Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCheckExistence(true);
        templateResolver.setOrder(0);
        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }

    @Bean
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        
        
        return viewResolver;
    }
    
    @Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/").setViewName(viewName);
		registry.addRedirectViewController("/", "redirect:/task/taskAssignment");
    	
	}
}
