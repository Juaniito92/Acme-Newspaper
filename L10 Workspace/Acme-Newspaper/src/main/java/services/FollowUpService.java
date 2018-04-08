package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FollowUpRepository;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;
import forms.FollowUpForm;

@Service
@Transactional
public class FollowUpService {

	// Managed repository
	@Autowired
	private FollowUpRepository followUpRepository;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	// Constructors
	public FollowUpService() {
		super();
	}

	// Simple CRUD methods

	public FollowUp create(final User user) {
		FollowUp res = new FollowUp();

		Date moment = new Date(System.currentTimeMillis() - 1);

		res.setUser(user);
		res.setPublicationMoment(moment);

		return res;

	}

	public Collection<FollowUp> findAll() {
		Collection<FollowUp> res;
		res = this.followUpRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public FollowUp findOne(final int followUpid) {
		Assert.isTrue(followUpid != 0);
		FollowUp res;
		res = this.followUpRepository.findOne(followUpid);
		Assert.notNull(res);

		return res;
	}

	public FollowUp save(FollowUp followUp) {
		Assert.notNull(followUp);
		this.checkPrincipal(followUp);

		FollowUp res;

		res = this.followUpRepository.save(followUp);

		Assert.notNull(res);
		return res;

	}

	public void delete(FollowUp followUp) {
		this.adminService.checkAuthority();

		Assert.notNull(followUp);
		Assert.isTrue(followUp.getId() != 0);
		Assert.isTrue(this.followUpRepository.exists(followUp.getId()));

		this.followUpRepository.delete(followUp);
	}

	// Other busines methods

	public Collection<FollowUp> findFollowUpsByArticle(int articleId) {
		Collection<FollowUp> res = this.followUpRepository
				.findFollowUpsByArticle(articleId);
		return res;
	}

	public void checkPrincipal(FollowUp followUp) {
		User principal = userService.findByPrincipal();
		Assert.isTrue(principal.equals(followUp.getUser()));
	}

	public void flush() {
		this.followUpRepository.flush();
	}

	public FollowUpForm construct(FollowUp followUp) {
		FollowUpForm res = new FollowUpForm();

		res.setTitle(followUp.getTitle());
		res.setText(followUp.getText());
		res.setPictures(followUp.getPictures());
		res.setSummary(followUp.getSummary());

		return res;
	}

	public FollowUp reconstruct(FollowUpForm followUpForm, BindingResult binding) {
		Assert.notNull(followUpForm);
		FollowUp res = new FollowUp();

		Date moment = new Date(System.currentTimeMillis() - 1);

		res.setTitle(followUpForm.getTitle());
		res.setPublicationMoment(moment);
		res.setText(followUpForm.getText());
		res.setPictures(followUpForm.getPictures());
		res.setSummary(followUpForm.getSummary());

		if (binding != null)
			validator.validate(res, binding);

		return res;
	}
}
