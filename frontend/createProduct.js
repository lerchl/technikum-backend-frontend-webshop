// hier wird dem Element mit der Id "createProductButton" ein listener
// für das onclick-Event hinzugefügt
$("#createProductButton").on("click", _e => {
    // wir erstellen ein object und setzen die 
    // Werte aus den Eingabefeldern
    const product = {
        "name": $("#nameInput").val(),
        "description": $("#descriptionInput").val(),
        "imageUrl": $("#imageInput").val(),
        "price": $("#priceInput").val(),
        "quantity": $("#quantityInput").val(),
        "type": $("#typeInput").val()
    }

    // dann senden wir eine POST request mit dem neuen
    // product als json string an das backend
    $.ajax({
        url: "http://localhost:8080/products",
        type: "POST",
        cors: true,
        contentType: "application/json",
        data: JSON.stringify(product),
        success: console.log,
        error: console.error
    });
});
