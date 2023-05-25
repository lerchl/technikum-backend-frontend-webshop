$.ajax({
    url: "http://localhost:8080/products",
    cors: true,
    headers: { "Authorization": sessionStorage.getItem("token") },
    success: function(products) { addProductsToPage(products) },
    error: function(error) { console.error(error) }
});

function addProductsToPage(products) {
    const productsContainer = $("#productsContainer");
    productsContainer.empty();

    let row;
    for (let i = 0; i < products.length; i++) {
        if (i % 3 === 0) {
            row = $(`<div class="row"></div>`);
            productsContainer.append(row);
        }

        row.append(createProduct(products[i]));
    }
}

function createProduct(product) {
    const card = $(`<div class="card col-md-4"></div>`);
    const img = $(`<img src="${product.imageUrl}" height="200" class="card-img-top" alt="product image">`);
    const cardBody = $(`<div class="card-body"></div>`);
    const title = $(`<h5 class="card-title">${product.name}<span style="position: absolute; right: 1rem;">${product.price}€</span></h5>`);
    const description = $(`<p class="card-text">${product.description}</p>`);
    const footer = $(`<div class="card-footer row"></div>`);
    const quantity = $(`<input id="productQuantity${product.id}" type="number" value="1" min="1" max="10" class="col-4 mr-2">`);
    const button = $(`<button class="btn btn-primary col-8">Add to cart</button>`);

    button.on("click", function(_e) { addProductToCart(product, quantity.val()); })

    footer.append(quantity);
    footer.append(button);

    cardBody.append(title);
    cardBody.append(description);
    cardBody.append(footer);

    card.append(img);
    card.append(cardBody);

    return card;
}

function addProductToCart(product, quantity) {
    const data = {
        productId: product.id,
        quantity: quantity
    }

    $.post({
        url: "http://localhost:8080/positions",
        headers: { "Authorization": sessionStorage.getItem("token") },
        cors: true,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: console.log,
        error: console.error
    });
}
