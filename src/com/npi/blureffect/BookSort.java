package com.npi.blureffect;

import java.util.Comparator;





public class BookSort implements Comparator<SearchBookRecord> 
{
    public int compare(SearchBookRecord b1, SearchBookRecord b2)
    {
    	if(b1.getKey() == b2.getKey())
    		return 0;
    	else if(b1.getKey() < b2.getKey())
    		return -1;
    	else
    		return 1;
    		
    }
}
