package com.example.hw8.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * ConversionRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-08T22:52:08.119737200+03:00[Europe/Moscow]")
public class ConversionRequest {

  @JsonProperty("fromCurrency")
  private String fromCurrency;

  @JsonProperty("toCurrency")
  private String toCurrency;

  @JsonProperty("amount")
  private Double amount;

  public ConversionRequest fromCurrency(String fromCurrency) {
    this.fromCurrency = fromCurrency;
    return this;
  }

  /**
   * Get fromCurrency
   * @return fromCurrency
  */
  @NotNull 
  @Schema(name = "fromCurrency", required = true)
  public String getFromCurrency() {
    return fromCurrency;
  }

  public void setFromCurrency(String fromCurrency) {
    this.fromCurrency = fromCurrency;
  }

  public ConversionRequest toCurrency(String toCurrency) {
    this.toCurrency = toCurrency;
    return this;
  }

  /**
   * Get toCurrency
   * @return toCurrency
  */
  @NotNull 
  @Schema(name = "toCurrency", required = true)
  public String getToCurrency() {
    return toCurrency;
  }

  public void setToCurrency(String toCurrency) {
    this.toCurrency = toCurrency;
  }

  public ConversionRequest amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @NotNull 
  @Schema(name = "amount", required = true)
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversionRequest conversionRequest = (ConversionRequest) o;
    return Objects.equals(this.fromCurrency, conversionRequest.fromCurrency) &&
        Objects.equals(this.toCurrency, conversionRequest.toCurrency) &&
        Objects.equals(this.amount, conversionRequest.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromCurrency, toCurrency, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversionRequest {\n");
    sb.append("    fromCurrency: ").append(toIndentedString(fromCurrency)).append("\n");
    sb.append("    toCurrency: ").append(toIndentedString(toCurrency)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

