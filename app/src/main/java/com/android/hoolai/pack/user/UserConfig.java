package com.android.hoolai.pack.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.hoolai.pack.GlobalContext;
import com.android.hoolai.pack.domain.LoginUser;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/5/30.
 */
public class UserConfig {

    private static LoginUser currentUser;
    private static final Object sync = new Object();
    public static String accessToken = "";

    public static void setCurrentUser(LoginUser user) {
        synchronized (sync) {
            currentUser = user;
            accessToken = user.getToken();
        }
    }

    public static LoginUser getCurrentUser() {
        synchronized (sync) {
            return currentUser;
        }
    }

    public static void saveConfig(boolean withFile) {
        synchronized (sync) {
            SharedPreferences sp = GlobalContext.getGlobalContext().getSharedPreferences("userConfig", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            if (currentUser != null) {
                if (withFile) {
                    editor.putString("user", new Gson().toJson(currentUser));
                }
            } else {
                editor.remove("user");
            }
            editor.apply();
        }
    }

    public static void loadConfig() {
        synchronized (sync) {
            SharedPreferences preferences = GlobalContext.getGlobalContext().getSharedPreferences("userConfig", Context.MODE_PRIVATE);
            String user = preferences.getString("user", null);
            if (!TextUtils.isEmpty(user)) {
                currentUser = new Gson().fromJson(user, LoginUser.class);
            }
        }
    }

    public static void clearConfig() {
        currentUser = null;
        saveConfig(true);
    }
}
