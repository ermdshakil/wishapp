/*
 * package com.wish.controller;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * import com.wish.model.WishStatus;
 * 
 * import java.time.LocalDateTime; import java.util.List; import
 * java.util.Optional;
 * 
 * @Service public class WishStatusService {
 * 
 * private final WishStatusRepository wishStatusRepository;
 * 
 * @Autowired public WishStatusService(WishStatusRepository
 * wishStatusRepository) { this.wishStatusRepository = wishStatusRepository; }
 * 
 * // Create a new WishStatus public WishStatus createWishStatus(WishStatus
 * wishStatus) { wishStatus.setCreatedAt(LocalDateTime.now()); return
 * wishStatusRepository.save(wishStatus); }
 * 
 * // Get all WishStatus records public List<WishStatus> getAllWishStatuses() {
 * return wishStatusRepository.findAll(); }
 * 
 * // Get WishStatus by ID public Optional<WishStatus> getWishStatusById(Long
 * id) { return wishStatusRepository.findById(id); }
 * 
 * // Get WishStatus by customer ID public Optional<WishStatus>
 * getWishStatusByCustomerId(Long customerId) { return
 * wishStatusRepository.findByCustomerId(customerId); }
 * 
 * // Update WishStatus public WishStatus updateWishStatus(Long id, WishStatus
 * updatedWishStatus) { return wishStatusRepository.findById(id) .map(wishStatus
 * -> { wishStatus.setCustomerId(updatedWishStatus.getCustomerId());
 * wishStatus.setEmail(updatedWishStatus.getEmail());
 * wishStatus.setPhone(updatedWishStatus.getPhone());
 * wishStatus.setOccasion(updatedWishStatus.getOccasion());
 * wishStatus.setMessage(updatedWishStatus.getMessage());
 * wishStatus.setStatus(updatedWishStatus.getStatus()); return
 * wishStatusRepository.save(wishStatus); }) .orElseGet(() -> {
 * updatedWishStatus.setId(id); return
 * wishStatusRepository.save(updatedWishStatus); }); }
 * 
 * // Delete WishStatus public void deleteWishStatus(Long id) {
 * wishStatusRepository.deleteById(id); }
 * 
 * // Count WishStatus records by status public long countWishStatusByStatus(int
 * status) { return wishStatusRepository.countByStatus(status); }
 * 
 * // Count all WishStatus records public long countAllWishStatuses() { return
 * wishStatusRepository.count(); } }
 */