package model;

public class User {
    private int id;  // Added ID field
    private  String username;
    private  String email;
    private  String passwordHash;  // Changed to passwordHash for security
    private  Role role;
    
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
            if (text == null) return MEMBER;
            for (Role role : Role.values()) {
                if (role.value.equalsIgnoreCase(text)) {
                    return role;
                }
            }
            return MEMBER;
        }
    }

    // Constructor with all fields including ID (for existing users)
    public User(int id, String username, String email, String passwordHash, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Constructor without ID (for new users)
    public User(String username, String email, String passwordHash, Role role) {
        this(-1, username, email, passwordHash, role);
    }
    
    public User() {}

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }

    // Setters (only for fields that might need updating)
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) {
    	this.username=username;
    }
    public void setRole(String role) {
        this.role = Role.fromString(role);
    }

	public void setEmail(String email) {
		// TODO Auto-generated method stub
		this.email=email;
	}
}