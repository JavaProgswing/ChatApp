//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

class Guild {
    private String name;
    private String description;
    private String icon;
    private String[] participants;
    private String[] admins;

    public Guild() {
    }

    public Guild(String name, String description, String icon, String[] participants, String[] admins) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.participants = participants;
        this.admins = admins;
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

    public String[] getAdmins() {
        return this.admins;
    }

    public void setAdmins(String[] admins) {
        this.admins = admins;
    }

    public String[] getParticipants() {
        return this.participants;
    }

    public void setParticipants(String[] participants) {
        this.participants = participants;
    }
}
