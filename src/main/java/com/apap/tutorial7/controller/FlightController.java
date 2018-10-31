package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.service.FlightService;
import com.apap.tutorial7.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @PostMapping(value = "/add")
    public FlightModel addFlightSubmit(@RequestBody FlightModel flight){
    	return flightService.addFlight(flight);
    }
    
    @PutMapping(value = "/update/{flightId}")
    public String updatePilotSubmit(@PathVariable("flightId") long flightId,
    		@RequestParam(value="destination", required=false) String destination,
    		@RequestParam(value="origin", required=false) String origin,
    		@RequestParam(value="time", required=false) Date time) {
    	FlightModel flight = flightService.getFlightDetailById(flightId);
    
    	if(flight.equals(null)) {
    		return "Couldn't find the flight";
    	}
    	
    	System.out.println(destination);
    	if(destination != null) {
    		System.out.println(destination);
    		flight.setDestination(destination);
    	} 
    	if(origin != null) flight.setOrigin(origin);
    	if(time != null) flight.setTime(time);
    	flightService.updateFlight(flightId, flight);
    	return "Flight has been updated";
    }
    
    @GetMapping(value = "/view/{flightNumber}")
    public FlightModel flightView(@PathVariable("flightNumber") String flightNumber) {
    	FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
    	System.out.println(flight);
    	return flight;
    }
    
    @GetMapping(value = "/all")
    public List<FlightModel> flightViewAll() {
    	List<FlightModel> listFlight = flightService.getAllFlight();
    	return listFlight;
    }
    
    @DeleteMapping(value = "/delete")
    public String deleteFlight(@RequestParam("flightId") long flightId) {
    	
    	FlightModel flight = flightService.getFlightDetailById(flightId);
    	System.out.println(flight);
    	flightService.deleteFlight(flight);
    	return "Flight has been deleted";    
    }
    
//    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
//    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
//        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber).get();
//        pilot.setListFlight(new ArrayList<FlightModel>(){
//            private ArrayList<FlightModel> init(){
//                this.add(new FlightModel());
//                return this;
//            }
//        }.init());
//
//        model.addAttribute("pilot", pilot);
//        return "add-flight";
//    }
//
//    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"addRow"})
//    private String addRow(@ModelAttribute PilotModel pilot, Model model) {
//        pilot.getListFlight().add(new FlightModel());
//        model.addAttribute("pilot", pilot);
//        return "add-flight";
//    }
//
//    @RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"removeRow"})
//    public String removeRow(@ModelAttribute PilotModel pilot, Model model, HttpServletRequest req) {
//        Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
//        pilot.getListFlight().remove(rowId.intValue());
//        
//        model.addAttribute("pilot", pilot);
//        return "add-flight";
//    }
//
//    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
//    private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
//        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).get();
//        for (FlightModel flight : pilot.getListFlight()) {
//            if (flight != null) {
//                flight.setPilot(archive);
//                flightService.addFlight(flight);
//            }
//        }
//        return "add";
//    }
//
//
//    @RequestMapping(value = "/flight/view", method = RequestMethod.GET)
//    private @ResponseBody FlightModel view(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
//        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
//        return archive;
//    }
//
//    @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
//    private String delete(@ModelAttribute PilotModel pilot, Model model) {
//        for (FlightModel flight : pilot.getListFlight()) {
//            flightService.deleteByFlightNumber(flight.getFlightNumber());
//        }
//        return "delete";
//    }
//
//    @RequestMapping(value = "/flight/update", method = RequestMethod.GET)
//    private String update(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
//        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
//        model.addAttribute("flight", archive);
//        return "update-flight";
//    }
//
//    @RequestMapping(value = "/flight/update", method = RequestMethod.POST)
//    private @ResponseBody FlightModel updateFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
//        flightService.addFlight(flight);
//        return flight;
//    }
}