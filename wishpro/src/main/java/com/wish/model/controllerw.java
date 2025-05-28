/*
 * package com.example.demo.controller;
 * 
 * import com.example.demo.model.Customer; import
 * com.example.demo.repository.CustomerRepository; import
 * com.example.demo.service.EmailService; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.*; import org.springframework.web.bind.annotation.*;
 * 
 * import java.time.LocalDate; import java.util.List;
 * 
 * @RestController
 * 
 * @RequestMapping("/api") public class CustomerController {
 * 
 * @Autowired private CustomerRepository customerRepo;
 * 
 * @Autowired private EmailService emailService;
 * 
 * // Add new customer
 * 
 * @PostMapping("/customers") public ResponseEntity<Customer>
 * addCustomer(@RequestBody Customer customer) { Customer savedCustomer =
 * customerRepo.save(customer); return new ResponseEntity<>(savedCustomer,
 * HttpStatus.CREATED); }
 * 
 * // Update existing customer
 * 
 * @PutMapping("/customers/{id}") public ResponseEntity<Customer>
 * updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer)
 * { return customerRepo.findById(id) .map(customer -> {
 * customer.setFirstName(updatedCustomer.getFirstName());
 * customer.setLastName(updatedCustomer.getLastName());
 * customer.setEmail(updatedCustomer.getEmail());
 * customer.setDob(updatedCustomer.getDob());
 * customer.setAnniversary(updatedCustomer.getAnniversary()); Customer
 * savedCustomer = customerRepo.save(customer); return new
 * ResponseEntity<>(savedCustomer, HttpStatus.OK); }) .orElseGet(() -> new
 * ResponseEntity<>(HttpStatus.NOT_FOUND)); }
 * 
 * // Delete customer by ID
 * 
 * @DeleteMapping("/customers/{id}") public ResponseEntity<Void>
 * deleteCustomer(@PathVariable Long id) { if (customerRepo.existsById(id)) {
 * customerRepo.deleteById(id); return new
 * ResponseEntity<>(HttpStatus.NO_CONTENT); } else { return new
 * ResponseEntity<>(HttpStatus.NOT_FOUND); } }
 * 
 * // Manually send wishes by occasion
 * 
 * @PostMapping("/customers/{id}/send-wish") public ResponseEntity<String>
 * sendWishManually(@PathVariable Long id, @RequestParam String occasion) {
 * return customerRepo.findById(id) .map(customer -> { String message =
 * generateMessage(customer, occasion);
 * emailService.sendWish(customer.getEmail(), occasion, message); return new
 * ResponseEntity<>("Wish sent successfully to " + customer.getEmail(),
 * HttpStatus.OK); }) .orElseGet(() -> new
 * ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND)); }
 * 
 * // Automatically check and send wishes for tomorrow
 * 
 * @GetMapping("/customers/check-events") public void checkEvents() { LocalDate
 * tomorrow = LocalDate.now().plusDays(1);
 * 
 * List<Customer> birthdayCustomers = customerRepo.findByDob(tomorrow); for
 * (Customer customer : birthdayCustomers) { String message =
 * generateMessage(customer, "Birthday");
 * emailService.sendWish(customer.getEmail(), "Birthday", message); }
 * 
 * List<Customer> anniversaryCustomers =
 * customerRepo.findByAnniversary(tomorrow); for (Customer customer :
 * anniversaryCustomers) { String message = generateMessage(customer,
 * "Anniversary"); emailService.sendWish(customer.getEmail(), "Anniversary",
 * message); } }
 * 
 * // Generate message content based on occasion private String
 * generateMessage(Customer customer, String occasion) { switch (occasion) {
 * case "Birthday": return "Happy Birthday " + customer.getFirstName() +
 * "! 🎉\n\n" + "Wishing you a joyful year ahead.\n\n" +
 * "Best wishes,\nYour Company"; case "Anniversary": return "Happy Anniversary "
 * + customer.getFirstName() + "! 💍\n\n" +
 * "Wishing you many more years of love and happiness.\n\n" +
 * "Warm regards,\nYour Company"; default: return "Hello " +
 * customer.getFirstName() + ",\n\n" +
 * "Best wishes from all of us at Your Company."; } } }
 */