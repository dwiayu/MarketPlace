package com.example.gseviepenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetTempat {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<TempatSewa> result = new ArrayList<TempatSewa>();
    @SerializedName("message")
    private String message;

    public GetTempat() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TempatSewa> getResult() {
        return result;
    }

    public void setResult(List<TempatSewa> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
