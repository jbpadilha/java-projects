package hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.joaopadilha.dao.UserDao;
import com.joaopadilha.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatfloDockerTests {
	
	@Autowired
	UserDao userDao;
	
	User test,test1,test2;
	
	@Before
	public void setUp() {
		
		test = userDao.save(new User(1,"joao","foo"));
		test1 = userDao.save(new User(1,"joao1","foo"));
		test2 = userDao.save(new User(1,"joao1","foo2"));
		
	}
	
	@Test
	public void findUser() {
		List<User> result = userDao.findUser("foo");
		assertThat(result).hasSize(1);
	}
	
	
}
