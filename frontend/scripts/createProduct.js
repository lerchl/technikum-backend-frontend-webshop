$.get({
    url: "http://localhost:8080/taxRates",
    cors: true,
    // wenn die request erfolgreich war, wird die Funktion
    // addTaxRatesToSelect aufgerufen und die taxRates werden
    // als parameter übergeben
    // success: function (taxRates) { addTaxRatesToSelect(taxRates) }
    // ist das gleiche wie:
    success: addTaxRatesToSelect,
    error: console.error
});

function addTaxRatesToSelect(taxRates) {
    const taxRateInput = $("#taxRateInput");

    taxRates.forEach(taxRate => {
        taxRateInput.append(`<option value="${taxRate.id}">${taxRate.name}</option>`);
    });
}

// hier wird dem Element mit der Id "createProductButton" ein listener
// für das onclick-Event hinzugefügt
$("#createProductButton").on("click", _e => {
    // entfernen der input-error class von allen elementen,
    // die die input-error class besitzen
    $(".input-error").removeClass("input-error");

    // löscht alle elemente mit der klasse error-message
    $(".error-message").remove();

    // wir erstellen ein object und setzen die 
    // Werte aus den Eingabefeldern
    const product = {
        "name": $("#nameInput").val(),
        "description": $("#descriptionInput").val(),
        "imageUrl": $("#imageInput").val(),
        "price": $("#priceInput").val(),
        "quantity": $("#quantityInput").val(),
        "type": $("#typeInput").val(),
        "taxRateId": $("#taxRateInput").val(),
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
        error: error => {
            console.log(error);
            if (error.status === 400) {

                // über alle Fehler iterieren
                for (let err of error.responseJSON.errors) {
                    // das inputfeld entsprechend dem Fehler raussuchen
                    const input = $("#" + err.field + "Input");
                    // klasse für die anzeige des fehlerhaften feldes hinzufügen
                    input.addClass("input-error");

                    // den parent des inputfeldes raussuchen
                    const parent = input.parent();
                    // anfügen der fehlermeldung im parent
                    parent.append(`<p class="error-message">${err.defaultMessage}</p>`);
                }

                // aufruf der Funktion, damit die Toasts angezeigt werden
                handleErrors(error.responseJSON.errors);
            }
        }
    });
});
