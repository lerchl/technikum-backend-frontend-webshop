console.log("Hello from frontend/index.js");

$("#getProductsButton").on("click", function(e) {
    // $("#productsContainer").append("Hello World!");
    $.ajax({
        url: "http://localhost:8080/products",
        type: "GET",
        cors: true,
        success: function(products) { console.log(products) },
        error: function(error) { console.error(error) }
    })
});
