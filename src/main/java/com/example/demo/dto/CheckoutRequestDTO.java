package com.example.demo.dto;

import java.util.List;

public class CheckoutRequestDTO {
    private Integer idDistributeur;
    private List<CartItemDTO> items;

    public CheckoutRequestDTO() {
    }

    public CheckoutRequestDTO(Integer idDistributeur, List<CartItemDTO> items) {
        this.idDistributeur = idDistributeur;
        this.items = items;
    }

    public Integer getIdDistributeur() {
        return idDistributeur;
    }

    public void setIdDistributeur(Integer idDistributeur) {
        this.idDistributeur = idDistributeur;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}
