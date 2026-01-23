package com.assettracker.main.telegram_bot.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum Buttons {
    MY_BAG("my_bag"),
    MY_PROFILE("my_profile"),
    ANY_ASSET("any_asset"),
    MANAGE_ASSETS("manage_assets"),
    CREATE_ASSET("create_asset"),
    UPDATE_ASSET("update_asset"),
    DELETE_ASSET("delete_asset"),
    UPDATE_BAG_DATA("update_bag_data"),
    MY_ASSETS("my_assets"),
    CANCEL_TO_MY_ASSETS("cancel_to_my_assets"),
    ASSET_STATISTICS("asset_statistics"),
    CANCEL_TO_BAG_MENU("cancel_to_bag_menu"),
    CANCEL_TO_MENU("cancel_to_menu"),
    CREATE_ASSET_AFTER_TRY_DELETE("create_asset_after_try_delete"),
    FORCE_UPDATE_ASSET("force_update_asset"),
    CANCEL_TO_MANAGE_ASSETS("cancel_to_manage_assets"),
    FORCE_CREATE_ASSET("force_create_asset");

    @Getter
    private final String callbackData;

    public static Buttons parseCallbackData(String callbackData) {
        return Arrays.stream(Buttons.values())
                .filter(button -> button.getCallbackData().equals(callbackData))
                .findFirst()
                .orElseThrow();
    }
}
