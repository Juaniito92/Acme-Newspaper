
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors

	public Configuration() {
		super();
	}


	// Attributes

	private String	tabooWords;


	@SafeHtml
	public String getTabooWords() {
		return this.tabooWords;
	}

	public void setTabooWords(final String tabooWords) {
		this.tabooWords = tabooWords;
	}

}
