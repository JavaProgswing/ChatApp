//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

import java.net.HttpURLConnection;

class Response {
    public HttpURLConnection requestContent;
    public int status_code;
    public String content;

    public Response(int status_code, String content, HttpURLConnection requestContent) {
        this.status_code = status_code;
        this.content = content;
        this.requestContent = requestContent;
    }

    public Response(int status_code, StringBuffer content, HttpURLConnection requestContent) {
        this.status_code = status_code;
        if (content != null) {
            this.content = content.toString();
        }

        this.requestContent = requestContent;
    }

    public String toString() {
        return "Response{requestContent=" + this.requestContent + ", status_code=" + this.status_code + ", content='" + this.content + "'}";
    }
}
