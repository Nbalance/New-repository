package com.gloudtek.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-renren.xml" })
public class AppVersionServiceTest {
	
	@Test
	public void get() {
//		String[] names = { "张三", "李四", "王五" };
//		String name_str = "礼拜";
//		for (int i = 0; i < names.length; i++) {
//			if (i > 0 || i < names.length) {
//				name_str += ", ";
//			}
//			name_str += names[i];
//		}
//		System.out.println(name_str);
		String ks ="123456";
		long appId =Long.parseLong(ks);
		System.out.println("+++++++++++++++++++++++++++++++++"+appId);
	}
}
