package data_classes;

import java.util.Calendar;

public class RevenueReport { 
	
	private int _numReservations;
	private int _revenue; 
	private ReportType _reportType;
	
	private Calendar _date; 
	
	public static enum ReportType { 
		MONTH, DAY, YEAR
	}
	
	public RevenueReport(int numReservations, int revenue, ReportType type, Calendar date) { 
		_numReservations = numReservations;
		_revenue = revenue;
		_reportType = type;
		_date = date;
	}
	
	public int getNumReservations() { 
		return _numReservations;
	}

	public int getRevenue() { 
		return _revenue;
	}
	
	public ReportType getReportType() { 
		return _reportType;
	}
	
	public Calendar getDate() { 
		return _date;
	}
	
}