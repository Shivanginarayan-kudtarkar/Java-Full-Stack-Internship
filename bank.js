class BankAccount {
    #balance = 0;

    constructor(type, pin) {
        this.type = type;
        this.pin = pin;
        this.history = [];
    }

    checkPIN(input) {
        return input == this.pin;
    }

    deposit(amount) {
        this.#balance += amount;
        this.history.push("Deposited: " + amount);
    }

    withdraw(amount) {
        if (amount > this.#balance || amount <= 0) {
            return "Invalid Withdraw";
        }
        this.#balance -= amount;
        this.history.push("Withdrawn: " + amount);
    }

    getBalance() {
        return this.#balance;
    }

    getHistory() {
        return this.history.join("<br>");
    }

    calculateInterest() {
        if (this.type === "Savings") {
            return this.#balance * 0.05;
        }
        return 0;
    }
}

let account = new BankAccount("Savings", "1234");

function deposit() {
    let amt = +document.getElementById("amount").value;
    let pin = document.getElementById("pin").value;

    if (!account.checkPIN(pin)) return show("Wrong PIN");

    account.deposit(amt);
    show("Deposited Successfully");
}

function withdraw() {
    let amt = +document.getElementById("amount").value;
    let pin = document.getElementById("pin").value;

    if (!account.checkPIN(pin)) return show("Wrong PIN");

    let result = account.withdraw(amt);
    show(result || "Withdraw Success");
}

function checkBalance() {
    show("Balance: " + account.getBalance() +
         "<br>Interest: " + account.calculateInterest() +
         "<br><br>History:<br>" + account.getHistory());
}

function show(msg) {
    document.getElementById("output").innerHTML = msg;
}