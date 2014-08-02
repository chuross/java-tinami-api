package com.chuross.api.tinami;

public interface OnLoginSessionExpiredListener {

    public void onSessionChanged(String authKey);

    public void onChangedAccountInfo();

}
