package hu.bme.ecommercebackend.model.enums;

public enum OrderStatus {
    CREATED("created"),
    IN_PROGRESS("in progress"),
    UNDER_DELIVERY("under delivery"),
    COMPLETED("completed");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
