package com.daitoj.tkms;

import com.daitoj.tkms.config.AsyncSyncConfiguration;
import com.daitoj.tkms.config.EmbeddedRedis;
import com.daitoj.tkms.config.EmbeddedSQL;
import com.daitoj.tkms.config.JacksonConfiguration;
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
@SpringBootTest(classes = { TkmsBackEndApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
