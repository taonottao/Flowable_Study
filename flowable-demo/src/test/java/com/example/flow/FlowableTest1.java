package com.example.flow;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.junit.jupiter.api.Test;

/**
 * @version 1.0
 * @Author T-WANG
 * @Date 2023/12/17 11:43
 */
public class FlowableTest1 {

    private static final String url =
            "jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC&nullCatalogMeansCurrent=true";

    /**
     * 部署流程到数据库中
     * 在非 Spring 环境下的应用
     */
    @Test
    void deployFlow() {
//        System.out.println("***********");

        // 流程引擎的配置对象，关联相关的数据源
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl(url)
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("wangtao")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 获取流程引擎对象
        ProcessEngine processEngine = cfg.buildProcessEngine();
        // 部署流程需要获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/01/firstFlow.bpmn20.xml")
                .name("第一个流程图")
                .deploy();// 部署的方法
        System.out.println(deploy.getId());
    }

}
