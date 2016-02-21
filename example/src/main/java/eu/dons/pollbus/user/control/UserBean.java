package eu.dons.pollbus.user.control;

import java.util.Objects;

import javax.inject.Inject;

import io.baratine.core.Lookup;
import io.baratine.core.Modify;
import io.baratine.core.OnInit;
import io.baratine.core.OnLoad;
import io.baratine.core.OnSave;
import io.baratine.core.Result;
import io.baratine.core.ServiceManager;
import lombok.Builder;

import eu.dons.baratine.persistence.StoredVal;
import eu.dons.pollbus.core.AppException;
import eu.dons.pollbus.core.ResourceBase;
import eu.dons.pollbus.core.validation.IBeanValidator;
import eu.dons.pollbus.user.boundary.IUser;
import eu.dons.pollbus.user.entity.User;
import eu.dons.pollbus.user.entity.UserId;


@Builder
public class UserBean extends ResourceBase implements IUser {

	private IBeanValidator validator;
	
	private UserId userId;	
	private StoredVal<User> db;	
	private User user;
	
	@OnInit
	public void onInit() {
		validator = ServiceManager.current().lookup("/base/beanvalidator").as(IBeanValidator.class);
	}	
	
	@OnLoad
	public void onLoad(Result<Boolean> result) {
		Objects.requireNonNull(db);
		db.load(User.EMPTY, result.from( u -> {
			this.user = u;
			return true;
		}));
	}	
	
	@OnSave
	public void save(Result<Boolean> result){
		db.save(this.user, result);
	}

	@Override
	public void get(Result<User> result) {
		result.complete(user);		
	}

	@Override
	@Modify
	public void create(User user, Result<String> result) throws AppException {
		
		if(userId == null ) {
			throw new IllegalStateException("resources id not initialized!");
		} else {			
			if(!userId.equals(user.identity())) {
				throw new IllegalArgumentException("wrong data passed - user#identity did not match!");			}
		}	
		
		validator.validate(user, result.from(u -> {
			this.user = u;
			return user.identity().getKey();
		}));		
	}

	@Override
	@Modify
	public void delete(Result<Boolean> result) {
		this.user = User.EMPTY;
	}
	
	@Override
	public String toString() {
		return "UserBean [userId=" + userId + "]";
	}
}
