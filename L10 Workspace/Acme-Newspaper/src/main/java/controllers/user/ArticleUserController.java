package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.NewspaperRepository;
import services.ArticleService;
import controllers.AbstractController;

@Controller
@RequestMapping("/article/user")
public class ArticleUserController extends AbstractController{
	
	// Services --------------------------------
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperRepository newspaperRepository;
	
	// Constructors ----------------------------
	public ArticleUserController() {
		super();
	}
	
	// Create ----------------------------------------
	
//	public ModelAndView create(@RequestParam final int )
	
	// Edit ----------------------------------------
	
	// Save ----------------------------------------
}
