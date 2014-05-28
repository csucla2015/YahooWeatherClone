package com.npi.blureffect;

public class ReviewObject {
	private String mBody;
	private String mAuthor;
	private String mRating;

	public ReviewObject(String body, String author, String rating) {
		mBody = body;
		mAuthor = author;
		mRating = rating;
	}

	public String getBody() {
		return mBody;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public String getRating() {
		return mRating;
	}
}
