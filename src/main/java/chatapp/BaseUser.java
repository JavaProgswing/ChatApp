//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

class BaseUser {
    private String name;
    private StringBuffer password;

    public BaseUser(String name, StringBuffer password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public StringBuffer getPassword() {
        return this.password;
    }
}
