package desforge.dev.entities;

import desforge.dev.enumerations.BorrowRequestState;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="borrow_request")
public class BorrowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRequest;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_BORROW_REQUEST"))
    private User userBorrowRequest;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TOOL_BORROW_REQUEST"))
    private Tool toolBorrowRequest;

    private Date returnDate;

    private Date requestDate;

    private BorrowRequestState state;

    public BorrowRequest() {
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public User getUserBorrowRequest() {
        return userBorrowRequest;
    }

    public void setUserBorrowRequest(User userBorrowRequest) {
        this.userBorrowRequest = userBorrowRequest;
    }

    public Tool getToolBorrowRequest() {
        return toolBorrowRequest;
    }

    public void setToolBorrowRequest(Tool toolBorrowRequest) {
        this.toolBorrowRequest = toolBorrowRequest;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public BorrowRequestState getState() {
        return state;
    }

    public void setState(BorrowRequestState state) {
        this.state = state;
    }
}
