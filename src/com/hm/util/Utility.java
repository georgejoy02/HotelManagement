package com.hm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.sql.Date;

public class Utility {

	public static Date convertStringToSQLDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			java.util.Date javaDate = sdf.parse(strDate);
			Date sqlDate = new Date(javaDate.getTime());
			return sqlDate;

		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}
	
	public String formatTimestampToDate(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timestamp);
    }

}
