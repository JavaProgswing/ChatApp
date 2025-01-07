//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

class BaseMessage {
    private String content;
    private String originalauthor;
    private long epochtime;

    public BaseMessage() {
    }

    public BaseMessage(String content, String originalauthor, long epochtime) {
        this.content = content;
        this.originalauthor = originalauthor;
        this.epochtime = epochtime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOriginalauthor() {
        return this.originalauthor;
    }

    public void setOriginalauthor(String originalauthor) {
        this.originalauthor = originalauthor;
    }

    public long getEpochtime() {
        return this.epochtime;
    }

    public void setEpochtime(long epochtime) {
        this.epochtime = epochtime;
    }

    public String toString() {
        return "{\"content\":\"" + this.content + "\", \"originalauthor\":\"" + this.originalauthor + "\", \"epochtime\":" + this.epochtime + "}";
    }
}
