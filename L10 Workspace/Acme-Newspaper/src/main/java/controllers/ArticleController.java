package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Article;

import repositories.NewspaperRepository;
import services.ArticleService;
import services.NewspaperService;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController{
	
	// Services --------------------------------
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperService newspaperService;
	
	// Constructors ----------------------------
	public ArticleController(){
		super();
	}
	
	// Listing ----------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam final int newspaperId){
		ModelAndView res;
		Collection<Article> articles;
		
		articles = this.newspaperService.findOne(newspaperId).getArticles();
		
		res = new ModelAndView("article/list");
		res.addObject("article",articles);
		res.addObject("requestURI", "article/list.do");
		
		return res;
	}
	
	// Display ----------------------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId){
		ModelAndView res;
		Article article;
		
		article = this.articleService.findOne(articleId);
		
		res = new ModelAndView("article/display");
		res.addObject("article",article);
		res.addObject("requestURI","article/display.do");
		
		return res;
	}
	
	// Searching --------------------------------

}
