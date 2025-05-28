package com.wish.controller;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wish.model.Customer;
import com.wish.model.EmailService;
import com.wish.model.NotificationScheduler;
import com.wish.model.ResourceNotFoundException;
import com.wish.model.WishStatus;


@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private EmailService emailService;

	@Autowired
	private NotificationScheduler nscheduler;
	@Autowired
	private WishStatusRepository wishStatusRepo;
	
	
    @RequestMapping("/")
    public String home() {
       return "index"; // This looks in templates/index.html, not static/
    	//return "forward:/index.html";
    }

	@PostMapping("/addcustomers")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerRepo.save(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomer() {
	    List<Customer> customers = customerRepo.findAll();
	    return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(Long Id) {
		Customer customer = customerRepo.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", Id));
        return ResponseEntity.ok(customer);
    }

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Validated  @RequestBody Customer updatedCustomer) {

		Customer customer = customerRepo.findById(updatedCustomer.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedCustomer.getId()));
		customer.setName(updatedCustomer.getName());
		customer.setEmail(updatedCustomer.getEmail());
		customer.setDob(updatedCustomer.getDob());
		customer.setAnniversary(updatedCustomer.getAnniversary());
		customerRepo.save(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		if (customerRepo.existsById(id)) {
			customerRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * @PostMapping("/customers/{id}/send-wish") public ResponseEntity<String>
	 * sendWishManually(@PathVariable Long id, @RequestParam String occasion) {
	 * 
	 * @SuppressWarnings("unchecked") Optional<Customer> customerOpt =
	 * (Optional<Customer>) customerRepo.findById(id);
	 * 
	 * if (customerOpt.isPresent()) { Customer customer = customerOpt.get(); String
	 * message = nscheduler.generateMessage(customer, occasion);
	 * emailService.sendWish(customer.getEmail(), occasion, message); return
	 * ResponseEntity.ok("Wish sent successfully to " + customer.getEmail()); } else
	 * { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found"); } }
	 */

	@PostMapping("/customers/{id}/send-wish")
    public ResponseEntity<String> sendWishManually(@PathVariable Long id, @RequestParam String occasion) {
        Optional<Customer> customerOpt = customerRepo.findById(id);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            String message = nscheduler.generateMessage(customer, occasion);

            try {
                emailService.sendWish(customer.getEmail(), occasion, message);
                wishStatusRepo.save(new WishStatus(customer.getId(), customer.getEmail(), occasion, message, 2));
                return ResponseEntity.ok("Wish sent successfully to " + customer.getEmail());
            } catch (Exception e) {
                wishStatusRepo.save(new WishStatus(customer.getId(), customer.getEmail(), occasion, message, 1));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
            }

        } else {
            wishStatusRepo.save(new WishStatus(id, "N/A", occasion, "", 1));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }
	/*
	 * @PostMapping("/customers/{id}/send-wish") public ResponseEntity<String>
	 * sendWishManually(@PathVariable Long id, @RequestParam String occasion) {
	 * return customerRepo.findById(id) .map(customer -> { String message =
	 * generateMessage(customer, occasion);
	 * emailService.sendWish(customer.getEmail(), occasion, message); return new
	 * ResponseEntity<>("Wish sent successfully to " + customer.getEmail(),
	 * HttpStatus.OK); }) .orElseGet(() -> new
	 * ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND)); }
	 */

	@GetMapping("/upcomings")
	public List<Customer> getUpcomingEventss() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		return customerRepo.findUpcomingEvents(tomorrow);
	}
    @GetMapping("/customers/birthdays-today")
    public List<Customer> getTodaysBirthdays() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        return customerRepo.findAll().stream()
                .filter(c -> c.getDob() != null &&
                             c.getDob().format(DateTimeFormatter.ofPattern("MM-dd")).equals(today))
                .collect(Collectors.toList());
    }

    @GetMapping("/customers/anniversaries-today")
    public List<Customer> getTodaysAnniversaries() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        return customerRepo.findAll().stream()
                .filter(c -> c.getAnniversary() != null &&
                             c.getAnniversary().format(DateTimeFormatter.ofPattern("MM-dd")).equals(today))
                .collect(Collectors.toList());
    }

	/*
	 * // GET /upcoming?date=2025-05-25
	 * 
	 * @GetMapping("/upcoming") public List<Customer> getUpcomingEvents(
	 * 
	 * @RequestParam(required = false)
	 * 
	 * @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
	 * 
	 * LocalDate filterDate = (date != null) ? date : LocalDate.now().plusDays(1);
	 * return customerRepo.findByEventDateGreaterThanEqual(filterDate); }
	 */
    @GetMapping("/upcomingss")
    public List<Customer> getUpcomingEvents(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob) {

        LocalDate targetDate = (dob != null) ? dob : LocalDate.now();
        return customerRepo.findByDobMonthAndDay(targetDate.getMonthValue(), targetDate.getDayOfMonth());
    }

    // Helper method to check if event date's month-day is within the range [today, limitDate]
    @SuppressWarnings("unused")
	private boolean isDateInRange(LocalDate eventDate, LocalDate start, LocalDate end) {
        int eventMonthDay = eventDate.getMonthValue() * 100 + eventDate.getDayOfMonth();
        int startMonthDay = start.getMonthValue() * 100 + start.getDayOfMonth();
        int endMonthDay = end.getMonthValue() * 100 + end.getDayOfMonth();

        if (startMonthDay <= endMonthDay) {
            // Normal case (range in same year)
            return eventMonthDay >= startMonthDay && eventMonthDay <= endMonthDay;
        } else {
            // Range wraps year end (e.g., Dec 28 to Jan 7)
            return eventMonthDay >= startMonthDay || eventMonthDay <= endMonthDay;
        }
    }
    
    @GetMapping("/upcoming")
    public List<Map<String, Object>> getUpcomingEvents(@RequestParam(defaultValue = "10") int days) {
        LocalDate today = LocalDate.now();
        LocalDate limitDate = today.plusDays(days);

        List<Customer> allEvents = customerRepo.findAll();
        List<Map<String, Object>> upcomingEvents = new ArrayList<>();
        
        for (Customer c : allEvents) {
            // Adjust DOB to current year
            LocalDate dob = c.getDob() != null ? c.getDob().withYear(today.getYear()) : null;
            if (dob != null && isDateInRange(dob, today, limitDate)) {
                Map<String, Object> birthdayEvent = new HashMap<>();
                birthdayEvent.put("id", c.getId());
                birthdayEvent.put("phone", c.getPhone());
                birthdayEvent.put("name", c.getName());
                birthdayEvent.put("email", c.getEmail());
                birthdayEvent.put("eventDate", dob.toString());
                birthdayEvent.put("eventType", "Birthday");
               try {
                    Optional<WishStatus> wishStatusOpt = wishStatusRepo.findByCustomerId(c.getId());

                    if (wishStatusOpt.isPresent()) {
                    	birthdayEvent.put("status", wishStatusOpt.get().getStatus());
                    } else {
                    	birthdayEvent.put("status", 0); // Or 0 / "Pending" based on your logic
                    }
                } catch (Exception e) {
                    // Log the error (if using a logger) and add a fallback
                    // log.error("Error fetching WishStatus for customer {}", c.getId(), e);
                	birthdayEvent.put("status", 0);
                }
                upcomingEvents.add(birthdayEvent);
            }

            // Adjust anniversary to current year
            LocalDate anniversary = c.getAnniversary() != null ? c.getAnniversary().withYear(today.getYear()) : null;
            if (anniversary != null && isDateInRange(anniversary, today, limitDate)) {
                Map<String, Object> anniversaryEvent = new HashMap<>();
                anniversaryEvent.put("id", c.getId());
                anniversaryEvent.put("phone", c.getPhone());
                anniversaryEvent.put("name", c.getName());
                anniversaryEvent.put("email", c.getEmail());
                anniversaryEvent.put("eventDate", anniversary.toString());
                anniversaryEvent.put("eventType", "Anniversary");
              try {
                        Optional<WishStatus> wishStatusOpt = wishStatusRepo.findByCustomerId(c.getId());

                        if (wishStatusOpt.isPresent()) {
                            anniversaryEvent.put("status", wishStatusOpt.get().getStatus());
                        } else {
                            anniversaryEvent.put("status", 0); // Or 0 / "Pending" based on your logic
                        }
                    } catch (Exception e) {
                        // Log the error (if using a logger) and add a fallback
                        // log.error("Error fetching WishStatus for customer {}", c.getId(), e);
                        anniversaryEvent.put("status", 0);
                    }

                    upcomingEvents.add(anniversaryEvent);
                }

            }

        return upcomingEvents;
    }



}
