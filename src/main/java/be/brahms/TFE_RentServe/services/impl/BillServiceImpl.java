package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.bill.BillNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.AccessNotAuthorizedException;
import be.brahms.TFE_RentServe.exceptions.user.UserNotFoundException;
import be.brahms.TFE_RentServe.mappers.BillMapper;
import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import be.brahms.TFE_RentServe.models.entities.Bill;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.repositories.BillRepository;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.BillService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing Bill. Uses BillRepository to perform database operations uses
 * BillMapper to map between form to entity or dto to entity
 *
 * <ul>
 *   <li>Get detail about the bill
 * </ul>
 *
 * @author Brahim K
 */
@Service
public class BillServiceImpl implements BillService {

  private final BillRepository billRepository;
  private final BillMapper billMapper;
  private final UserRepository userRepository;

  /**
   * Constructor with params
   *
   * @param billRepository the billRepo to access bill data
   * @param billMapper the bill mapper change entity to dto or form to entity
   * @param userRepository the userRepo to access user data
   */
  public BillServiceImpl(
      BillRepository billRepository, BillMapper billMapper, UserRepository userRepository) {
    this.billRepository = billRepository;
    this.billMapper = billMapper;
    this.userRepository = userRepository;
  }

  /**
   * Get a detail about the bill
   *
   * @param id the identifier of user favor
   * @return a detail about the bill
   */
  @Override
  public BillDTO findById(long id) {
    Bill bill = billRepository.findById(id).orElseThrow(BillNotFoundException::new);
    String billOwner = bill.getUser().getPseudo();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!billOwner.equals(authentication.getName())) {
      throw new AccessNotAuthorizedException();
    }

    return billMapper.toBillDetailDTO(bill);
  }

  /**
   * Get a list of bills from user
   *
   * @param userId the identifier
   * @return a list of all bills from user
   */
  @Override
  public List<BillDTO> findAllByUserId(long userId) {
    List<Bill> listBills = billRepository.findBillsByUserId(userId);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    if (!user.getPseudo().equals(authentication.getName())) {
      throw new AccessNotAuthorizedException();
    }
    return listBills.stream().map(billMapper::toBillDetailDTO).toList();
  }

  /**
   * Get a list of bills from current month
   *
   * @param userId the user identifier
   * @return a list of bill on current month
   */
  @Override
  public List<BillDTO> findAllBillsCurrentMonth(long userId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    if (!user.getPseudo().equals(authentication.getName())) {
      throw new AccessNotAuthorizedException();
    }

    LocalDate startDateMonth = LocalDate.now().withDayOfMonth(1);
    LocalDate endDateMonth = startDateMonth.plusMonths(1);

    List<Bill> listBillsCurrentMonth =
        billRepository.findBillsCurrentMonthByUserId(userId, startDateMonth, endDateMonth);

    return listBillsCurrentMonth.stream().map(billMapper::toBillDetailDTO).toList();
  }
}
