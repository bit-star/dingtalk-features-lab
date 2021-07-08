package com.kyanite.dingtalk.lab;

import com.kyanite.dingtalk.lab.DingtalkFeaturesLabApp;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = DingtalkFeaturesLabApp.class)
public @interface IntegrationTest {
}
