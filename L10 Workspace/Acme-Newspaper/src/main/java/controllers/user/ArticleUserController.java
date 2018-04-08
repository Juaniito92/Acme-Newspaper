package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import services.UserService;
import controllers.AbstractController;
import domain.Article;
import domain.Newspaper;
import domain.User;
import forms.ArticleForm;

@Controller
@RequestMapping("/article/user")
public class ArticleUserController extends AbstractController{
	
	// Services --------------------------------
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private UserService userService;
	
	// Constructors ----------------------------
	public ArticleUserController() {
		super();
	}
	
	// Create ----------------------------------------
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId){
		this.userService.checkAuthority();
		ModelAndView res;
		Article article;
		ArticleForm articleForm;
		
		article = this.articleService.create(newspaperId);
		articleForm = this.articleService.construct(article);
		
		res = this.createEditModelAndView(articleForm);
		return res;
	}

	
	// Edit ----------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId){
		ModelAndView res;
		Article article;
		ArticleForm articleForm;
		
		article = this.articleService.findOne(articleId);
		articleForm = this.articleService.construct(article);
		
		res = this.createEditModelAndView(articleForm);
		return res;
	}
	
	
	// Save ----------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(final ArticleForm articleForm, final BindingResult binding){
		ModelAndView res;
		
		if(binding.hasErrors()){
			res= this.createEditModelAndView(articleForm, "article.params.error");
		}else
			try{
				Article article = this.articleService.reconstruct(articleForm, binding);
				this.articleService.save(article);
				Integer id = article.getNewspaper().getId();
				
				res = new ModelAndView("redirect:/article/list.do?newspaperId="+id);
			
			}catch (final Throwable oops) {
				res = this.createEditModelAndView(articleForm, "article.commit.error");
			}
		
		return res;
	}
	
	
				
	// Ancillary methods  ----------------------------------------
	
	protected ModelAndView createEditModelAndView(final ArticleForm articleForm) {
		ModelAndView res;
		
		res = this.createEditModelAndView(articleForm, null);
		
		return res;
	}
	
	protected ModelAndView createEditModelAndView(ArticleForm articleForm,
			String message) {
		ModelAndView res;
		User principal = this.userService.findByPrincipal();
		res = new ModelAndView("article/edit");
		int newspaperId = articleForm.getNewspaperId();
		Newspaper newspaper = this.newspaperService.findOne(newspaperId);
		Collection<Newspaper> newspapers = new ArrayList<Newspaper>();
		newspapers = principal.getNewspapers();
		
		res.addObject("articleForm",articleForm);
		res.addObject("newspaper",newspapers);
		res.addObject("message",message);
		res.addObject("requestURI","article/user/edit.do");
		
		return res; 
	}
}