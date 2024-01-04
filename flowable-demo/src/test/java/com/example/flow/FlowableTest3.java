package com.example.flow;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author T-WANG
 * @Date 2023/12/26 21:37
 */
@SpringBootTest(classes = FlowableDemoApplication.class)
public class FlowableTest3 {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    // 任务实例相关的操作都是通过这个来实现的
    @Autowired
    TaskService taskService;

    // 流程部署操作
    @Test
    void deployFlow() {
//        System.out.println(processEngine);
        // 可直接注入
//        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/01/firstFlow.bpmn20.xml")
                .name("流程变量案例")
                .deploy();// 部署的方法
        System.out.println(deploy.getId());
    }

    /**
     * 启动流程实例
     */
    @Test
    void startFlow() {
        // 流程定义表中动态维护，推荐使用 id 来启动
        String processId = "firstFlow:1:79b9cbaf-aa32-11ee-a814-7a2b46d26c57";
        // 创建流程图的时候自定义的。注意保证唯一性
//        String processKey = "firstFlow";

        // 在启动流程实例的时候我们就可以绑定对应的表达式的值
        Map<String, Object> variables = new HashMap<>();
        variables.put("var1", "test1");
        variables.put("var2", "test2");
        variables.put("var3", "test3");

        // 1. 根据流程定义ID 启动流程实例，返回值为流程实例，设置的变量为全局变量
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId, variables);
        // 2. 根据流程定义key 启动流程实例
//        runtimeService.startProcessInstanceByKey(processKey);
    }

    /**
     * 获取对应的变量
     */
    @Test
    void getVariables() {

    }

    /**
     * 根据用户查询待办信息
     */
    @Test
    void findFlow() {
        // 获取到 act_ru_task 中 assignee 字段是 zhangsan 的记录，也就是找到 zhangsan 所有待办的任务信息
        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee("lisi") // 指定查询条件
                .list();
        for (Task task : taskList) {
            System.out.println(task.getId());
        }
    }

    /**
     * 任务的审批
     */
    @Test
    void completeTask() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("myAssign1", "lisi");

        // 这个 taskId 就是上面 findFlow 中的 task.getId()
        String taskId = "fe02c88f-a8bb-11ee-a9d4-7a2b46d26c57";
        // 完成任务的审批，根据任务的 id 以及绑定的表达式的值
//        taskService.complete(taskId, variables);
        taskService.complete(taskId);
    }

    /**
     * 流程定义的挂起和激活
     */
    @Test
    void suspendedActivity() {
        String processDefinitionId = "firstFlow:1:57d76795-a3fa-11ee-b2da-7a2b46d26c57";
        // 流程的挂起和激活操作，针对的是流程
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult(); // 返回单个

        // 获取当前的流程定义的状态，是否挂起
        boolean suspended = processDefinition.isSuspended();
        if (suspended) {
            // 表示挂起，则启动激活流程
            System.out.println("激活流程");
            repositoryService.activateProcessDefinitionById(processDefinitionId);
        } else {
            // 表示激活，则启动挂起流程
            System.out.println("挂起流程");
            repositoryService.suspendProcessDefinitionById(processDefinitionId);
        }

    }

    /**
     * 挂起流程实例
     */
    @Test
    void suspendInstance() {
        // 1. 挂起流程实例
        runtimeService.suspendProcessInstanceById("9093d7e8-a4c3-11ee-a994-7a2b46d26c57");
        // 2. 激活流程实例
        runtimeService.activateProcessInstanceById("9093d7e8-a4c3-11ee-a994-7a2b46d26c57");
    }

}
