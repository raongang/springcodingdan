package org.zerock.web;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/*
 * RunWith, ContextConfiguration 애노테이션은 현재 테스트코드를 실행할 때 스프링이 로딩되도록 하는 부분.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DataSourceTest {

		//spring이 생성해서 주입해준다.
		@Inject
		private DataSource ds;
		
		@Test
		public void testConnection() throws Exception{
			try(Connection con=ds.getConnection()){
				System.out.println(con);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

}
