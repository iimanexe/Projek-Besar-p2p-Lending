package com.p2p.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;

public class LoanServiceTest {
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {

        // =====================================================
        // SCENARIO:
        // Borrower tidak terverifikasi (KYC = false)
        // Ketika borrower mengajukan pinjaman
        // Maka sistem harus menolak dengan melempar exception
        // =====================================================

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower belum lolos proses KYC
        Borrower borrower = new Borrower(false, 700);

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(1000);

        // Kita berharap (Assert) akan ada Exception yang dilempar
        assertThrows(IllegalArgumentException.class, () -> {
            // Di dalam sini adalah tindakan (Act) yang memicu error
            loanService.createLoan(borrower, amount);
        });
    }

    @Test 
    void shouldRejectLoanWhenAmountIsZeroOrNegative() { // test case 2
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi (KYC = true)
        // Ketika borrower mengajukan pinjaman <= 0
        // Maka sistem harus menolak dengan melempar exception
        // =====================================================

        // membuat objek peminjam di test case 2 dengan tingkat kepercayaan 700
        Borrower borrower2 = new Borrower(true, 500);

        // membuat objek pemberi pinjaman
        LoanService service2 = new LoanService();

        // jumlah pinjaman 
        BigDecimal amount2 = BigDecimal.ZERO;

        assertThrows(IllegalArgumentException.class, () -> {
            service2.createLoan(borrower2, amount2);
        });
    }

    @Test
    void shouldApproveLoanWhenCreditScoreHigh(){ // test case 03
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi (KYC = true)
        // Ketika cerdit score Borrower tinggi
        // Maka sistem harus memberikan approve pengajuan pinjaman tersebut
        // =====================================================

        // membuat objek peminjam dengan tingkat kepercayaan >= 600
        Borrower borrower3 = new Borrower(true, 800);

        // membuat objek pemberi pinjaman
        LoanService service3 = new LoanService();

        // jumlah pinjaman yang diajukan
        BigDecimal amount3 = BigDecimal.valueOf(400);

        //memanggil methode dari objek pemberi pinjaman
        Loan HasilLoan = service3.createLoan(borrower3, amount3);

        // memeriksa apakah status hasil loan di approve atau tidak
        assertEquals(HasilLoan.getStatus(), Loan.Status.APPROVED);
    }

    @Test
    void shouldRejectLoanWhenCreditScoreLow(){ // test case 04
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi (KYC = true)
        // Ketika cerdit score Borrower rendah
        // Maka sistem harus menolak pengajuan pinjaman tersebut
        // ===================================================== 

        // membuat objek peminjam dengan tingkat kepercayaan <= 600
        Borrower borrower4 = new Borrower(true, 200);

        // membuat objek pemberi pinjaman 
        LoanService service4 = new LoanService();

        // jumlah pinjaman
        BigDecimal amount4 = BigDecimal.valueOf(1000);

        // memanggil methode dari objem pemberi pinjaman
        Loan Hasil = service4.createLoan(borrower4, amount4);

        // memeriksa status 
        assertEquals(Loan.Status.REJECTED, Hasil.getStatus());
    }
}
