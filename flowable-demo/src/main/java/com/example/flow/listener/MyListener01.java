package com.example.flow.listener;

import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

/**
 * @version 1.0
 * @Author T-WANG
 * @Date 2024/1/1 22:54
 */
public class MyListener01 implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("MyListener01.notify....执行了。" + delegateTask.getEventName());
        if (EVENTNAME_CREATE.equals(delegateTask.getEventName())) {
            // 用户节点的创建，然后指派审批人
            delegateTask.setAssignee("wt");
        }
    }
}
