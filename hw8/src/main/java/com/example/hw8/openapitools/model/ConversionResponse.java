package com.example.hw8.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * ConversionResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-08T22:52:08.119737200+03:00[Europe/Moscow]")
public class ConversionResponse {

  @JsonProperty("fromCurrency")
  private String fromCurrency;

  @JsonProperty("toCurrency")
  private String toCurrency;

  @JsonProperty("convertedAmount")
  private Double convertedAmount;

  public ConversionResponse fromCurrency(String fromCurrency) {
    this.fromCurrency = fromCurrency;
    return this;
  }

  /**
   * Get fromCurrency
   * @return fromCurrency
  */
  
  @Schema(name = "fromCurrency", required = false)
  public String getFromCurrency() {
    return fromCurrency;
  }

  public void setFromCurrency(String fromCurrency) {
    this.fromCurrency = fromCurrency;
  }

  public ConversionResponse toCurrency(String toCurrency) {
    this.toCurrency = toCurrency;
    return this;
  }

  /**
   * Get toCurrency
   * @return toCurrency
  */
  
  @Schema(name = "toCurrency", required = false)
  public String getToCurrency() {
    return toCurrency;
  }

  public void setToCurrency(String toCurrency) {
    this.toCurrency = toCurrency;
  }

  public ConversionResponse convertedAmount(Double convertedAmount) {
    this.convertedAmount = convertedAmount;
    return this;
  }

  /**
   * Get convertedAmount
   * @return convertedAmount
  */
  
  @Schema(name = "convertedAmount", required = false)
  public Double getConvertedAmount() {
    return convertedAmount;
  }

  public void setConvertedAmount(Double convertedAmount) {
    this.convertedAmount = convertedAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversionResponse conversionResponse = (ConversionResponse) o;
    return Objects.equals(this.fromCurrency, conversionResponse.fromCurrency) &&
        Objects.equals(this.toCurrency, conversionResponse.toCurrency) &&
        Objects.equals(this.convertedAmount, conversionResponse.convertedAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromCurrency, toCurrency, convertedAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversionResponse {\n");
    sb.append("    fromCurrency: ").append(toIndentedString(fromCurrency)).append("\n");
    sb.append("    toCurrency: ").append(toIndentedString(toCurrency)).append("\n");
    sb.append("    convertedAmount: ").append(toIndentedString(convertedAmount)).append("\n");
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

