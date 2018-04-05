
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.UserService;
import domain.Chirp;
import domain.User;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ChirpService	chirpService;


	// Constructors ---------------------------------------------------------

	public ChirpController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chirp> chirps;

		chirps = this.chirpService.findAll();

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/list.do");

		return result;
	}

	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.findOne(chirpId);

		result = new ModelAndView("user/display");
		result.addObject("chirp", chirp);
		result.addObject("requestURI", "chirp/display.do");

		return result;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int chirpId) {
		ModelAndView result;
		final Chirp chirp = this.chirpService.findOne(chirpId);

		result = this.createEditModelAndView(chirp);					//Cambiar por el de Editar

		return result;
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid final Chirp c, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(c, "user.params.error");	//Cambiar por el de Editar			
		else
			try {
				this.chirpService.save(c);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(c, "user.commit.error");	//Cambiar por el de editar
			}
		System.out.println(binding);

		return res;
	}

	// Registering ----------------------------------------------------------

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.create();
		result = this.createEditModelAndView(chirp);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Chirp chirp, final BindingResult binding) {
		ModelAndView res;
		final User user;

		if (binding.hasErrors())
			res = this.createEditModelAndView(chirp, "user.params.error");
		else
			try {
				this.chirpService.save(chirp);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(chirp, "user.commit.error");
			}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp) {
		ModelAndView result;

		result = this.createEditModelAndView(chirp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp, final String message) {
		ModelAndView result;

		result = new ModelAndView("/user/chirp/edit");
		result.addObject("chirp", chirp);
		result.addObject("message", message);

		return result;
	}
}
