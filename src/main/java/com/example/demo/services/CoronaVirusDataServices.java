package com.example.demo.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.LocationStates;

@Service
public class CoronaVirusDataServices 
{
    public CoronaVirusDataServices() {
        System.out.println("Data Service Invoked");
    }

    private List<LocationStates> allstates = new ArrayList<LocationStates>();
    
    public List<LocationStates> getAllstates() {
		return allstates;
	}
    public void setAllstates(List<LocationStates> allstates){
    	this.allstates = allstates;
    }
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *") // Run at 1 AM every day
    public void fetchVirusData() throws IOException, InterruptedException
    {
        List<LocationStates> newstates = new ArrayList<LocationStates>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        
        for (CSVRecord record : records) 
        {
            LocationStates state = new LocationStates();
            state.setState(record.get("Province/State"));
            state.setCountry(record.get("Country/Region"));
            
            // Access columns by their respective names
            int latestCase = Integer.parseInt(record.get(record.size()-1));
            int prevCase = Integer.parseInt(record.get(record.size()-2)); 
            
            
            state.setLatestTotalDeaths(latestCase);
            state.setDifferFromPrevday(latestCase-prevCase); 
            
            System.out.println(state);
            newstates.add(state);
           
            
        }
        
        this.allstates = newstates;
    }

	
}
