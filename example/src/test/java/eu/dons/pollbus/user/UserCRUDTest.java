package eu.dons.pollbus.user;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.caucho.junit.ConfigurationBaratine;
import com.caucho.junit.RunnerBaratine;
import com.caucho.v5.config.Configurable;

import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.core.validation.BeanValidator;
import eu.dons.pollbus.user.boundary.IUser;
import eu.dons.pollbus.user.control.UsersEndpoint;
import eu.dons.pollbus.user.entity.User;
import io.baratine.core.Lookup;


@RunWith(RunnerBaratine.class)
@ConfigurationBaratine(services={BeanValidator.class, UsersEndpoint.class})
public class UserCRUDTest {
	
	@Lookup("/users")
	private IUsersSync sync;
	
	@Lookup("/users/123")
	private IUserSync a;
	
	
	// === Endpoint
	@Test
	public void usersEndpointCreatesNewUsers() throws AppException {
		String userKey = sync.createUser("don", "password");
		assertTrue(userKey != null);
	}
	
	
	// === Resource
	@Test
	public void userResourceWithoutUserCreatedHasEmptyManagedUser(){
		User user = a.get();
		assertTrue(user != null);
		assertTrue(user.isEmpty());
	}

}
