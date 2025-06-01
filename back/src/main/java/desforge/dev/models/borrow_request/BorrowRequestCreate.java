package desforge.dev.models.borrow_request;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class BorrowRequestCreate {
    @NotBlank
    private Date ReturnDate;

    public BorrowRequestCreate() {
    }

    public Date getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(Date returnDate) {
        ReturnDate = returnDate;
    }
}
