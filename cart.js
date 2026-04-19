class Product {
    constructor(name, price, qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    getTotal() {
        return this.price * this.qty;
    }
}

class Cart {
    constructor() {
        this.items = [];
        this.discount = 0;
    }

    add(product) {
        this.items.push(product);
    }

    remove(name) {
        this.items = this.items.filter(p => p.name !== name);
    }

    getTotal() {
        let total = this.items.reduce((sum, p) => sum + p.getTotal(), 0);
        return total - this.discount;
    }

    applyCoupon() {
        this.discount = 50;
    }

    display() {
        let result = "";
        this.items.forEach(p => {
            result += `${p.name} x${p.qty} = ₹${p.getTotal()}<br>`;
        });
        result += "<br>Total: ₹" + this.getTotal();
        return result;
    }
}

let cart = new Cart();

function addItem() {
    let name = document.getElementById("name").value;
    let price = +document.getElementById("price").value;
    let qty = +document.getElementById("qty").value;

    cart.add(new Product(name, price, qty));
    show(cart.display());
}

function applyCoupon() {
    cart.applyCoupon();
    show(cart.display());
}

function show(msg) {
    document.getElementById("output").innerHTML = msg;
}