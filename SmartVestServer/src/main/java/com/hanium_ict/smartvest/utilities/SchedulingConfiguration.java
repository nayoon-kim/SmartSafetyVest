package com.hanium_ict.smartvest.utilities;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulingConfiguration implements SchedulingConfigurer{
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
	}
}
