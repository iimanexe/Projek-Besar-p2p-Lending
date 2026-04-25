package com.p2p.service;

import com.p2p.domain.*;
import java.math.BigDecimal;

public class LoanService {
    public Loan createLoan(Borrower borrower, BigDecimal amount) {

        validateBorrower(borrower);

        Loan loan = new Loan();
        if (borrower.getCreditScore() >= 600) {
            loan.approve();
        } else {
            loan.reject();
        }

        return loan;
    }

    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            throw new IllegalArgumentException("Borrower not verified");
        }
    }

}
