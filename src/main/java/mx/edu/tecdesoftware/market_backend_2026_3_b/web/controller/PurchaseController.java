package mx.edu.tecdesoftware.market_backend_2026_3_b.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.market_backend_2026_3_b.domain.Product;
import mx.edu.tecdesoftware.market_backend_2026_3_b.domain.Purchase;
import mx.edu.tecdesoftware.market_backend_2026_3_b.domain.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@Tag(name= "Compras")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    @Operation(summary = "Get all purchase",
            description = "return a list of all purchases")
    @ApiResponse(responseCode = "200",
            description = "Successful retrival of purchases")
    @ApiResponse(responseCode = "500",
            description = "Internal server error")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get purchase by ID",
            description = "Return a purchase by its ID if it exists")
    @ApiResponse(responseCode = "200",
            description = "Purchase found")
    @ApiResponse(responseCode = "404",
            description = "Purchase not found")
    @ApiResponse(responseCode = "500",
            description = "Internal server error")
    public ResponseEntity<?> getByClientId(
            @Parameter(description="ID of the purchase to be retrieved",
                    example = "7", required = true)
            @PathVariable("clientId") String clientId) {
        return purchaseService.getByClientId(clientId)
                .filter(purchases -> !purchases.isEmpty())
                .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @Operation(summary = "Create a new purchase",
            description = "Register a new purchase and return it",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Purchase",
                                    value = """
                                        {
                                          "clientId": "4546221",
                                          "date": "2026-07-16T11:15:00",
                                          "paymentMethod": "E",
                                          "comment": "Purchase registered from Swagger",
                                          "state": "P",
                                          "products": [
                                            {
                                              "productId": 7,
                                              "quantity": 2,
                                              "total": 59.00,
                                              "active": true
                                            }
                                          ]
                                        }
                                        """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Purchase created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid purchase data")
    @ApiResponse(responseCode = "404", description = "Client or product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }
}