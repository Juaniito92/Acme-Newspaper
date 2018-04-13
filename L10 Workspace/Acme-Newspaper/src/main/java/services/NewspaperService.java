package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NewspaperRepository;
import domain.Article;
import domain.Configuration;
import domain.Newspaper;
import domain.Subscription;
import domain.User;
import forms.NewspaperForm;

@Service
@Transactional
public class NewspaperService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NewspaperRepository newspaperRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private Validator validator;
	
	@Autowired
	private ConfigurationService configurationService;

	// Constructors -----------------------------------------------------------

	public NewspaperService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Newspaper create() {

		Newspaper result = new Newspaper();

		result.setIsPrivate(false);
		result.setPublisher(this.userService.findByPrincipal());
		result.setArticles(new ArrayList<Article>());
		result.setSubscriptions(new ArrayList<Subscription>());

		return result;
	}

	public Collection<Newspaper> findAll() {

		Collection<Newspaper> result;

		result = this.newspaperRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Newspaper findOne(final int newspaperId) {

		final Newspaper result = this.newspaperRepository.findOne(newspaperId);

		return result;
	}

	public Newspaper findOneToEdit(final int newspaperId) {

		final Newspaper result = this.findOne(newspaperId);
		this.checkPrincipal(result);

		return result;
	}

	public Newspaper save(final Newspaper newspaper) {

		Assert.notNull(newspaper);
		this.checkPrincipal(newspaper);

		final Newspaper result;

		result = this.newspaperRepository.save(newspaper);
		result.getPublisher().getNewspapers().add(result);

		return result;
	}

	public void delete(final Newspaper newspaper) {

		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(this.adminService.findByPrincipal() != null);

		newspaper.getPublisher().getNewspapers().remove(newspaper);
		for (Article article : newspaper.getArticles()) {
			articleService.delete(article);
		}
		for (final Subscription subscription : newspaper.getSubscriptions())
			this.subscriptionService.delete(subscription);

		this.newspaperRepository.delete(newspaper);
	}

	// Other business methods -------------------------------------------------

	public void checkPrincipal(final Newspaper newspaper) {

		Assert.notNull(newspaper);

		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(newspaper.getPublisher().equals(principal));
	}

	public NewspaperForm construct(final Newspaper newspaper) {

		Assert.notNull(newspaper);

		NewspaperForm newspaperForm;

		newspaperForm = new NewspaperForm();

		newspaperForm.setId(newspaper.getId());
		newspaperForm.setTitle(newspaper.getTitle());
		newspaperForm.setDescription(newspaper.getDescription());
		newspaperForm.setPicture(newspaper.getPicture());
		newspaperForm.setPublicationDate(newspaper.getPublicationDate());
		newspaperForm.setIsPrivate(newspaper.getIsPrivate());
		newspaperForm.setPublisherId(newspaper.getPublisher().getId());

		return newspaperForm;
	}

	public Newspaper reconstruct(final NewspaperForm newspaperForm,
			final BindingResult binding) {

		Assert.notNull(newspaperForm);

		Newspaper newspaper;

		if (newspaperForm.getId() != 0)
			newspaper = this.findOne(newspaperForm.getId());
		else
			newspaper = this.create();

		newspaper.setTitle(newspaperForm.getTitle());
		newspaper.setDescription(newspaperForm.getDescription());
		newspaper.setPublicationDate(newspaperForm.getPublicationDate());
		newspaper.setPicture(newspaperForm.getPicture());
		newspaper.setIsPrivate(newspaperForm.getIsPrivate());

		if (binding != null)
			this.validator.validate(newspaper, binding);

		return newspaper;
	}

	public Collection<Newspaper> findAvalibleNewspapers() {

		Collection<Newspaper> newspapers1 = newspaperRepository
				.findPastNewspapers();
		Collection<Newspaper> newspapers2 = newspaperRepository
				.findPastNewspapersWithNonFinalArticle();

		newspapers1.removeAll(newspapers2);

		return newspapers1;
	}

	public Collection<Newspaper> findPerKeyword(final String keyword) {

		Collection<Newspaper> newspapers = null;
		newspapers = new ArrayList<Newspaper>();
		String aux = "Newspaper";

		if (keyword.equals("")) {
			newspapers = this.findAvalibleNewspapers();
		} else {
			aux = keyword;
			newspapers = this.newspaperRepository.findPerKeyword(aux);
		}

		return newspapers;
	}

	public Collection<Newspaper> findByPublisherId(int publisherId) {

		Collection<Newspaper> newspapers = newspaperRepository
				.findByPublisherId(publisherId);

		return newspapers;
	}

	public Collection<Newspaper> findByPrincipal() {

		User principal = this.userService.findByPrincipal();
		Collection<Newspaper> newspapers = newspaperRepository
				.findByPublisherId(principal.getId());

		return newspapers;
	}

	public Collection<Newspaper> findNonPublished() {

		Collection<Newspaper> newspapers = newspaperRepository
				.findNonPublished();

		return newspapers;
	}
	
	public Collection<Newspaper> newspaperContainTabooWord(){
		Assert.notNull(adminService.findByPrincipal());
		Collection<Newspaper> res = new ArrayList<>();
		Configuration configuration;
		Collection<String> tabooWords = new ArrayList<>();
		Collection<Newspaper> allNewspaper = new ArrayList<>();
		
		configuration = this.configurationService.findAll().iterator().next();
		tabooWords = Arrays.asList(configuration.getTabooWords().split(","));
		allNewspaper = this.findAll();
		
		for(Newspaper newspaper: allNewspaper){
			for(String tabooWord: tabooWords){
				String lowTabooWord = tabooWord.toLowerCase();
				if(newspaper.getTitle().toLowerCase().contains(lowTabooWord.trim()) || newspaper.getDescription().toLowerCase().contains(lowTabooWord.trim())){
					if(!res.contains(newspaper)){
						res.add(newspaper);
					}
				}
			}
		}
		return res;
	}
	
	public void flush() {
		this.newspaperRepository.flush();
	}

}
