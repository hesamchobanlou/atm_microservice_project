package com.example.atm_service.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 2672002501364666498L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable=false)
    private String firstName;

    @Column(nullable=false)
    private String lastName;

    @Column(nullable=false, unique=true)
    private String userId;

    @Column(nullable=false)
    private String encryptedPassword;

    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="user")
    private UserBankAccountEntity userBankAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public UserBankAccountEntity getUserBankAccount() {
        return userBankAccount;
    }

    public void setUserBankAccount(UserBankAccountEntity userBankAccount) {
        this.userBankAccount = userBankAccount;
    }
}
