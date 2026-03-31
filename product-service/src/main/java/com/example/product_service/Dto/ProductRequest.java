package com.example.product_service.Dto;

import java.math.BigDecimal;

public record ProductRequest(String id, String name, String description, BigDecimal price)
{

}
