package com.bank.AccountMs.service;

import com.bank.AccountMs.model.BankAccount;
import com.bank.AccountMs.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository repository;

    public BankAccount createAccount(BankAccount account) {
        return repository.save(account);
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public Optional<BankAccount> getAccountById(Long id) {
        return repository.findById(id);
    }

    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }

    public BankAccount deposit(Long accountId, double amount) {
        Optional<BankAccount> optionalAccount = repository.findById(accountId);
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            if (amount > 0) {
                account.setBalance(account.getBalance() + amount);
                return repository.save(account);
            } else {
                throw new IllegalArgumentException("El monto a depositar debe ser mayor que cero.");
            }
        } else {
            throw new IllegalArgumentException("Cuenta no encontrada.");
        }
    }

    public BankAccount withdraw(Long accountId, double amount) {
        Optional<BankAccount> optionalAccount = repository.findById(accountId);
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            if (amount > 0) {
                if (account.getAccountType() == BankAccount.AccountType.AHORROS && account.getBalance() - amount < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente en la cuenta de ahorros.");
                } else if (account.getAccountType() == BankAccount.AccountType.CORRIENTE && account.getBalance() - amount < -500.0) {
                    throw new IllegalArgumentException("Límite de saldo negativo excedido en la cuenta corriente.");
                } else {
                    account.setBalance(account.getBalance() - amount);
                    return repository.save(account);
                }
            } else {
                throw new IllegalArgumentException("El monto de retiro debe ser mayor que cero.");
            }
        } else {
            throw new IllegalArgumentException("Cuenta no encontrada.");
        }
    }
}
