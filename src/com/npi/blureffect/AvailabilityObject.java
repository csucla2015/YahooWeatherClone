package com.npi.blureffect;

public class AvailabilityObject {
	private String mLocation;
	private String mCallNo;
	private String mStatus;
	private String mNumber;

	public AvailabilityObject(String location, String callno, String status, String number) {
		mLocation = location;
		mCallNo = callno;
		mStatus = status;
		mNumber = number;
	}

	public String getLocation() {
		return mLocation;
	}

	public String getCallNo() {
		return mCallNo;
	}

	public String getStatus() {
		return mStatus;
	}
	
	public String getNumber(){
		return mNumber;
	}
}
