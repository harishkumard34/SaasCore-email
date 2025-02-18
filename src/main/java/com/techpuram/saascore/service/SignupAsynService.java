package com.techpuram.saascore.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.techpuram.saascore.config.TenantContext;
import com.techpuram.saascore.entity.Auth;
import com.techpuram.saascore.entity.Profiles;
import com.techpuram.saascore.entity.Roles;
import com.techpuram.saascore.entity.Users;

@Service
public class SignupAsynService {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@SuppressWarnings("unused")
	@Autowired
	private DataSource dataSource;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private RoleService roleService;

	@Async
    public void createUserAsync(Auth auth, String tenantName, UserService userService, AuthService authService) {
		TenantContext.setCurrentTenant(tenantName);
		try{
		// Connection connection = dataSource.getConnection();
		transactionTemplate.execute(status -> {

			//  Flyway flyway = Flyway.configure()
	        //             .dataSource(dataSource)
	        //             .schemas(tenantName)
	        //             .locations("classpath:/db/migration/defaultvalues") // Separate folder for tenant migrations
	        //             .baselineOnMigrate(true)
	        //             .load();
	        //     flyway.migrate();



				// Fetch Role and Profile
				Roles defaultRole = roleService.getRoleByName("Level 1");
				Profiles adminProfile = profileService.getProfileByName("Administrator");

				if (defaultRole == null || adminProfile == null) {
					throw new IllegalStateException("Default Role or Profile not found in tenant schema");
				}

			Users user = new Users();
		    user.setFirstName(auth.getAuthName());
			user.setLastName(auth.getAuthName());
		    user.setEmailId(auth.getEmail());
		    user.setCreatedTime(auth.getCreatedTime());
		    user.setModifiedTime(auth.getCreatedTime());
		    user.setRole(defaultRole);
			user.setProfile(adminProfile);
		    user.setStatus("ACTIVE");
	        userService.createUser(user);

			auth.setUserId(user.getUserId());
			authService.updateAuth(auth);

	        return null;

		});
	}catch(Exception e) {


	}
    }

}
