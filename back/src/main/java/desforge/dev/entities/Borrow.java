package desforge.dev.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="borrow")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBorrow;

    private Date dateReturn;

    private Date dateBorrow;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_BORROW"))
    private User userBorrow;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TOOL_BORROW"))
    private Tool toolBorrow;

    public Borrow() {
    }

    public int getIdBorrow() {
        return idBorrow;
    }

    public void setIdBorrow(int idBorrow) {
        this.idBorrow = idBorrow;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public Date getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(Date dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public User getUserBorrow() {
        return userBorrow;
    }

    public void setUserBorrow(User userBorrow) {
        this.userBorrow = userBorrow;
    }

    public Tool getToolBorrow() {
        return toolBorrow;
    }

    public void setToolBorrow(Tool toolBorrow) {
        this.toolBorrow = toolBorrow;
    }
}
