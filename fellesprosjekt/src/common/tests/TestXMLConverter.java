package common.tests;

import java.util.Date;
import org.w3c.dom.Document;
import common.util.XMLConverter;

public class TestXMLConverter 
{
	public static void main(String[] args)
	{
		testDateConversion();
	}
	
	public static void testUserConversion()
	{

	}
	
	public static void testNotificationConversion()
	{

	}
	
	public static void testDateConversion()
	{
		Date date1, date2;
		Document doc;
		XMLConverter converter 	= new XMLConverter();
		
		doc		= converter.getNewDocument();
		date1 	= new Date();
		
		converter.dateToDOMElement(date1, doc, null);
		
		date2 = (Date) converter.genericObjectBuilder(doc.getFirstChild());
		
		System.out.println(date1);
		System.out.println(date2);
	}
}
