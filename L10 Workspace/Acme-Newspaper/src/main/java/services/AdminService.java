package services;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import domain.Newspaper;

@Service
@Transactional
public class AdminService {

	// Managed repository
	@Autowired
	private AdminRepository adminRepository;

	// Constructors
	public AdminService() {
		super();
	}

	// Simple CRUD methods

	public Admin create() {
		final Admin res = new Admin();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();

		authority.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(authority);
		res.setUserAccount(userAccount);

		return res;

	}

	public Collection<Admin> findAll() {
		Collection<Admin> res;
		res = this.adminRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Admin findOne(final int adminid) {
		Assert.isTrue(adminid != 0);
		Admin res;
		res = this.adminRepository.findOne(adminid);
		Assert.notNull(res);

		return res;
	}

	public Admin save(final Admin admin) {
		Admin res;

		if (admin.getId() == 0) {
			String pass = admin.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			admin.getUserAccount().setPassword(pass);
		}

		res = this.adminRepository.save(admin);
		return res;
	}

	// Ancillary methods

	public Admin findByPrincipal() {
		Admin res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		res = this.adminRepository
				.findAdminByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public boolean checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("ADMIN");
		if (authority.contains(res))
			return true;
		else
			return false;
	}

	public void flush() {
		this.adminRepository.flush();
	}

	// C-1
	public Object[] avgSqtrUser() {
		Object[] res;
		res = this.adminRepository.avgSqtrUser();
		return res;
	}

	// C-2
	public Object[] avgSqtrArticlesByWriter() {
		Object[] res;
		res = this.adminRepository.avgSqtrArticlesByWriter();
		return res;
	}

	// C-3
	public Object[] avgSqtrArticlesByNewspaper() {
		Object[] res;
		res = this.adminRepository.avgSqtrArticlesByNewspaper();
		return res;
	}

	// C-4
	public Collection<Newspaper> newspapersMoreAverage() {
		Collection<Newspaper> res = this.adminRepository
				.newspapersMoreAverage();
		return res;
	}

	// C-5
	public Collection<Newspaper> newspapersFewerAverage() {
		Collection<Newspaper> res = this.adminRepository
				.newspapersFewerAverage();
		return res;
	}

	// C-6
	public Double ratioUserCreatedNewspaper() {
		Double res;
		res = this.adminRepository.ratioUserCreatedNewspaper();
		return res;
	}

	// C-7
	public Double ratioUserWrittenArticle() {
		Double res;
		res = this.adminRepository.ratioUserWrittenArticle();
		return res;
	}

	// B-1
	public Double avgFollowupsPerArticle() {
		Double res;
		res = this.adminRepository.avgFollowupsPerArticle();
		return res;
	}

	// B-2
	public Double avgNumberOfFollowUpsPerArticleAfter1Week() {
		Double res;
		long oneWeek = TimeUnit.DAYS.toMillis(7);
		Date f = new Date(System.currentTimeMillis() - oneWeek);
		res = this.adminRepository.avgNumberOfFollowUpsPerArticleAfter1Week(f);
		return res;
	}

	// B-3
	public Double avgNumberOfFollowUpsPerArticleAfter2Week() {
		Double res;
		long twoWeeks = TimeUnit.DAYS.toMillis(7);
		Date f = new Date(System.currentTimeMillis() - twoWeeks);
		res = this.adminRepository.avgNumberOfFollowUpsPerArticleAfter2Week(f);
		return res;
	}

	// B-4
	public Object[] avgStddevNumberOfChirpPerUser() {
		Object[] res;
		res = this.adminRepository.avgStddevNumberOfChirpPerUser();
		return res;
	}
	
	//B-5
		public Double ratioUsersMorePostedChirpsOfAveragePerUser() {
			Double res;
			res = this.adminRepository.ratioUsersMorePostedChirpsOfAveragePerUser();
			return res;
		}

}
