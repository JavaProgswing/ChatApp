//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

class Admin {
    private String guildname;
    private String username;
    private boolean admin;

    public Admin() {
    }

    public Admin(String guildname, String username) {
        this.guildname = guildname;
        this.username = username;
    }

    public String getGuildname() {
        return this.guildname;
    }

    public void setGuildname(String guildname) {
        this.guildname = guildname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String toString() {
        return "Admin{guildname='" + this.guildname + "', username='" + this.username + "', admin=" + this.admin + "}";
    }
}
