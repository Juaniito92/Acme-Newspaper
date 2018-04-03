
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserToStringConverter implements Converter<User, String> {

	@Override
	public String convert(final User user) {
		String result;

		if (user == null)
			result = null;
		else
			result = String.valueOf(user.getId());

		return result;
	}
}
