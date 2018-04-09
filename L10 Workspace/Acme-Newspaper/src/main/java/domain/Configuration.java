
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors

	public Configuration() {
		super();
	}


	// Attributes

	private Collection<String>	tabooWords;


	@NotEmpty
	@ElementCollection
	public Collection<String> getTabooWords() {
		return this.tabooWords;
	}

	public void setTabooWords(final Collection<String> tabooWords) {
		this.tabooWords = tabooWords;
	}

}
