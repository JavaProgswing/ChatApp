//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

class Authorization {
    private String access_token;
    private long expires_in;
    private String expiration_unit;

    public Authorization() {
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getExpiration_unit() {
        return this.expiration_unit;
    }

    public void setExpiration_unit(String expiration_unit) {
        this.expiration_unit = expiration_unit;
    }
}
