package uz.qarzon.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.qarzon.entity.Customer;
import uz.qarzon.repository.CustomerRepo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final CustomerRepo customerRepo;

    public byte[] exportDebtCustomersByStoreId(Integer storeId) {

        List<Customer> debtors = customerRepo.findByStoreIdAndCurrentBalanceGreaterThan(storeId, BigDecimal.ZERO);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Qarzdor Mijozlar");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Current Balance");
            headerRow.createCell(3).setCellValue("Phone Number");
            headerRow.createCell(4).setCellValue("Address");

            int rowIdx = 1;
            for (Customer customer : debtors) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(customer.getId());
                row.createCell(1).setCellValue(customer.getName());
                row.createCell(2).setCellValue(customer.getCurrentBalance().toString());
                row.createCell(3).setCellValue(customer.getPhoneNumber());
                row.createCell(4).setCellValue(customer.getAddress() != null ? customer.getAddress() : "");
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to export debt customers by storeId " + storeId, e);
        }
    }

}
