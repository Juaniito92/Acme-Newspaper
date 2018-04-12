package controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import domain.Article;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController{
	
	// Services --------------------------------
	@Autowired
	private ArticleService articleService;

	
	// Constructors ----------------------------
	public ArticleController(){
		super();
	}
	
	// Listing ----------------------------------
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list(@RequestParam(required = false) String keyword,
//			@RequestParam int newspaperId) {
//		Collection<Article> articles = new ArrayList<Article>();
//		Newspaper newspaper = newspaperService.findOne(newspaperId);
//
//		if (keyword != null) {
//			articles = this.articleService.findPerKeyword(keyword);
//		} else {
//			articles = newspaper.getArticles();
//		}
//
//		ModelAndView result = new ModelAndView(
//				"redirect:../newspaper/display.do?newspaperId=" + newspaperId);
//		result.addObject("articles", articles);
//
//		return result;
//	}
	
	// Display ----------------------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId){
		ModelAndView res;
		Article article;
		
		article = this.articleService.findOne(articleId);
		Collection<String> pictures = Arrays.asList(article.getPictures().split("\r\n"));
		
		res = new ModelAndView("article/display");
		res.addObject("article",article);
		res.addObject("pictures", pictures);
		res.addObject("date", new Date());
		
		return res;
	}
	
	// Searching --------------------------------

}
