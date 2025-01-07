//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

class Messages {
    private String name;
    private String description;
    private String icon;
    private String[] messages;

    public Messages() {
    }

    public Messages(String name, String description, String icon, String[] messages) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.messages = messages;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String[] getMessages() {
        return this.messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }
}
