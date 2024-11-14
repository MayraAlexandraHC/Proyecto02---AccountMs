package com.bank.AccountMs.model;

import java.util.Random;

public class BankAccount {
    public enum AccountType {
        AHORROS,
        CORRIENTE
    }

    private String id;
    private String accountNumber;
    private Double balance;
    private AccountType accountType;

    // Constructor
    //Constructor. Se crea la cuenta con s/.0.0.
    public BankAccount(AccountType accountType) {
        this.accountNumber = AccountGenerator(accountType);
        this.balance = 0.0;
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    //Depósito
    public boolean depositar(double amount) {
        if (amount > 0) {
            this.balance = balance + amount;
            return true;
        }
        else {
            System.out.println("El monto a depositar debe ser mayor que cero.");
            return false;
        }
    }

    //restricciones para el retiro
    public boolean retirar(double amount) {
        if (amount > 0) {
            if (accountType == AccountType.AHORROS && this.balance - amount < 0) {
                System.out.println("Lo sentimos, no se pudo realizar el retiro de su cuenta de ahorros. Saldo insuficiente.");
                return false;
            }
            else if(accountType == AccountType.CORRIENTE && this.balance - amount < -500.0) {
                System.out.println("Lo sentimos, no se pudo realizar el retiro de su cuenta corriente. Límite de saldo negativo excedido.");
                return false;
            }
            else {
                this.balance = this.balance - amount;
                return true;
            }
        }
        else {
            System.out.println("El monto de retiro debe ser mayor que 0.");
            return false;
        }
    }

    //Genera el número de cuenta bancaria
    private String AccountGenerator(AccountType accountType) {
        Random random = new Random();
        String newAccount;
        if (accountType == AccountType.CORRIENTE) newAccount = "CC";
        else newAccount = "CA";
        for (int i = 0; i < 14; i++) {
            String c = String.valueOf(random.nextInt(10));
            newAccount = newAccount.concat(c);
        }
        return newAccount;
    }
}