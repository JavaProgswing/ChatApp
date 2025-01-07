//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

class ResponseStatusCode {
    private int status_code;
    private String reason;

    public ResponseStatusCode() {
    }

    public ResponseStatusCode(int status_code, String reason) {
        this.status_code = status_code;
        this.reason = reason;
    }

    public int getStatus_code() {
        return this.status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
