package com.wish.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wish.controller.CustomerRepository;

@Service
public class NotificationScheduler {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 0 8 * * ?") // Runs daily at 8 AM 
	public void checkEvents() {
	    LocalDate tomorrow = LocalDate.now().plusDays(1);

	    // Check birthdays
	    List<Customer> birthdayCustomers = (List<Customer>) customerRepo.findByDob(tomorrow);
	    for (Customer customer : birthdayCustomers) {
	        String message = generateMessage(customer, "Birthday");
	        emailService.sendWish(customer.getEmail(), "Birthday", message);
	        

	    }

	    // Check anniversaries
	    List<Customer> anniversaryCustomers = (List<Customer>) customerRepo.findByAnniversary(tomorrow);
	    for (Customer customer : anniversaryCustomers) {
	        String message = generateMessage(customer, "Anniversary");
	        emailService.sendWish(customer.getEmail(), "Anniversary", message);
	    }
	}

	public String generateMessage(Customer customer, String occasion) {
	    switch (occasion) {
	        case "Birthday":
	            return "Happy Birthday " + customer.getName() + "! 🎉\n\n" +
	                   "Wishing you a joyful year ahead.\n\n" +
	                   "Best wishes,\nYour Company";
	        case "Anniversary":
	            return "Happy Anniversary " + customer.getName() + "! 💍\n\n" +
	                   "Wishing you many more years of love and happiness.\n\n" +
	                   "Warm regards,\nYour Company";
	        default:
	            return "Hello " + customer.getName() + ",\n\n" +
	                   "Best wishes from all of us at Your Company.";
	    }
	}



	/*
	 * public void checkEvents() { LocalDate tomorrow = LocalDate.now().plusDays(1);
	 * 
	 * // Process birthdays sendGreetingsForEvent(customerRepo.findByDob(tomorrow),
	 * "Birthday");
	 * 
	 * // Process anniversaries
	 * sendGreetingsForEvent(customerRepo.findByAnniversary(tomorrow),
	 * "Anniversary"); }
	 * 
	 * private void sendGreetingsForEvent(List<Customer> customers, String
	 * eventType) { if (customers == null || customers.isEmpty()) { return; }
	 * 
	 * for (Customer customer : customers) { String message =
	 * generateMessage(customer); String email = customer.getEmail();
	 * emailService.sendWish(email, eventType, message); } }
	 */

}