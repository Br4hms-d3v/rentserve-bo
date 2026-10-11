package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.bill.BillNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.AccessNotAuthorizedException;
import be.brahms.TFE_RentServe.mappers.BillMapper;
import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import be.brahms.TFE_RentServe.models.entities.Bill;
import be.brahms.TFE_RentServe.repositories.BillRepository;
import be.brahms.TFE_RentServe.services.BillService;
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

  /**
   * Constructor with params
   *
   * @param billRepository the billRepo to access bill data
   * @param billMapper the bill mapper change entity to dto or form to entity
   */
  public BillServiceImpl(BillRepository billRepository, BillMapper billMapper) {
    this.billRepository = billRepository;
    this.billMapper = billMapper;
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
}
