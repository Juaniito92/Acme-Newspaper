package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminServiceTest extends AbstractTest{

	@Autowired
	private AdminService adminService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	// Test login ------------------------------------
	
	@Test
	public void loginDriver(){
		final Object testingData[][] = {
				{"admin", null}, // logueado correctamente
				{"Carlos", IllegalArgumentException.class}, // logueado sin existir
		};
		for (int i=0;i<testingData.length;i++){
			this.templateLoginAdmin((String)testingData[i][0],
					(Class<?>)testingData[i][1]);
		}
	}

	private void templateLoginAdmin(String admin, Class<?> expected) {

		Class<?> caught;
		caught = null;
		
		try{
			super.authenticate(admin);
			this.unauthenticate();
			this.adminService.flush();
		}catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}
		this.checkExceptions(expected, caught);
	}
	
}
