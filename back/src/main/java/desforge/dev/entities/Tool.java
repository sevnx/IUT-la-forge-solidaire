
package desforge.dev.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTool;

    private String name;

    private String description;

    private String photo;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TOOL_OWNER"))
    private User owner;

    @OneToMany(mappedBy = "idRequest" )
    private List<BorrowRequest> borrowRequests;

    @OneToMany(mappedBy = "idBorrow")
    private List<Borrow> borrows;

    public Tool() {
    }

    public int getIdTool() {
        return idTool;
    }

    public void setIdTool(int idTool) {
        this.idTool = idTool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<BorrowRequest> getBorrowRequests() {
        return borrowRequests;
    }

    public void setBorrowRequests(List<BorrowRequest> borrowRequests) {
        this.borrowRequests = borrowRequests;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
