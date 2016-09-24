package app.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date> {

	@Autowired
    private Logger logger = Logger.getLogger(DateFormatter.class.getName());
	
	public Date parse(final String text, final Locale locale) throws ParseException {
		
		logger.info("executando parse do DateFormatter");
		
		final SimpleDateFormat dateFormat = dateFormat();
		return dateFormat.parse(text);
	}

	public String print(final Date object, final Locale locale) {
		
		logger.info("executando print do DateFormatter");
		
		final SimpleDateFormat dateFormat = dateFormat();
		return dateFormat.format(object);
	}

	private SimpleDateFormat dateFormat() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		return dateFormat;
	}

}