package Helpers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SqlDateFormatHelper {

	public static Calendar SQLDateStringToCalendar(String sqlDateString){
		String[] splitDateString = sqlDateString.split("-");
		
		int year = Integer.valueOf(splitDateString[0]);
		int month = Integer.valueOf(splitDateString[1]);
		int day = Integer.valueOf(splitDateString[2]);
		
		Calendar calendarDate = new GregorianCalendar(year, month, day);
		
		return calendarDate;
	}
	
	public static String CalendarToSqlDateString(Calendar calendar){
		String sqlStringDate = null;

		SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

		sqlStringDate = sqlFormat.format(calendar.getTime());
		
		return sqlStringDate;
	}
	
	public static boolean isValidSqlDateString(String dateString){
		try{
			java.sql.Date.valueOf(dateString);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
