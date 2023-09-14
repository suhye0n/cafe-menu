package spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class RootContextConfig {

    @Bean(name = "mainHikariConfig")
    public HikariConfig getMainHikariConfig () {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:3306/test");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("1111");
        return hikariConfig;
    }

    @Bean(name = "mainHikariDataSource")
    public HikariDataSource getMainHikariDataSource () {
        HikariDataSource hikariDataSource = new HikariDataSource(getMainHikariConfig());
        return hikariDataSource;
    }

    private class Configuration extends org.apache.ibatis.session.Configuration {
        private Configuration() {
            super();
            super.setCacheEnabled(true);
            super.setLazyLoadingEnabled(false);
            super.setMultipleResultSetsEnabled(true);
            super.setUseColumnLabel(true);
            super.setUseGeneratedKeys(false);
            super.setDefaultExecutorType(ExecutorType.SIMPLE);
            super.setDefaultStatementTimeout(25000);
            super.setCallSettersOnNulls(true);

            TypeAliasRegistry typeAliasRegistry = super.getTypeAliasRegistry();
        }
    }
    private Configuration configuration = new Configuration();

    @Bean(name = "mainMapperScannerConfigurer")
    public MapperScannerConfigurer getMainMapperScannerConfigurer () {
        MapperScannerConfigurer mapperScannerConfigurer = null;
        mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("kr.co.edu");
        mapperScannerConfigurer.setAnnotationClass(org.apache.ibatis.annotations.Mapper.class);//Annotation을 지정해 줘야 service interface 를 Mapper라고 인식하지 않음
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("mainSqlSessionTemplate");
        return mapperScannerConfigurer;
    }

    @Bean(name = "mainSqlSessionFactory")
    public SqlSessionFactory getMainSqlSessionFactory (ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getMainHikariDataSource());
        sqlSessionFactoryBean.setConfiguration(this.configuration);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/main/*-SQL.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "mainSqlSessionTemplate")
    public SqlSessionTemplate getMainSqlSessionTemplate (ApplicationContext applicationContext) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(getMainSqlSessionFactory(applicationContext));
        return sqlSessionTemplate;
    }

    @Bean(name = "objectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //기본 날짜 포맷 비활성화
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //새로운 날짜 포맷 세팅
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return mapper;
    }
    @Bean(name="jsonView")
    public MappingJackson2JsonView getJsonView () {
        ObjectMapper objectMapper = getObjectMapper();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView(objectMapper);
        jsonView.setExtractValueFromSingleKeyModel(true);
        return jsonView;
    }
}
