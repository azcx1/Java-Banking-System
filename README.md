# 🏦 Bank System — Console Banking Application

A Java-based console banking application simulating core banking operations, built with clean architecture principles, domain-driven design, and strong object-oriented fundamentals.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Domain Model](#domain-model)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Design Decisions](#design-decisions)

---

## Overview

Bank System is a console application that simulates real-world banking operations. It supports multiple client types, multiple account types, and full transaction history — all designed around a layered architecture separating concerns between UI, business logic, and data access.

---

## Features

### 👤 Client Management
- **Individual Clients** — registered with PESEL number, full name, and contact details
- **Corporate Customers** — registered with NIP, REGON, company name, and contact details
- Full contact details: address (with optional flat number), email, phone number

### 🏧 Bank Accounts
- **Standard Account** — basic deposit, withdrawal, and transfer operations
- **Savings Account** — minimum deposit of 5.00, withdrawal fee of 5.00, interest accrual support
- Multiple accounts per client
- Co-owner support (add/remove)

### 💸 Transactions
- Deposit funds
- Withdraw funds
- Transfer between accounts (by account number)
- Full transaction history per account

### 🔢 Validation
- PESEL — format + checksum validation + birth date extraction + gender detection
- NIP — format + checksum validation
- REGON — 9-digit and 14-digit format + checksum validation
- Email — regex validation + normalization
- Phone number — Polish and international format support
- Postal code — Polish `XX-XXX` format
- Account number — 26-digit NRB format with formatted display

---

## Project Structure

```
src/
├── com.github.azcx1/
│   ├── BankConsoleApp.java              # Entry point & UI layer
│   └── AppUtils/
│       ├── AccountSummaryDTO.java       # DTO for account display
│       ├── BankAccountServices.java     # Currency selection helper
│       └── ClientService.java          # Client creation via console
│
└── banksystem/
    ├── BankController.java              # Mediator between UI and services
    ├── BankService.java                 # Core business logic
    │
    ├── account/
    │   ├── BankAccount.java             # Abstract base account
    │   ├── StandardAccount.java         # Standard account implementation
    │   ├── SavingsAccount.java          # Savings account with interest
    │   └── SingleTransaction.java       # Transaction record
    │
    ├── client/
    │   ├── Client.java                  # Abstract base client
    │   ├── IndividualClient.java        # Personal client
    │   ├── CorporateCustomer.java       # Business client
    │   └── ContactDetails.java         # Contact info aggregate
    │
    ├── common/model/
    │   ├── account/
    │   │   └── AccountNumber.java       # Value object — NRB account number
    │   └── client/
    │       ├── Address.java             # Value object — postal address
    │       ├── EmailAddress.java        # Value object — email
    │       ├── PhoneNumber.java         # Value object — phone
    │       ├── Pesel.java               # Value object — PESEL
    │       ├── Nip.java                 # Value object — NIP
    │       └── Regon.java              # Value object — REGON
    │
    └── utils/
        ├── BankAccountRepository.java   # Repository interface
        ├── AccountRepository.java       # In-memory repository impl
        └── AccountNumberGenerator.java  # Thread-safe account number generator
```

---

## Domain Model

```
Client (abstract)
├── IndividualClient      → PESEL, firstName, surname
└── CorporateCustomer     → NIP, REGON, companyName

BankAccount (abstract)
├── StandardAccount       → basic operations
└── SavingsAccount        → min deposit, withdrawal fee, interest

ContactDetails
└── Address, EmailAddress, PhoneNumber

SingleTransaction         → record: to, amount, dateTime
AccountNumber             → 26-digit NRB, formatted display
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle (depending on your build setup)

### Running the Application

```bash
# Clone the repository
git clone https://github.com/azcx1/bank-system.git
cd bank-system

# Compile and run
javac -d out src/**/*.java
java -cp out com.github.azcx1.BankConsoleApp
```

---

## Usage

On startup, you'll be greeted with the main menu:

```
=-=-= Main Menu -0-0-0

1. Select client
2. Add client
3. Exit
```

**Typical flow:**
1. Add an Individual or Corporate client
2. Select the client
3. Create a bank account (Standard or Savings)
4. Select the account
5. Perform deposits, withdrawals, or transfers
6. View transaction history

---

## Design Decisions

### Value Objects over Primitives
Domain identifiers like `Pesel`, `Nip`, `Regon`, `AccountNumber`, `EmailAddress`, and `PhoneNumber` are modeled as dedicated classes/records rather than raw `String` values. Each one encapsulates its own validation logic, preventing invalid state from ever entering the system.

### Layered Architecture
```
UI (BankConsoleApp)
    ↓
Controller (BankController)
    ↓
Service (BankService)
    ↓
Repository (AccountRepository)
```
Each layer has a single responsibility. The controller mediates between the console UI and the service layer without containing business logic.

### Repository Pattern
`BankAccountRepository` is defined as an interface, with `AccountRepository` providing an in-memory `HashMap`-based implementation. This makes the persistence layer easily swappable (e.g., replacing with a database implementation in the future).

### BigDecimal for Money
All monetary values use `BigDecimal` instead of `double` or `float`, avoiding floating-point precision errors — a critical requirement for financial applications.

### Immutability
Account numbers, client IDs (UUID), and transaction records are immutable. Getters returning collections use `Collections.unmodifiableList/Map` to prevent external mutation.

### Thread-Safe Account Number Generation
`AccountNumberGenerator` uses `AtomicLong` to ensure unique, sequentially generated account numbers even under concurrent access.

---

## Supported Currencies

| Code | Currency       |
|------|----------------|
| PLN  | Polish Złoty   |
| USD  | US Dollar      |
| EUR  | Euro           |

---

## Author

**azcx1** — [github.com/azcx1](https://github.com/azcx1)
