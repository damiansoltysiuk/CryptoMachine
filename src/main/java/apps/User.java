package apps;

public class User {
    private String login;
    private String loginPass;
    private String email;
    private String emailPass;
    private String cipherMethod;

    public User() {
    }

    public User(String login, String loginPass, String email, String emailPass, String cipherMethod) {
        this.login = login;
        this.loginPass = loginPass;
        this.email = email;
        this.emailPass = emailPass;
        this.cipherMethod = cipherMethod;
    }

    public static class Builder {
        private String login;
        private String userPass;
        private String email;
        private String emailPass;
        private String cipherMethod;

        public Builder() {
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setUserPass(String userPass) {
            this.userPass = userPass;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setEmailPass(String emailPass) {
            this.emailPass = emailPass;
            return this;
        }

        public Builder setCipherMethod(String cipherMethod) {
            this.cipherMethod = cipherMethod;
            return this;
        }

        public User build() {
            return new User(login, userPass, email, emailPass, cipherMethod);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPass() {
        return emailPass;
    }

    public void setEmailPass(String emailPass) {
        this.emailPass = emailPass;
    }

    public String getCipherMethod() {
        return cipherMethod;
    }

    public void setCipherMethod(String cipherMethod) {
        this.cipherMethod = cipherMethod;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("login='").append(login).append('\'');
        sb.append(", loginPass='").append(loginPass).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", emailPass='").append(emailPass).append('\'');
        sb.append(", cipherMethod='").append(cipherMethod).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
