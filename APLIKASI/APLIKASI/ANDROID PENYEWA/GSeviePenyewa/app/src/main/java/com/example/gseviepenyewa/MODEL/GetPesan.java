package com.example.gseviepenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPesan {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Pesan> result = new ArrayList<Pesan>();
    @SerializedName("message")
    private String message;

    public GetPesan() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Pesan> getResult() {
        return result;
    }

    public void setResult(List<Pesan> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
