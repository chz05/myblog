package com.javatiaocao.myblog.constant;

/**
 * @ClassName RoleType.java
 * @Description TODO
 * @createTime 2023/08/02
 */
public enum RoleType {

    ROLE_USER(1, "ROLE_USER"),
    ROLE_ADMIN(2, "ROLE_ADMIN"),
    ROLE_SUPERADMIN(3, "ROLE_SUPERADMIN"),
    ;

    private int RoleID;

    private String RoleName;

    RoleType(int RoleID, String RoleName) {
        this.RoleID = RoleID;
        this.RoleName = RoleName;
    }

    public int getRoleID() {
        return RoleID;
    }

    public String getRoleName() {
        return RoleName;
    }
}
