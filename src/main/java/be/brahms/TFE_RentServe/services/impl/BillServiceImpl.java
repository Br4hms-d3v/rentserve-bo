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

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
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
     * @param billMapper     the bill mapper change entity to dto or form to entity
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

        Bill bill = billRepository.findById(billId)
                .orElseThrow(BillNotFoundException::new);

        BigDecimal amount = bill.getAmount() != null
                ? bill.getAmount()
                : BigDecimal.ZERO;

        // Taux de gestion : 10 %, comme sur ton modèle.
        BigDecimal feeRate = new BigDecimal("0.10");

        BigDecimal fees = amount.multiply(feeRate)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = amount.add(fees)
                .setScale(2, RoundingMode.HALF_UP);

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream content =
                         new PDPageContentStream(document, page)) {

                float pageWidth = page.getMediaBox().getWidth();
                float left = 55;
                float right = pageWidth - 55;
                float width = right - left;
                float y = 785;

                PDFont font = new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA);
                PDFont bold = new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA_BOLD);

                // 1. Logo
                float logoX = left;
                float logoY = y - 48;
                float logoWidth = 100;
                float logoHeight = 48;

                PDImageXObject logo = PDImageXObject.createFromFile(
                        "C:/Users/khadr/ProjectAngular/TFE_RentServe_Frontend/public/logo/logoSmall.jpg",
                        document
                );

                float imageWidth = logo.getWidth();
                float imageHeight = logo.getHeight();

                // Redimensionnement proportionnel pour conserver le ratio
                float scale = Math.min(
                        logoWidth / imageWidth,
                        logoHeight / imageHeight
                );

                float renderedWidth = imageWidth * scale;
                float renderedHeight = imageHeight * scale;

                // Centrage dans la zone réservée au logo
                float imageX = logoX + (logoWidth - renderedWidth) / 2;
                float imageY = logoY + (logoHeight - renderedHeight) / 2;

                content.drawImage(
                        logo,
                        imageX,
                        imageY,
                        renderedWidth,
                        renderedHeight
                );

                // 2. Nom de la plateforme
                drawText(content, "RENT SERVER", left + 5, y - 78, 13, bold);

                // 3. Numéro et statut
                drawText(content,
                        "Facture n° : " + bill.getId(),
                        left + 5, y - 118, 12, font);

                drawText(content,
                        "Statut : " + bill.getStatus(),
                        left + 5, y - 138, 11, font);

                // 4. Informations du client
                // Adapte les getters à ceux de ton entité User.
                User client = bill.getUser();

                float clientX = 365;
                float clientY = y - 85;

                if (client != null) {
                    drawText(content,
                            safeText(client.getName() + " "
                                    + client.getFirstName()),
                            clientX, clientY, 11, font);

                    drawText(content,
                            safeText(client.getStreet()),
                            clientX, clientY - 20, 10, font);

                    drawText(content,
                            safeText(client.getZipCode() + " " + client.getCity()),
                            clientX, clientY - 40, 10, font);
                }

                drawLine(content, clientX - 5, clientY - 52,
                        right, clientY - 52, 0.8f);

                LocalDate today = LocalDate.now();

                drawText(content,
                        "Fait le " + today.format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        clientX + 5, clientY - 76, 10, font);

                drawText(content, "100 Bruxelles",
                        clientX + 5, clientY - 96, 10, font);

                // 5. Titre du tableau
                float tableTop = 520;

                drawText(content, "DETAIL DES LOCATIONS",
                        left + 20, tableTop + 15, 13, bold);

                float tableX = left;
                float tableWidth = width;
                float priceColumn = tableX + tableWidth * 0.72f;

                float currentY = tableTop;

                // 6. Lignes de location et de prestation (UN SEUL cadre)
                float itemPitch = 85;      // espace vertical entre deux locations
                float blockPadding = 30;   // marge basse du cadre

                List<Rental> rentals = bill.getRentals() != null
                        ? new ArrayList<>(bill.getRentals())
                        : List.of();

                float blockHeight = rentals.isEmpty()
                        ? 60
                        : rentals.size() * itemPitch + blockPadding;
                float blockBottom = currentY - blockHeight;

                // Cadre unique + séparation de la colonne des prix,
                // dessinés une seule fois pour toutes les locations
                drawRect(content, tableX, blockBottom,
                        tableWidth, blockHeight, 2f);

                drawLine(content, priceColumn, blockBottom,
                        priceColumn, currentY, 2f);

                float itemTop = currentY;

                for (Rental rental : rentals) {

                    String name;

                    if (rental.getUserMaterial() != null) {
                        name = "Materiel : "
                                + safeText(rental.getUserMaterial()
                                .getDescriptionMaterial());

                    } else if (rental.getUserFavor() != null) {
                        name = "Service : "
                                + safeText(rental.getUserFavor()
                                .getDescriptionFavor());

                    } else {
                        name = "Location";
                    }

                    String startDate = formatDate(rental.getStarDateAt());
                    String endDate = formatDate(rental.getEndDateAt());

                    String startTime = rental.getStartTime() != null
                            ? rental.getStartTime().format(
                            DateTimeFormatter.ofPattern("HH:mm"))
                            : "";

                    String endTime = rental.getEndTime() != null
                            ? rental.getEndTime().format(
                            DateTimeFormatter.ofPattern("HH:mm"))
                            : "";

                    BigDecimal rentalAmount =
                            rental.getAmount() != null
                                    ? rental.getAmount()
                                    : BigDecimal.ZERO;

                    drawText(content, "- " + name,
                            tableX + 25, itemTop - 25, 11, font);

                    drawText(content, "Debut : " + startDate
                                    + " " + startTime,
                            tableX + 45, itemTop - 48, 10, font);

                    drawText(content, "Fin : " + endDate
                                    + " " + endTime,
                            tableX + 45, itemTop - 68, 10, font);

                    drawText(content,
                            formatEuro(rentalAmount),
                            priceColumn + 8, itemTop - 30, 11, font);

                    // Pas de rectangle ni de trait entre deux locations
                    itemTop -= itemPitch;
                }

                currentY = blockBottom;

                // 7. Frais d'assurance et de gestion
                BigDecimal totalHT = amount.setScale(2, RoundingMode.HALF_UP);
                float feeHeight = 60;
                float feeBottom = currentY - feeHeight;

                drawRect(content, tableX, feeBottom,
                        tableWidth, feeHeight, 2f);

                drawLine(content, priceColumn, feeBottom,
                        priceColumn, currentY, 2f);

