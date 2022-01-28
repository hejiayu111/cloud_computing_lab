package Model;

public class Customer {
    public int id;
    public String firstName;
    public String lastName;
    public String associatedAccountNumber;

    public Customer (int id, String firstName, String lastName, String associatedAccountNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.associatedAccountNumber = associatedAccountNumber;
    }
}
