package uz.qarzon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qarzon.dto.request.LoanRequest;
import uz.qarzon.dto.response.LoanResponse;
import uz.qarzon.service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Integer loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponse>> getLoansByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(loanService.getLoansByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@RequestBody LoanRequest loanRequest) {
        return new ResponseEntity<>(loanService.createLoan(loanRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<LoanResponse> updateLoan(@PathVariable Integer loanId, @RequestBody LoanRequest loanRequest) {
        return new ResponseEntity<>(loanService.updateLoan(loanId, loanRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<LoanResponse> deleteLoan(@PathVariable Integer loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }

}
