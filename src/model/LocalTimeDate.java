package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class LocalTimeDate{


	public LocalDate localDate = LocalDate.now();
	public LocalTime localTime = LocalTime.now();


	public int year = localDate.getYear();
	public int month = localDate.getMonthValue();
	public int dayMonth = localDate.getDayOfMonth();

	public int hour = localTime.getHour();
	public int minutes = localTime.getMinute();
	public int seconds = localTime.getSecond();



	public String getTime(){
		return "Added on " + hour + ":" + minutes + "|" + dayMonth + "/" + month + "/" + year + "." ;
	}

	public String getDate(){
		return month + "/" + dayMonth + "/" + year;
	}


}
