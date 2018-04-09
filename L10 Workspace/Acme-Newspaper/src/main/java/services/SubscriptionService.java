package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.SubscriptionRepository;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------
	
	public SubscriptionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public void delete(Subscription subscription){
		
		Assert.notNull(subscription);
		Assert.isTrue(subscription.getId() != 0);
		
		subscription.getCustomer().getSubscriptions().remove(subscription);
		subscriptionRepository.delete(subscription);
		
	}
}
