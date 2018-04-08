package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdminRepository;
import repositories.ArticleRepository;
import repositories.FollowUpRepository;
import repositories.NewspaperRepository;
import repositories.UserRepository;
import domain.Article;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;
import forms.ArticleForm;

@Service
@Transactional
public class ArticleService {

	// Managed repository
	@Autowired
	private ArticleRepository articleRepository;

	// Supporting services
	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FollowUpService followUpService;

	@Autowired
	private Validator validator;
	
	

	// Constructors
	public ArticleService() {
		super();
	}

	// Simple CRUD methods
	public Article create(final int newspaperId) {
		Article res = new Article();
		Newspaper newspaper = this.newspaperService.findOne(newspaperId);
		Date actual = new Date(System.currentTimeMillis()-1);
		Collection<FollowUp> followUps = new ArrayList<FollowUp>();
		User user = this.userService.findByPrincipal();
		
		res.setWriter(user);
		res.setFollowUps(followUps);
		res.setPublicationMoment(actual);
		res.setNewspaper(newspaper);

		return res;
	}

	public Article save(final Article article) {
		Assert.notNull(article);
		this.checkPrincipal(article);
		Article res;

		res = this.articleRepository.save(article);

		Assert.notNull(res);
		return res;
	}

	

	public Collection<Article> findAll() {
		Collection<Article> res = new ArrayList<Article>();

		res = this.articleRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Article findOne(final int articleId) {
		Assert.isTrue(articleId != 0);
		Article res;

		res = this.articleRepository.findOne(articleId);

		Assert.notNull(res);
		return res;
	}

	public void delete(final Article article){
		this.adminService.checkAuthority();
		
		Assert.notNull(article);
		Assert.isTrue(article.getId()!=0);
		
		
		article.getNewspaper().getArticles().remove(article);
		article.getWriter().getArticles().remove(article);
		for (FollowUp followUp : article.getFollowUps()){
			this.followUpService.delete(followUp);
		}
		
		this.articleRepository.delete(article);
	}
	
	// Other busines methods
	
	private void checkPrincipal(Article article) {
		User principal = this.userService.findByPrincipal();
		Assert.isTrue(principal.equals(article.getNewspaper().getPublisher()));
		
	}
	
	public ArticleForm construct(Article article){
		Assert.notNull(article);
		ArticleForm res = new ArticleForm();
		
		res.setId(article.getId());
		res.setTitle(article.getTitle());
		res.setSummary(article.getSummary());
		res.setBody(article.getBody());
		res.setPictures(article.getPictures());
		res.setIsFinal(article.getIsFinal());
		res.setNewspaperId(article.getNewspaper().getId());
		
		return res;
	}
	
	public Article reconstruct(ArticleForm articleForm, BindingResult binding){
		Assert.notNull(articleForm);
		Newspaper newspaper = this.newspaperService.findOne(articleForm.getNewspaperId());
		Article res;
		
		if(articleForm.getId()!=0)
			res = this.findOne(articleForm.getId());
		else	
			res = this.create(newspaper.getId());
		
		res.setId(articleForm.getId());
		res.setTitle(articleForm.getTitle());
		res.setSummary(articleForm.getSummary());
		res.setBody(articleForm.getBody());
		res.setPictures(articleForm.getPictures());
		res.setIsFinal(articleForm.getIsFinal());
		res.setNewspaper(newspaper);
		
		if(binding!=null)
			this.validator.validate(res,binding);
		
		return res;
	}

	public Collection<Article> findPerKeyword(String keyword) {
		Collection<Article> articles = null;
		articles = new ArrayList<Article>();
		String aux = "Article";
		
		if(keyword!=null){
			aux = keyword;
			articles = this.articleRepository.findPerKeyword(aux);
		}
		return articles;
	}
	
 
}
