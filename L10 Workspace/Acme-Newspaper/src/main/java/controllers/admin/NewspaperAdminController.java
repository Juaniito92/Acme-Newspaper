package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import controllers.AbstractController;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/admin")
public class NewspaperAdminController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private AdminService adminService;

	// Constructors --------------------------------------------------

	public NewspaperAdminController() {
		super();
	}

	// Delete -------------------------------------------------------
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int newspaperId) {

		Assert.notNull(this.adminService.findByPrincipal());
		
		ModelAndView result;
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);

		try{
			this.newspaperService.delete(newspaper);
		}catch(Throwable oops){
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "newspaper.commit.error");
		}
		
		return result;
	}

}
