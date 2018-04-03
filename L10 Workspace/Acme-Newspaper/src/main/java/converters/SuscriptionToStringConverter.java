
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SuscriptionToStringConverter implements Converter<Suscription, String> {

	@Override
	public String convert(final Suscription suscription) {
		String result;

		if (suscription == null)
			result = null;
		else
			result = String.valueOf(suscription.getId());

		return result;
	}
}
