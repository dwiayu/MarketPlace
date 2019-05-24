package com.example.penyewa.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetKostumAll {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<KostumAll> result = new ArrayList<KostumAll>();
    @SerializedName("message")
    private String message;

    public GetKostumAll() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<KostumAll> getResult() {
        return result;
    }

    public void setResult(List<KostumAll> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
