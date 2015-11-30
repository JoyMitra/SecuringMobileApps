package com.example.idost.pojo;

import java.util.HashMap;


public class ContactBean 
{
private String Name;
private String Phn;

public ContactBean(String name,String phn)
{
	this.Name=name;
	this.Phn=phn;
}

public String getPhn()
{
	return Name;
}

public String getName()
{
	return Phn;
}

public static HashMap<String, String> ContactMap ;

public static String showMsg;}
