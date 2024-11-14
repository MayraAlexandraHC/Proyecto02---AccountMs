package com.bank.AccountMs.controller;

import com.bank.AccountMs.model.BankAccount;
import com.bank.AccountMs.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class BankAccountController {

    @Autowired
    private BankAccountService service;

    @PostMapping
    public ResponseEntity<BankAccount> createAccount(@RequestBody BankAccount account) {
        BankAccount createdAccount = service.createAccount(account);
        return ResponseEntity.status(201).body(createdAccount);
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> accounts = service.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable Long id) {
        return service.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{accountId}/depositar")
    public ResponseEntity<BankAccount> deposit(@PathVariable Long accountId, @RequestBody double amount) {
        BankAccount updatedAccount = service.deposit(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    @PutMapping("/{accountId}/retirar")
    public ResponseEntity<BankAccount> withdraw(@PathVariable Long accountId, @RequestBody double amount) {
        BankAccount updatedAccount = service.withdraw(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
