package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "location_states")
public class LocationStates 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "country_id")
	int country_id ;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "latesttotaldeaths")
	private int latestTotalDeaths;
	@Column(name = "differfromprevay")
	private int differFromPrevday;
	
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	public int getDifferFromPrevday() {
		return differFromPrevday;
	}
	public void setDifferFromPrevday(int differFromPrevday) {
		this.differFromPrevday = differFromPrevday;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalDeaths() {
		return latestTotalDeaths;
	}
	public void setLatestTotalDeaths(int latestTotalDeaths) {
		this.latestTotalDeaths = latestTotalDeaths;
	}
	
	
	@Override
	public String toString() {
		return "LocationStates [country_id=" +country_id+ ",state=" + state + ", country=" + country + ", latestTotalDeaths=" + latestTotalDeaths
				+"Differfromprevay="+differFromPrevday+ "]";
	}
	
	

	
}
