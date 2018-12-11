package tk.mybatis.springboot.model;
public enum RoleCode {

    ADMIN(1, "管理员"), 
    USER(2, "用户"), 
    GUEST(3, "客户");

    public Integer code;
    public String desc;

    RoleCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static RoleCode getTypeByCode(Integer code) {
        RoleCode defaultType = RoleCode.GUEST;
        for (RoleCode ftype : RoleCode.values()) {
            if (ftype.code == code) {
                return ftype;
            }
        }
        return defaultType;
    }

    public static String getDescByCode(Integer code) {
        return getTypeByCode(code).desc;
    }
}