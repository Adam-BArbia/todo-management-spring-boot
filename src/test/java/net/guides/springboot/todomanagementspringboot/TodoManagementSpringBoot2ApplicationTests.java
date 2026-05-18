package net.guides.springboot.todomanagementspringboot;

import net.guides.springboot.todomanagement.TodoManagementSpringBoot2Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = TodoManagementSpringBoot2Application.class,
		properties = {
				"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
		}
)
public class TodoManagementSpringBoot2ApplicationTests {

	@Test
	public void contextLoads() {
	}
}