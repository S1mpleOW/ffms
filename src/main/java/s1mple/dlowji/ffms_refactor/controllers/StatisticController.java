package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;
import s1mple.dlowji.ffms_refactor.services.IImportReceiptService;
import s1mple.dlowji.ffms_refactor.services.ItemService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatisticController {

    @Autowired
    private IImportReceiptService importReceiptService;

    @Autowired
    private IBookedTicketService bookedTicketService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/import-price/{year}/{month}")
    public ResponseEntity<?> getImportPriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        int totalPrice = importReceiptService.getImportReceiptsByMonth(month, year);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("total_import_price", totalPrice);
        response.put("year", year);
        response.put("month", month);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/purchase-price/{year}/{month}")
    public ResponseEntity<?> getPurchasePriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        int totalPrice = itemService.getPurchasePriceByMonth(month, year);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("total_purchase_price", totalPrice);
        response.put("year", year);
        response.put("month", month);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/booked-monthly-price/{year}/{month}")
    public ResponseEntity<?> getBookedPriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        int totalPrice = bookedTicketService.getBookedPriceByMonth(month, year);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("total_booked_price", totalPrice);
        response.put("year", year);
        response.put("month", month);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-price/{year}/{month}")
    public ResponseEntity<?> getTotalPriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        int totalPrice = bookedTicketService.getBookedPriceByMonth(month, year) + itemService.getPurchasePriceByMonth(month, year) + importReceiptService.getImportReceiptsByMonth(month, year);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("total_price", totalPrice);
        response.put("year", year);
        response.put("month", month);

        return ResponseEntity.ok(response);
    }
}
