package com.kalvin.kvf;

import com.kalvin.kvf.common.utils.PastUtil;
import com.kalvin.kvf.modules.generator.utils.VelocityKit;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by Kalvin on 2019/06/13 20:21
 */
public class MyTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyTest.class);

    public static void main(String[] args) throws Exception {
//        velocity();
        getTickets();
    }

    public static void velocity() {
        VelocityContext ctx = new VelocityContext();
        ctx.put("name", "velocity");
        String destPath = "D:\\vm\\";
        VelocityKit.toFile("table.vm", ctx, destPath + "_table.html");
    }

    public static void getTickets(){
//        String json = PastUtil.getParam ("wx719c937ea903be65", "b22eabdd155cd8174e2791c95d513a7e");
//
//        System.out.println (json);
    }
}
