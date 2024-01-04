package com.example.flow.bean;

import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author T-WANG
 * @Date 2024/1/1 22:24
 */
@Component
public class MyBean {

    public String getAssignee() {
        System.out.println("MyBean.getAssignee...执行了");
        return "wangwu";
    }
}
