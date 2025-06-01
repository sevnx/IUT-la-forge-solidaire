package desforge.dev.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;

    @Column(unique = true)
    private String username;

    private String password;

    private String address;

    @OneToMany(mappedBy = "idTool")
    private List<Tool> ownedTools;

    @OneToMany(mappedBy = "idRequest")
    private List<BorrowRequest> borrowRequests;

    @OneToMany(mappedBy = "idBorrow")
    private List<Borrow> borrows;

    public User() {
        ownedTools = new ArrayList<>();
        borrowRequests = new ArrayList<>();
        borrows = new ArrayList<>();
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Tool> getOwnedTools() {
        return ownedTools;
    }

    public void setOwnedTools(List<Tool> ownedTools) {
        this.ownedTools = ownedTools;
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
