package br.com.metaway.petshop;

public enum UserRoles {
    ADMIN("admin"),
    CLIENT("client"),
    GUEST("Guest");

    private String role;

    UserRoles(String role){
        this.role = role;
    }

    public String UserRoles(){
        return role;
    }
}
