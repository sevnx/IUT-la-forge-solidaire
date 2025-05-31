
package desforge.dev.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTool;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String photo;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TOOL_OWNER"))
    private User owner;

    @OneToMany(mappedBy = "idRequest" )
    @Column(nullable = false)
    private List<BorrowRequest> borrowRequests;

    @OneToMany(mappedBy = "idBorrow")
    @Column(nullable = false)
    private List<Borrow> borrows;

    public Tool() {
    }

    public Tool(String name, String description, String photo, User owner) {
         this.name = name;
        this.description = description;
        this.photo = photo;
        this.owner = owner;
        this.borrowRequests = new ArrayList<>();
        this.borrows = new ArrayList<>();
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

    public void setPhoto(String photo) throws IllegalArgumentException {
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
