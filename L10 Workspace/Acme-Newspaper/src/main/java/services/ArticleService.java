package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.AdminRepository;
import repositories.ArticleRepository;
import repositories.FollowUpRepository;
import repositories.NewspaperRepository;
import repositories.UserRepository;
import domain.Article;
import domain.FollowUp;
import domain.Newspaper;
import forms.ArticleForm;

public class ArticleService {

	// Managed repository
	@Autowired
	private ArticleRepository articleRepository;

	// Supporting services
	@Autowired
	private NewspaperRepository newspaperRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private FollowUpRepository followUpRepository;

	// Constructors
	public ArticleService() {
		super();
	}

	// Simple CRUD methods
	public Article create(final int newspaperId) {
		Article res = new Article();
		// An article is published when the corresponding newspaper is published.
		Newspaper newspaper = this.newspaperRepository.findOne(newspaperId);
		Date publicationMoment = newspaper.getPublicationDate();

		res.setPublicationMoment(publicationMoment);
		res.setNewspaper(newspaper);

		return res;
	}

	public Article save(final Article article) {
		Assert.notNull(article);
		// this.userRepository.checkAuthority();
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
//		this.adminRepository.checkAuthority();
		
		Assert.notNull(article);
		Assert.isTrue(article.getId()!=0);
		Assert.isTrue(this.adminRepository.exists(article.getId()));
		
		
		article.getNewspaper().getArticles().remove(article);
		article.getWriter().getArticles().remove(article);
		for (FollowUp followUp : article.getFollowUps()){
			this.followUpRepository.delete(followUp);
		}
		
		this.articleRepository.delete(article);
	}
	
	// Other busines methods
	
	public ArticleForm construct(Article article){
		ArticleForm res = new ArticleForm();
		
		res.setId(article.getId());
		res.setTitle(article.getTitle());
		res.setSummary(article.getSummary());
		res.setBody(article.getBody());
		res.setPictures(article.getPictures());
		res.setFinal(article.getIsFinal());
		res.setNewspaperId(article.getNewspaper().getId());
		
		return res;
	}
	
	public Article reconstruct(ArticleForm articleForm, BindingResult binding){
		Assert.notNull(articleForm);
		Newspaper newspaper = this.newspaperRepository.findOne(articleForm.getNewspaperId());
		
		Article res = create(newspaper.getId());
		
		res.setId(articleForm.getId());
		res.setTitle(articleForm.getTitle());
		res.setSummary(articleForm.getSummary());
		res.setBody(articleForm.getBody());
		res.setPictures(articleForm.getPictures());
		res.setIsFinal(articleForm.isFinal());
		res.setNewspaper(newspaper);
		
		return res;
	}
	
 
}
