package be.brahms.TFE_RentServe.services.impl;

import be.brahms.TFE_RentServe.exceptions.bill.BillNotFoundException;
import be.brahms.TFE_RentServe.exceptions.user.AccessNotAuthorizedException;
import be.brahms.TFE_RentServe.exceptions.user.UserNotFoundException;
import be.brahms.TFE_RentServe.mappers.BillMapper;
import be.brahms.TFE_RentServe.models.dtos.bill.BillDTO;
import be.brahms.TFE_RentServe.models.entities.Bill;
import be.brahms.TFE_RentServe.models.entities.Rental;
import be.brahms.TFE_RentServe.models.entities.User;
import be.brahms.TFE_RentServe.repositories.BillRepository;
import be.brahms.TFE_RentServe.repositories.UserRepository;
import be.brahms.TFE_RentServe.services.BillService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional(readOnly = true)
  public byte[] generateBillPdf(long billId) {

    Bill bill = billRepository.findById(billId).orElseThrow(BillNotFoundException::new);

    BigDecimal amount = bill.getAmount();

    BigDecimal fees = amount.multiply(new BigDecimal("0.02")).setScale(2, RoundingMode.HALF_UP);

    BigDecimal total = amount.add(fees);

    try (PDDocument document = new PDDocument();
        ByteArrayOutputStream output = new ByteArrayOutputStream()) {

      PDPage page = new PDPage();
      document.addPage(page);

      try (PDPageContentStream content = new PDPageContentStream(document, page)) {

        float y = 750;

        // Titre
        y = writeLine(content, "RENT SERVE - FACTURE", 50, y, 18);
        y -= 15;

        y = writeLine(content, "Facture n° : " + bill.getId(), 50, y, 12);

        y = writeLine(content, "Statut : " + bill.getStatus(), 50, y - 5, 12);

        y -= 25;

        y = writeLine(content, "DETAIL DES LOCATIONS", 50, y, 14);

        y -= 10;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (bill.getRentals() != null) {
          for (Rental rental : bill.getRentals()) {

            String name;

            if (rental.getUserMaterial() != null) {
              name = "Materiel : " + safeText(rental.getUserMaterial().getDescriptionMaterial());

            } else if (rental.getUserFavor() != null) {
              name = "Service : " + safeText(rental.getUserFavor().getDescriptionFavor());

            } else {
              name = "Location";
            }

            y = writeLine(content, name, 50, y - 5, 11);

            String start =
                rental.getStarDateAt() != null
                    ? rental.getStarDateAt().format(dateFormatter)
                    : "Non renseignee";

            String startTime =
                rental.getStartTime() != null ? rental.getStartTime().format(timeFormatter) : "";

            y = writeLine(content, "Debut : " + start + " " + startTime, 65, y - 3, 10);

            String end =
                rental.getEndDateAt() != null
                    ? rental.getEndDateAt().format(dateFormatter)
                    : "Non renseignee";

            String endTime =
                rental.getEndTime() != null ? rental.getEndTime().format(timeFormatter) : "";

            y = writeLine(content, "Fin : " + end + " " + endTime, 65, y - 3, 10);

            y -= 10;
          }
        }

        y -= 10;

        y =
            writeLine(
                content, "Montant des prestations : " + amount.toPlainString() + " EUR", 50, y, 12);

        y =
            writeLine(
                content,
                "Frais d'assurance et de gestion (2 %) : " + fees.toPlainString() + " EUR",
                50,
                y - 8,
                12);

        y -= 15;

        writeLine(content, "TOTAL A PAYER : " + total.toPlainString() + " EUR", 50, y - 5, 14);
      }

      document.save(output);
      return output.toByteArray();

    } catch (IOException e) {
      throw new IllegalStateException("Erreur lors de la generation du PDF", e);
    }
  }

  private float writeLine(PDPageContentStream content, String text, float x, float y, int fontSize)
      throws IOException {

    content.beginText();
    content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), fontSize);
    content.newLineAtOffset(x, y);

    // Les polices PDF standard ne prennent pas en charge
    // tous les caractères Unicode.
    content.showText(safeText(text));

    content.endText();

    return y - fontSize - 5;
  }

  private String safeText(String text) {
    if (text == null) {
      return "";
    }

    // Évite les caractères non pris en charge par Helvetica standard.
    return text.replace("€", "EUR")
        .replace("’", "'")
        .replace("–", "-")
        .replace("é", "e")
        .replace("è", "e")
        .replace("ê", "e")
        .replace("à", "a")
        .replace("ù", "u")
        .replace("ç", "c")
        .replace("É", "E");
  }
}
