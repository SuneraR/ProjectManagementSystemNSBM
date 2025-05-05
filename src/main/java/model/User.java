package model;

public class User {
    public enum Role {
        ADMIN("admin"),
        MANAGER("manager"),
        MEMBER("member");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Role fromString(String text) {
            for (Role role : Role.values()) {
                if (role.value.equalsIgnoreCase(text)) {
                    return role;
                }
            }
            return MEMBER; // default value
        }
    }

    private final String name;
    private final String email;
    private final String password;
    private final Role role;

    public User(String username, String email, String password, Role member) {
        this.name = username;
        this.email = email;
        this.password = password;
        this.role = member;
    }

   

	// Getters
    public String getUsername() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
}