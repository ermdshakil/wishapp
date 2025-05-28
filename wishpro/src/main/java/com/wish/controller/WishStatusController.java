/*
 * package com.wish.controller;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.*;
 * 
 * import com.wish.model.WishStatus;
 * 
 * import java.util.List; import java.util.Optional;
 * 
 * @RestController
 * 
 * @RequestMapping("/api/wish-status") public class WishStatusController {
 * 
 * private final WishStatusService wishStatusService;
 * 
 * @Autowired public WishStatusController(WishStatusService wishStatusService) {
 * this.wishStatusService = wishStatusService; }
 * 
 * @PostMapping public WishStatus createWishStatus(@RequestBody WishStatus
 * wishStatus) { return wishStatusService.createWishStatus(wishStatus); }
 * 
 * @GetMapping public List<WishStatus> getAllWishStatuses() { return
 * wishStatusService.getAllWishStatuses(); }
 * 
 * @GetMapping("/{id}") public ResponseEntity<WishStatus>
 * getWishStatusById(@PathVariable Long id) { Optional<WishStatus> wishStatus =
 * wishStatusService.getWishStatusById(id); return
 * wishStatus.map(ResponseEntity::ok) .orElseGet(() ->
 * ResponseEntity.notFound().build()); }
 * 
 * @GetMapping("/customer/{customerId}") public Optional<WishStatus>
 * getWishStatusByCustomerId(@PathVariable Long customerId) { return
 * wishStatusService.getWishStatusByCustomerId(customerId); }
 * 
 * @PutMapping("/{id}") public ResponseEntity<WishStatus> updateWishStatus(
 * 
 * @PathVariable Long id, @RequestBody WishStatus wishStatus) { WishStatus
 * updatedWishStatus = wishStatusService.updateWishStatus(id, wishStatus);
 * return ResponseEntity.ok(updatedWishStatus); }
 * 
 * @DeleteMapping("/{id}") public ResponseEntity<Void>
 * deleteWishStatus(@PathVariable Long id) {
 * wishStatusService.deleteWishStatus(id); return
 * ResponseEntity.noContent().build(); }
 * 
 * @GetMapping("/count") public long countAllWishStatuses() { return
 * wishStatusService.countAllWishStatuses(); }
 * 
 * @GetMapping("/count/{status}") public long
 * countWishStatusByStatus(@PathVariable int status) { return
 * wishStatusService.countWishStatusByStatus(status); } }
 */