package com.coherentlogic.coherent.datafeed.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:spring/application-context.xml", "classpath*:spring/cache-beans.xml"})
public class XMLConfiguration {

}
