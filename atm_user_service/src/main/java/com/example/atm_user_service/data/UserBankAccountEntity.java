package com.example.atm_user_service.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_bank_account")
public class UserBankAccountEntity implements Serializable {
    private static final long serialVersionUID = 5690557562929907720L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable=false)
    private double balance;

    @OneToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
