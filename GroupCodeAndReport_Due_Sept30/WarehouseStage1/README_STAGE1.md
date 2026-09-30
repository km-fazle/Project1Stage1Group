# CSCI 430 Project 1 — Stage 1

Group 7 warehouse Stage 1 implementation. This is a small plain-Java program. The UI talks to `Warehouse`, which uses `ClientList` and `ProductList`. Each `Client` owns its own wishlist of `WishlistItem` objects.

## Build

```bash
javac *.java
```

## Run

```bash
java UserInterface
```

## Regression test

```bash
java Stage1RegressionTest
```

## Stage 1 features

- Add client
- Add products
- Add products to client wishlist
- Display all clients
- Display all products
- Display one client's wishlist

New client IDs: C1, C2, ...
New product IDs: P1, P2, ...

A new client's account balance starts at 0.00. If a product is already on a client's wishlist, the old quantity is replaced; the quantities are not added together.
