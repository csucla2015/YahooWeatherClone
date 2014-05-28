package com.npi.blureffect;

public class SearchBookRecord {
	private String mTitle;
	private String mAuthor;
	private String mYear;
	private int mKey;
	private boolean mAvailability;
	private String mISBN;
	private String mDescript;
	private String mAvailDescript;
	private boolean mCheckedAvail;
	private String mOCLC;

	public SearchBookRecord(String title, String author, String year,
			boolean availability, String availDescript, String oclc,
			String isbn, String descript, int key) {
		mTitle = title;
		mAuthor = author;
		mKey = key;
		mYear = year;
		mAvailability = availability;
		mAvailDescript = availDescript;
		mISBN = isbn;
		mDescript = descript;
		mCheckedAvail = false;
		mOCLC = oclc;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public String getYear() {
		return mYear;
	}

	public boolean getAvailability() {
		return mAvailability;
	}

	public String getAvailDescript() {
		return mAvailDescript;
	}

	public void setAvailDescript(String x) {
		mAvailDescript = x;
	}

	public void setAvailability(boolean available) {
		mAvailability = available;
		mCheckedAvail = true;
	}

	public String getOCLC() {
		return mOCLC;
	}

	public boolean getIfCheckedAvail() {
		return mCheckedAvail;
	}

	public int getKey() {
		return mKey;
	}

	public String getISBN() {
		return mISBN;
	}

	public String getDescript() {
		return mDescript;
	}
}
