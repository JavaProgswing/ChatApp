//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;

public class DataExtractor {
    public static final String BASE_URL = "https://yashasviallen.is-a.dev/ChatApp";
    private static HttpURLConnection last_request_content = null;
    private static int last_status_code = -1;
    private static ResponseStatusCode last_request_response = null;

    public DataExtractor() {
    }

    public static User getUserInfo() throws IOException {
        Response user_resp = Requests.get(String.format("%s/users/@me", BASE_URL), AccessTokenGenerator.getAccess_token());
        last_status_code = user_resp.status_code;
        last_request_content = user_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(user_resp.content, ResponseStatusCode.class);
        User auth = null;
        if (user_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            auth = gson.fromJson(user_resp.content, User.class);
        } else if (user_resp.status_code == 401) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
        }

        return auth;
    }

    public static void sendOTPUser(BaseUser user) throws IOException {
        Response user_resp = Requests.get(String.format("%s/registration/getOTP/users/%s", BASE_URL, user.getName()), String.valueOf(user.getPassword()));
        last_status_code = user_resp.status_code;
        last_request_content = user_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gson = builderResp.create();
        last_request_response = gson.fromJson(user_resp.content, ResponseStatusCode.class);
    }

    public static void verifyOTPUser(BaseUser user, String otpGuess) throws IOException {
        Response user_resp = Requests.get(String.format("%s/registration/verifyOTP/users/%s/%s", BASE_URL, user.getName(), otpGuess), String.valueOf(user.getPassword()));
        last_status_code = user_resp.status_code;
        last_request_content = user_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gson = builderResp.create();
        last_request_response = gson.fromJson(user_resp.content, ResponseStatusCode.class);
    }

    public static void sendRecoveryOTPUser(BaseUser user) throws IOException {
        Response user_resp = Requests.get(String.format("%s/recovery/getOTP/users/%s", BASE_URL, user.getName()), String.valueOf(user.getPassword()));
        last_status_code = user_resp.status_code;
        last_request_content = user_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gson = builderResp.create();
        last_request_response = gson.fromJson(user_resp.content, ResponseStatusCode.class);
    }

    public static void verifyRecoveryOTPUser(BaseUser user, String otpGuess) throws IOException {
        Response user_resp = Requests.get(String.format("%s/recovery/verifyOTP/users/%s/%s", BASE_URL, user.getName(), otpGuess), String.valueOf(user.getPassword()));
        last_status_code = user_resp.status_code;
        last_request_content = user_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gson = builderResp.create();
        last_request_response = gson.fromJson(user_resp.content, ResponseStatusCode.class);
    }

    public static void changePasswordUser(BaseUser user) throws IOException {
        Response user_resp = Requests.get(String.format("%s/recovery/changePassword/users/%s", BASE_URL, user.getName()), String.valueOf(user.getPassword()));
        last_status_code = user_resp.status_code;
        last_request_content = user_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gson = builderResp.create();
        last_request_response = gson.fromJson(user_resp.content, ResponseStatusCode.class);
    }

    public static void createGuild(String guildName) throws IOException {
        Response guild_resp = Requests.post(String.format("%s/guilds", BASE_URL), guildName, AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gson = builderResp.create();
        last_request_response = gson.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static Guild getGuildInfo(String guildName) throws IOException {
        Response guild_resp = Requests.get(String.format("%s/guilds/%s", BASE_URL, guildName), AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
        Guild guild = null;
        if (guild_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            guild = gson.fromJson(guild_resp.content, Guild.class);
        } else if (guild_resp.status_code == 401) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return getGuildInfo(guildName, false);
        }

        return guild;
    }

    public static Guild getGuildInfo(String guildName, boolean repeat) throws IOException {
        Response guild_resp = Requests.get(String.format("%s/guilds/%s", BASE_URL, guildName), AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
        Guild guild = null;
        if (guild_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            guild = gson.fromJson(guild_resp.content, Guild.class);
        } else if (guild_resp.status_code == 401 && repeat) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return getGuildInfo(guildName, false);
        }

        return guild;
    }

    public static UserGuilds getUserGuildInfo() throws IOException {
        Response guild_resp = Requests.get(String.format("%s/users/@me/guilds", BASE_URL), AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
        UserGuilds guilds = null;
        if (guild_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            guilds = gson.fromJson(guild_resp.content, UserGuilds.class);
        } else if (guild_resp.status_code == 401) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return getUserGuildInfo(false);
        }

        return guilds;
    }

    public static UserGuilds getUserGuildInfo(boolean repeat) throws IOException {
        Response guild_resp = Requests.get(String.format("%s/users/@me/guilds", BASE_URL), AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
        UserGuilds guilds = null;
        if (guild_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            guilds = gson.fromJson(guild_resp.content, UserGuilds.class);
        } else if (guild_resp.status_code == 401 && repeat) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return getUserGuildInfo(false);
        }

        return guilds;
    }

    public static Admin checkAdmin(String guildname) throws IOException {
        Response admin_resp = Requests.get(String.format("%s/guilds/%s/admin/@me", BASE_URL, guildname), AccessTokenGenerator.getAccess_token());
        last_status_code = admin_resp.status_code;
        last_request_content = admin_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(admin_resp.content, ResponseStatusCode.class);
        Admin checkAdmin = null;
        if (admin_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            checkAdmin = gson.fromJson(admin_resp.content, Admin.class);
        } else if (admin_resp.status_code == 401) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return checkAdmin(guildname, false);
        }

        return checkAdmin;
    }

    public static Admin checkAdmin(String guildname, boolean repeat) throws IOException {
        Response admin_resp = Requests.get(String.format("%s/guilds/%s/admin/@me", BASE_URL, guildname), AccessTokenGenerator.getAccess_token());
        last_status_code = admin_resp.status_code;
        last_request_content = admin_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(admin_resp.content, ResponseStatusCode.class);
        Admin checkAdmin = null;
        if (admin_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            checkAdmin = gson.fromJson(admin_resp.content, Admin.class);
        } else if (admin_resp.status_code == 401 && repeat) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return checkAdmin(guildname, false);
        }

        return checkAdmin;
    }

    public static void editGuildDescription(String guildname, String description) throws IOException {
        Response guild_resp = Requests.patch(String.format("%s/guilds/%s/description", BASE_URL, guildname), description, AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static void editGuildIcon(String guildname, String icon) throws IOException {
        Response guild_resp = Requests.patch(String.format("%s/guilds/%s/icon", BASE_URL, guildname), icon, AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static void addMember(String guildname, String memberName) throws IOException {
        Response guild_resp = Requests.patch(String.format("%s/guilds/%s/members", BASE_URL, guildname), memberName, AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static void removeMember(String guildname, String memberName) throws IOException {
        Response guild_resp = Requests.delete(String.format("%s/guilds/%s/members", BASE_URL, guildname), memberName, AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static void addAdmin(String guildname, String adminName) throws IOException {
        Response guild_resp = Requests.patch(String.format("%s/guilds/%s/admins", BASE_URL, guildname), adminName, AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static void removeAdmin(String guildname, String adminName) throws IOException {
        Response guild_resp = Requests.delete(String.format("%s/guilds/%s/admins", BASE_URL, guildname), adminName, AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static void sendMessage(String guildname, BaseMessage message) throws IOException {
        Response guild_resp = Requests.post(String.format("%s/guilds/%s/messages", BASE_URL, guildname), message.toString(), AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
    }

    public static Messages getMessages(String guildname) throws IOException {
        Response guild_resp = Requests.get(String.format("%s/guilds/%s/messages", BASE_URL, guildname), AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
        Messages messagesInfo = null;
        if (guild_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            messagesInfo = gson.fromJson(guild_resp.content, Messages.class);
        } else if (guild_resp.status_code == 401) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return getMessages(guildname, false);
        }

        return messagesInfo;
    }

    public static Messages getMessages(String guildname, boolean repeat) throws IOException {
        Response guild_resp = Requests.get(String.format("%s/guilds/%s/messages", BASE_URL, guildname), AccessTokenGenerator.getAccess_token());
        last_status_code = guild_resp.status_code;
        last_request_content = guild_resp.requestContent;
        GsonBuilder builderResp = new GsonBuilder();
        builderResp.setPrettyPrinting();
        Gson gsonResp = builderResp.create();
        last_request_response = gsonResp.fromJson(guild_resp.content, ResponseStatusCode.class);
        Messages messagesInfo = null;
        if (guild_resp.status_code == 200) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            messagesInfo = gson.fromJson(guild_resp.content, Messages.class);
        } else if (guild_resp.status_code == 401 && repeat) {
            new AccessTokenGenerator(AccessTokenGenerator.getName(), AccessTokenGenerator.getPassword());
            return getMessages(guildname, false);
        }

        return messagesInfo;
    }

    public static int getLast_status_code() {
        return last_status_code;
    }

    public static HttpURLConnection getLast_request_content() {
        return last_request_content;
    }

    public static ResponseStatusCode getLast_request_response() {
        return last_request_response;
    }
}
