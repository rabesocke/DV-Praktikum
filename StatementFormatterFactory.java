package bavaria.hightech.statement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class StatementFormatterFactory {

	private Map<String, IStatementFormatter> formatter;
	
	public StatementFormatterFactory(Locale bankLocale){
		formatter = new Hashtable<String, IStatementFormatter>();
		
		Properties probs = new Properties();
		
		FileInputStream in;
		try {
			in = new FileInputStream("props/knoedelmitsosse.properties");
			probs.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String plainText = probs.getProperty("text/plain");
		String plainHTML = probs.getProperty("text/html");
		StatementFormatterTextPlain plain = null;
		StatementFormatterTextHtml htmlFormatter = null;
		try {
			plain = (StatementFormatterTextPlain) Class.forName(plainText).getConstructors()[0].newInstance(bankLocale);
			htmlFormatter = (StatementFormatterTextHtml) Class.forName(plainHTML).getConstructors()[0].newInstance(bankLocale);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Keine Klasse instanziert");
		} 
		
		formatter.put("text/plain", plain);
		formatter.put("text/html", htmlFormatter);


	}
	
	public IStatementFormatter getStatementFormatter(String mimeType){
		return formatter.get(mimeType);
	}
}
