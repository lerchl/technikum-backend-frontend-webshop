$("#getProductsButton").on("click", () => {
    $.ajax({
        url: "http://localhost:8080/products",
        type: "GET",
        cors: true,
        success: products => {
            const container = $("#productsContainer");
            products.forEach(product => {
                const productParagraph = $(`<p>${product.name} - ${product.description} | ${product.price}€ </p>`);
                const quantityButton = $("<button>Get Quantity</button>");
                quantityButton.on("click", () => getQuantity(product.id).then(quantity => productParagraph.append(`<span>Quantity: ${quantity}</span>`)));

                productParagraph.append(quantityButton);
                container.append(productParagraph);
            });
        },
        error: error => console.log("Error: ", error)
    });
});

const getQuantity = (productId) => {
    return $.ajax({
        url: "http://localhost:8080/products/quantity/" + productId,
        type: "GET",
        cors: true
    });
}
