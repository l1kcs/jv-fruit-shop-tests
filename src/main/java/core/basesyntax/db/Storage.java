package core.basesyntax.db;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private static final List<FruitTransaction> transactions = new ArrayList<>();
    private static final Map<String, Integer> fruits = new HashMap<>();

    public static void addRecord(FruitTransaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("input transaction cannot be null");
        }
        transactions.add(transaction);
    }

    public static List<FruitTransaction> getRecords() {
        return new ArrayList<>(transactions);
    }

    public static Map<String, Integer> getFruits() {
        return new HashMap<>(fruits);
    }

    public static void updateFruit(String fruit, int quantity) {
        fruits.put(fruit, fruits.getOrDefault(fruit, 0) + quantity);
    }

    public static void setFruit(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("balance cannot be negative");
        }
        fruits.put(fruit, quantity);
    }

    public static void clearRecords() {
        transactions.clear();
    }

    public static void clearFruits() {
        fruits.clear();
    }
}
