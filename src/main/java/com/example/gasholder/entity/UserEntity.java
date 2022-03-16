package com.example.gasholder.entity;



public class UserEntity {

    private int id;
    private String login;
    private String password;
    private String department;
    private String role;

    public UserEntity() {
    }

    public UserEntity(int id, String login, String password, String department, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.department = department;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
