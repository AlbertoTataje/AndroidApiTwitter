package com.mejorandola.android.utils;

public class ConstantsUtils {
	
	public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
	public static final String URL_SEARCH = URL_ROOT_TWITTER_API + "/1.1/search/tweets.json?q=";
	public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API + "/oauth2/token";

	public static final String CONSUMER_KEY ="EzyNXYNmzwMjs0YfbBGZ5s0Ok";
	public static final String CONSUMER_SECRET ="kiHqC1Ou8YfmGtPgv3eQde89sM3GdB7gWkC3YyK4QnfExbjjtz";
	
	public String MEJORANDROID_TERM = "mejorandroid";

	public static final String NEW_TWEETS_INTENT_FILTER = "com.mejorandroid.android.NEW_TWEETS";

	public String getMEJORANDROID_TERM() {
		return MEJORANDROID_TERM;
	}

	public void setMEJORANDROID_TERM(String mEJORANDROID_TERM) {
		MEJORANDROID_TERM = mEJORANDROID_TERM;
	}

	

	

}
