package desforge.dev.models.borrow_request;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class CreateBorrowRequest {
    @NotBlank
    private Date ReturnDate;

    public CreateBorrowRequest() {
    }

    public Date getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(Date returnDate) {
        ReturnDate = returnDate;
    }
}
