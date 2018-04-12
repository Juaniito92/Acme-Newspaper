package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private AdminService adminService;

	// Constructors -----------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods

	public Configuration create() {
		Configuration configuration;
		configuration = new Configuration();
		return configuration;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> res;
		res = this.configurationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Configuration findOne(int configuration) {
		Assert.isTrue(configuration != 0);
		Assert.notNull(adminService.findByPrincipal());
		Configuration res;
		res = this.configurationRepository.findOne(configuration);
		Assert.notNull(res);
		return res;
	}

	public Configuration save(Configuration configuration) {
		Assert.notNull(configuration);
		Assert.notNull(adminService.findByPrincipal());
		Configuration res;
		res = this.configurationRepository.save(configuration);
		return res;
	}

	public void delete(Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(configuration.getId() != 0);
		Assert.isTrue(this.configurationRepository.exists(configuration.getId()));
		this.configurationRepository.delete(configuration);
	}
	
	public Collection<String> findSpamWords() {
		Collection<String> res;
		res = configurationRepository.findSpamWords();
		return res;
	}
	
	public Collection<String> getQuitarPosicionesVaciasTabooWords(Collection<String> tabooWords) {
		Collection<String> res = new ArrayList<>();
		for(String word: tabooWords){
			if(!word.isEmpty()){
				res.add(word);
			}
		}
		return res;
	}
	
	public void flush() {
		this.configurationRepository.flush();
	}
	
	public Collection<Configuration> findAllByAdmin() {
		
		Assert.notNull(adminService.findByPrincipal());
		
		Collection<Configuration> res;
		res = this.findAll();
		Assert.notNull(res);
		return res;
	}


}
