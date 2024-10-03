package vn.hoangkhang.laptopshop.util;

public enum RoleUtil {
    USER("role-id-1", "User description"),
    ADMIN("role-id-2", "Admin description");

    private String id;
    private String description;

    RoleUtil(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
