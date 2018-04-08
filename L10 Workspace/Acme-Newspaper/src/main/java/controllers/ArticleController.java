package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import services.UserService;
import domain.Article;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController{
	
	// Services --------------------------------
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private UserService userService;
	
	// Constructors ----------------------------
	public ArticleController(){
		super();
	}
	
	// Listing ----------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false) String keyword,
							@RequestParam(required=false) Integer userId){
		ModelAndView res;
		Collection<Article> articles = new ArrayList<Article>();
		
		if(keyword!=null){
			articles = this.articleService.findPerKeyword(keyword);
		}
		
		if(userId!=null){
			articles = this.userService.findOne(userId).getArticles();
		}
		
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
