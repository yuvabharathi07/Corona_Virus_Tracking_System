package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.LocationStates;
import com.example.demo.services.CoronaVirusDataServicerepository;
import com.example.demo.services.CoronaVirusDataServices;



@Controller
public class HomeController 
{
	private  CoronaVirusDataServicerepository repository;

	public HomeController() {
		
		System.out.println("Home Contoller Invoked");
	}
	@Autowired
	public HomeController (CoronaVirusDataServicerepository repository) {
		this.repository = repository;
	}
	
	CoronaVirusDataServices crnService;
	
	@Autowired
	public void setCrnService(CoronaVirusDataServices crnService) {
		this.crnService = crnService;
	}


	@GetMapping("/")
	public String home(Model model)
	{
		List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		repository.saveAll(allstates);
		return "home";
	}
	
	@RequestMapping(path = "/collectChartData", produces = "application/json")
	@ResponseBody
	public List<LocationStates> collectChartData(Model model) {
	    System.out.println("Chart Data Viewed");
	    List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		return allstates;
	}


	@RequestMapping(path = "/collectChartData/{id}",produces = "application/json")
	@ResponseBody
	public Optional<LocationStates> collectChartDataByCountryId(@PathVariable("id") int country_id,Model model) {
		System.out.println("Chart Data Viewed by CountryID");
		Optional<LocationStates> locationstate=repository.findById(country_id);
		return locationstate;
	}
	
	@RequestMapping(path = "/collectChartData/country={name}",produces = "application/json")
	@ResponseBody
	public LocationStates collectChartDataByCountryName(@PathVariable("name") String countryname,Model model) {
		System.out.println("Chart Data Viewed by Country Name");
		LocationStates locationstate = repository.findBycountry(countryname);
		return locationstate;
	}
	@GetMapping("/viewChart")
	public String viewChart(Model model) {
	List<LocationStates> allstates = crnService.getAllstates();
	 // Create a list of maps with country name and total deaths
		 	//List<Map<String, Object>> chartData = new ArrayList<>();
			int totalDeathsReported = allstates.stream().mapToInt(stat -> stat.getLatestTotalDeaths()).sum();
			Map<String, Integer> hmap = new HashMap<>();
			for (int i = 0; i < allstates.size(); i++) {
				hmap.put(allstates.get(i).getCountry(), allstates.get(i).getLatestTotalDeaths());
			}
			model.addAttribute("country", hmap.keySet());
			model.addAttribute("DeathCount", hmap.values());
			return "ViewChart";
		    }
			
	@RequestMapping("/viewChart/{id}")
	public String viewChartById(@PathVariable("id") int id, Model model) {
	    Optional<LocationStates> locationState = repository.findById(id);

	    if (locationState.isPresent()) {
	        LocationStates state = locationState.get();
	        Map<String, Integer> hmap = new HashMap<>();
	        hmap.put(state.getCountry(), state.getLatestTotalDeaths());
	        model.addAttribute("country", hmap.keySet());
	        model.addAttribute("DeathCount", hmap.values());
	        // Return the view name
	    }else {
	    	model.addAttribute("country", "CountryNotFound");
	    	model.addAttribute("DeathCount", 0);
	    }
	    return "ViewChart";
	}
 
	@GetMapping("/viewChart/country={name}")
    public String viewChartByCountryName(@PathVariable("name") String name, Model model) {
        Optional<LocationStates> locationStateOptional = Optional.ofNullable(repository.findBycountry(name));

        if (locationStateOptional.isPresent()) {
            LocationStates state = locationStateOptional.get();
            model.addAttribute("country", state.getCountry());
            model.addAttribute("DeathCount", state.getLatestTotalDeaths());
        } else {
            // Handle the case when the country data is not found
            model.addAttribute("country", "Country Not Found");
            model.addAttribute("DeathCount", 0); // You can set a default value here
        }

        return "ViewChart";
    }
}
