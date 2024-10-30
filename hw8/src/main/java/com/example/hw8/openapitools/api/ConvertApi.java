package com.example.hw8.openapitools.api;

import com.example.hw8.openapitools.model.ConversionRequest;
import com.example.hw8.openapitools.model.ConversionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-08T22:52:08.119737200+03:00[Europe/Moscow]")
@Validated
@Tag(name = "convert", description = "the convert API")
public interface ConvertApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @Operation(
            operationId = "convertCurrency",
            summary = "Convert currency amount",
            tags = { "currencies" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conversion result", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ConversionResponse.class), examples = {
                                    @ExampleObject(name = "example", value = "{ \"convertedAmount\": 0.8008281904610115, \"toCurrency\": \"USD\", \"fromCurrency\": \"EUR\" }")
                            })
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/convert",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<ConversionResponse> convertCurrency(
            @Parameter(name = "ConversionRequest", description = "Conversion request", required = true) @Valid @RequestBody ConversionRequest conversionRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}