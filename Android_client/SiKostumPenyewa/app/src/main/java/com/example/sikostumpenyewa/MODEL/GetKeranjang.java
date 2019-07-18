package com.example.sikostumpenyewa.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetKeranjang {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Keranjang> result = new ArrayList<Keranjang>();
    @SerializedName("message")
    private String message;

    public GetKeranjang() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Keranjang> getResult() {
        return result;
    }

    public void setResult(List<Keranjang> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
