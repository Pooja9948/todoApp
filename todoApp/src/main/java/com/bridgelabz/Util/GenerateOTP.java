package com.bridgelabz.Util;

public class GenerateOTP {
	/*public String otp;
	public Date date;

	public String OTP() {
		date = new Date();
		int randomNumber = (int) (Math.random() * 9000) + 1000;
		otp = String.valueOf(randomNumber);

		return otp;
	}

	public boolean validateOtp(String enterOtp) {
		long diff = (new Date().getTime()) - date.getTime();
		long difference = TimeUnit.MILLISECONDS.toMinutes(diff);
		System.out.println("difference " + difference);
		System.out.println("date.gettime() " + date.getTime());
		System.out.println(enterOtp);
		System.out.println(otp);
		if (enterOtp.equalsIgnoreCase(otp) && difference < 1) {
			return true;
		}
		return false;
	}*/
	public static String generateOTP() {
		int randomPin   =(int)(Math.random()*9000)+1000;
		String otp  =String.valueOf(randomPin);
		return otp;
	}
}
