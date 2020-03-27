package com.lintasbandung.lintasbandungapps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.snap.CreditCard;

public class DataUser {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("activation_selector")
    @Expose
    private Object activationSelector;
    @SerializedName("activation_code")
    @Expose
    private Object activationCode;
    @SerializedName("forgotten_password_selector")
    @Expose
    private Object forgottenPasswordSelector;
    @SerializedName("forgotten_password_code")
    @Expose
    private Object forgottenPasswordCode;
    @SerializedName("forgotten_password_time")
    @Expose
    private Object forgottenPasswordTime;
    @SerializedName("remember_selector")
    @Expose
    private String rememberSelector;
    @SerializedName("remember_code")
    @Expose
    private String rememberCode;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("phone")
    @Expose
    private String phone;

    public DataUser(String id, String ipAddress, String username, String password, String email, Object activationSelector, Object activationCode, Object forgottenPasswordSelector, Object forgottenPasswordCode, Object forgottenPasswordTime, String rememberSelector, String rememberCode, String createdOn, String lastLogin, String active, String firstName, String lastName, String company, String phone) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
        this.email = email;
        this.activationSelector = activationSelector;
        this.activationCode = activationCode;
        this.forgottenPasswordSelector = forgottenPasswordSelector;
        this.forgottenPasswordCode = forgottenPasswordCode;
        this.forgottenPasswordTime = forgottenPasswordTime;
        this.rememberSelector = rememberSelector;
        this.rememberCode = rememberCode;
        this.createdOn = createdOn;
        this.lastLogin = lastLogin;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getActivationSelector() {
        return activationSelector;
    }

    public void setActivationSelector(Object activationSelector) {
        this.activationSelector = activationSelector;
    }

    public Object getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(Object activationCode) {
        this.activationCode = activationCode;
    }

    public Object getForgottenPasswordSelector() {
        return forgottenPasswordSelector;
    }

    public void setForgottenPasswordSelector(Object forgottenPasswordSelector) {
        this.forgottenPasswordSelector = forgottenPasswordSelector;
    }

    public Object getForgottenPasswordCode() {
        return forgottenPasswordCode;
    }

    public void setForgottenPasswordCode(Object forgottenPasswordCode) {
        this.forgottenPasswordCode = forgottenPasswordCode;
    }

    public Object getForgottenPasswordTime() {
        return forgottenPasswordTime;
    }

    public void setForgottenPasswordTime(Object forgottenPasswordTime) {
        this.forgottenPasswordTime = forgottenPasswordTime;
    }

    public String getRememberSelector() {
        return rememberSelector;
    }

    public void setRememberSelector(String rememberSelector) {
        this.rememberSelector = rememberSelector;
    }

    public String getRememberCode() {
        return rememberCode;
    }

    public void setRememberCode(String rememberCode) {
        this.rememberCode = rememberCode;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static TransactionRequest transactionRequest(String id, int price, int qty, String name) {

        int jumlah = price * qty;

        TransactionRequest request = new TransactionRequest(System.currentTimeMillis() + " ", jumlah);

        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_RBA);
        creditCard.setBank(BankType.MANDIRI);
        request.setCreditCard(creditCard);
        return request;

    }
}
