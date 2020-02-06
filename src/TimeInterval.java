import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Name: Pongpeera Sukasem
 * StudentID: 5988040
 * Section: 1 (Group 1)
 */

public class TimeInterval {
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat shortTimeFormat = new SimpleDateFormat("HH");
	
	public Time startTime;
	public Time endTime;
	
	// in case the string input is "8:00-20:30"
	public TimeInterval(String bothStartEnd){
		String[] temp = bothStartEnd.split("-");
		setStartTimeEndTime(temp[0], temp[1]);
	}
	
	// in case the start string is "8:00" and end string is "20;00"
	public TimeInterval(String start, String end){
		setStartTimeEndTime(start,end);	
	}
	
	public TimeInterval(){//In case of "close", set to prevent NullPointerException
		startTime = null;
		endTime = null;
	}
	
	/*
	 * convert string time into startTime and EndTime
	 * 
	 */
	public void setStartTimeEndTime(String start, String end){
		// YOUR CODE GOES HERE
		startTime = new Time(0);
		endTime = new Time(0);
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			//Check 2 cases: shortFormat or LongFormat (start-case, then end-case)
			if(start.contains(":")){
				startDate = timeFormat.parse(start);
			}
			else{
				startDate = shortTimeFormat.parse(start);
			}
			if(end.contains(":")){
				endDate = timeFormat.parse(end);
			}
			else{
				endDate = shortTimeFormat.parse(end);
			}
				startTime.setTime(startDate.getTime());
				endTime.setTime(endDate.getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
	
	/*
	 * to check whether this TimeInterval contains the current time or not
	 * For example, if the TimeInteval is from 8:00 - 20:00 and the current time is 9:00, 
	 * this method will return true. If the current time is 22:00, this method will return false.
	 */
	public boolean contains(String str){
		// YOUR CODE GOES HERE
		Date str2 = new Date();//Object to receive parsed time
		Time comp = new Time(0);//Current time
		try {
			if(str.contains(":")){
				str2 = timeFormat.parse(str);
			}
			else{
				str2 = shortTimeFormat.parse(str);
			}
			comp.setTime(str2.getTime());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(comp.after(startTime) && comp.before(endTime)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String toString(){
		if(startTime == null || endTime == null)
			return "";
		else
			return timeFormat.format(startTime) + "-" + timeFormat.format(endTime);
	}
	
	
	public static void main(String[] args){
		
		// implement the main method to test this class
		
		TimeInterval t = new TimeInterval("8-22:30");
		System.out.println(t.toString());
		Date now = new Date();
		Time time = new Time(now.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		System.out.println(sdf.format(time));
		if(t.contains(sdf.format(now))){
			System.out.println("still open " + now.getDay());
		} else{
			System.out.println("already closed");
		}
	}
}
