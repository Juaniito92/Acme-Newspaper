package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository
	@Autowired
	private ActorRepository actorRepository;

	// Constructors
	public ActorService() {
		super();
	}

	// Ancillary methods

	public Actor findActorByUserAccountId(int uA) {

		Actor actor = actorRepository.findActorByUserAccountId(uA);
		return actor;
	}

	public Actor findByPrincipal() {

		Actor actor;

		UserAccount principalUserAccount = LoginService.getPrincipal();
		if (principalUserAccount == null) {
			actor = null;
		} else {
			actor = actorRepository
					.findActorByUserAccountId(principalUserAccount.getId());
		}
		
		return actor;
	}

}
