package ru.job4j.generic;

public class Role extends Base {
    private final User user;
    private String roleName;

    public Role(User user, String roleName) {
        super(user.getId());
        this.user = user;
        this.roleName = roleName;
    }
}
