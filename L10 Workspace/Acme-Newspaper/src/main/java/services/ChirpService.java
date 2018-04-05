
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;
import domain.User;

@Service
@Transactional
public class ChirpService {

	// Managed repository

	@Autowired
	private ChirpRepository	chirpRepository;

	// Supporting services

	@Autowired
	private UserService		userService;


	// Constructors

	public ChirpService() {
		super();
	}

	// Simple CRUD methods

	public Chirp create() {
		final Chirp res = new Chirp();

		final Date publicationMoment;
		final User user;

		user = this.userService.findByPrincipal();
		publicationMoment = new Date(System.currentTimeMillis() - 1);

		res.setPublicationMoment(publicationMoment);
		res.setUser(user);

		return res;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> res;
		res = this.chirpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Chirp findOne(final int chirpId) {
		Assert.isTrue(chirpId != 0);
		Chirp res;
		res = this.chirpRepository.findOne(chirpId);
		Assert.notNull(res);
		return res;
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);
		Chirp res;

		final Date publicationMoment;
		final User user;
		Collection<Chirp> chirps;

		user = this.userService.findByPrincipal();
		publicationMoment = new Date(System.currentTimeMillis() - 1);
		chirps = user.getChirps();

		chirp.setPublicationMoment(publicationMoment);
		chirp.setUser(user);
		res = this.chirpRepository.save(chirp);
		chirps.add(res);
		user.setChirps(chirps);

		return res;
	}

	// Other business methods

}
