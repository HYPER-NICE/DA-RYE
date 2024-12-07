package hyper.darye.dto;

public class InsertMemberDto {
    private String email;
    private String name;
    private String password;
    private String mobile;
    private String address;

    public InsertMemberDto(String email, String name, String password, String mobile, String address) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