// Ligne 1 : total hors taxe
                drawText(content,
                        "Prix total H.T (hors taxe)",
                        tableX + 18, currentY - 23, 10, font);

                drawText(content, formatEuro(totalHT),
                        priceColumn + 8, currentY - 23, 10, font);

// Ligne 2 : frais (plus bas)
                drawText(content,
                        "Frais d'assurance et de gestion (10 %)",
                        tableX + 18, currentY - 45, 10, font);

                drawText(content, formatEuro(fees),
                        priceColumn + 8, currentY - 45, 10, font);

                // 8. Total à payer
                currentY = feeBottom;
                float totalHeight = 32;
                float totalBottom = currentY - totalHeight;

                drawRect(content, tableX, totalBottom,
                        tableWidth, totalHeight, 2.5f);

                drawLine(content, priceColumn, totalBottom,
                        priceColumn, currentY, 2.5f);

                drawText(content, "TOTAL A PAYER",
                        tableX + 18, currentY - 21, 11, bold);

                drawText(content, formatEuro(total),
                        priceColumn + 8, currentY - 21, 11, bold);
            }

            document.save(output);
            return output.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Erreur lors de la generation du PDF", e);
        }
    }

    private void drawText(
            PDPageContentStream content,
            String text,
            float x,
            float y,
            int fontSize,
            PDFont font) throws IOException {

        content.beginText();
        content.setFont(font, fontSize);
        content.newLineAtOffset(x, y);
        content.showText(safeText(text));
        content.endText();
    }

    private void drawRect(
            PDPageContentStream content,
            float x,
            float y,
            float width,
            float height,
            float lineWidth) throws IOException {

        content.setLineWidth(lineWidth);
        content.addRect(x, y, width, height);
        content.stroke();
    }

    private void drawLine(
            PDPageContentStream content,
            float x1,
            float y1,
            float x2,
            float y2,
            float lineWidth) throws IOException {

        content.setLineWidth(lineWidth);
        content.moveTo(x1, y1);
        content.lineTo(x2, y2);
        content.stroke();
    }

    private String formatDate(LocalDate date) {
        return date != null
                ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "Non renseignee";
    }

    private String formatEuro(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP)
                .toPlainString().replace('.', ',') + " EUR";
    }

    private String safeText(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("€", "EUR")
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