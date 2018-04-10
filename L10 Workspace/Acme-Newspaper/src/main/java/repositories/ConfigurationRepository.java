package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
	
	@Query("select tw from Configuration c join c.tabooWords tw")
	Collection<String> findSpamWords();
	
	@Query("select max(id) from Configuration")
	Integer maxIdConfiguration();
}
