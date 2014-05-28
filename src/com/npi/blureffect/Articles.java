package com.npi.blureffect;

public class Articles
{
    String ArticleName;
    String AuthorName;
    String date;
    String url;
    String year;
    String databaseName;
    String pubSource;
    public Articles(String name, String aname,String year1, String url1,String databaseName1, String pubSoruce1)
    {
        ArticleName = name;
        AuthorName = aname;
        year = year1;
        url = url1;
        databaseName = databaseName1;
        pubSource = pubSoruce1;
    }
    public String getName()
    {
        return ArticleName;
    }
   
    public String getAuthorName()
    {
    	return AuthorName;
    }
    public String getYear()
    {
    	return year;
    }
    public String getUrl()
    {
    	return url;
    }
    public String getdatabaseName()
    {
    	return databaseName;
    }
    public String getpubSource()
    {
    	return pubSource;
    }	
    
    
    
}