package controllers;

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
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private ArticleService articleService;

	// Constructors --------------------------------------------------

	public NewspaperController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String keyword) {

		Collection<Newspaper> newspapers;

		if (keyword != null) {
			newspapers = newspaperService.findPerKeyword(keyword);
		} else {
			newspapers = newspaperService.findAvalibleNewspapers();
		}

		ModelAndView result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}

	// Display -------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int newspaperId, @RequestParam (required = false) String keyword) {

		Newspaper newspaper = newspaperService.findOne(newspaperId);
		Collection<Article> articles;
		
		if(keyword!=null){
			articles = this.articleService.findPerKeyword(keyword, newspaperId);
		}else{
			articles = newspaper.getArticles();
		}

		ModelAndView result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", newspaper);
		result.addObject("articles", articles);
		result.addObject("date", new Date());

		return result;

	}
}
