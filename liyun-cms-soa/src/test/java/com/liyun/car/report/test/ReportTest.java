package com.liyun.car.report.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liyun.car.common.enums.OperMode;
import com.liyun.car.dealer.entity.CmsDealer;
import com.liyun.car.dealer.enums.DealerStatusEnum;
import com.liyun.car.dealer.service.DealerService;
import com.liyun.car.param.service.DepartmentService;
import com.liyun.car.system.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-base.xml" })
public class ReportTest {

	@Autowired
	private DepartmentService departmentService;
	
	@Test
	public void test1(){
	}
	
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		Field field = CmsDealer.class.getDeclaredField("dealerFiles");
		System.out.println(field.getType().getSimpleName());
	}
}
