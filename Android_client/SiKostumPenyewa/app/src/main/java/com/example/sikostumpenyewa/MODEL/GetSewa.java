package com.example.sikostumpenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetSewa {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Sewa> result = new ArrayList<Sewa>();
    @SerializedName("message")
    private String message;

    public GetSewa() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sewa> getResult() {
        return result;
    }

    public void setResult(List<Sewa> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
