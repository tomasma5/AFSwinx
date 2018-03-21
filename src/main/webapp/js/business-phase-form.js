var inputId = "linkedScreenText";
var hiddenId = "linkedHiddenScreen";
var buttonId = "linkedScreenButton";
var screenLabel = "Screen";

function addScreenToBusinessPhase() {
    var hiddens = document.querySelectorAll("input[id^=" + hiddenId + "]");
    var screenSelect = document.getElementById("screenSelect");
    var selectedScreenToAdd = screenSelect.options[screenSelect.selectedIndex];
    if (selectedScreenToAdd === null || selectedScreenToAdd === undefined) {
        //no screen was choosen;
        return;
    }
    var toBeAddedId = selectedScreenToAdd.value;
    var toBeAddedText = selectedScreenToAdd.text;
    for (var i = 0; i < hiddens.length; i++) {
        if (hiddens[i].value === toBeAddedId) {
            //screen already added
            alert("Screen is already placed on this screen");
            return;
        }
    }
    var linkedScreensCount = document.getElementById("linkedScreensCount");
    var actualCount = parseInt(linkedScreensCount.getAttribute("value"));
    var linkedScreensWrapper = document.getElementById("linkedScreens");

    var div = document.createElement("form-group");
    var id = hiddenId + (actualCount + 1);
    var idText = inputId + (actualCount + 1);

    var inputGroup = document.createElement("div");
    inputGroup.setAttribute("class", "input-group");

    var screenIdInput = createHiddenIdField(id);
    screenIdInput.setAttribute("value", toBeAddedId);
    var screenFieldLabel = createLabelElement(id, screenLabel + " " + (actualCount + 1).toString());
    var screenFieldInput = createInputTextElement(idText);
    screenFieldInput.setAttribute("disabled", "disabled");
    screenFieldInput.setAttribute("value", toBeAddedText);

    var span = document.createElement("span");
    span.setAttribute("class", "input-group-btn");
    var button = document.createElement("button");
    button.setAttribute("class", "btn btn-danger");
    button.setAttribute("type", "button");
    button.setAttribute("id", buttonId + (actualCount + 1).toString());
    button.innerText = "Remove";
    button.addEventListener("click", removeScreenFromBusinessPhase.bind(null, (actualCount + 1)));
    span.appendChild(button);

    div.appendChild(screenFieldLabel);
    div.appendChild(screenIdInput);
    inputGroup.appendChild(screenFieldInput);
    inputGroup.appendChild(span);
    div.appendChild(inputGroup);

    linkedScreensWrapper.appendChild(div);
    linkedScreensCount.setAttribute("value", "" + (actualCount + 1));
    screenSelect.removeChild(screenSelect.options[screenSelect.selectedIndex]);
}

function removeScreenFromBusinessPhase(linkedScreenId) {
    var screenSelect = document.getElementById("screenSelect");
    var linkedScreenEl = document.getElementById(hiddenId + linkedScreenId.toString());
    var linkedScreenInputEl = document.getElementById(inputId + linkedScreenId.toString());
    var linkedScreensWrapper = document.getElementById("linkedScreens");

    //put back as a option
    var option = createOption(linkedScreenEl.getAttribute("value"), linkedScreenInputEl.getAttribute("value"));
    screenSelect.appendChild(option);

    //remove it from html
    linkedScreensWrapper.removeChild(linkedScreenEl.parentNode);
    var linkedScreensCount = document.getElementById("linkedScreensCount");
    var actualCount = parseInt(linkedScreensCount.getAttribute("value"));
    linkedScreensCount.setAttribute("value", "" + (actualCount - 1));

    //recalculate next indexes
    var inputs = document.querySelectorAll("input[id^=" + inputId + "]");
    var hiddens = document.querySelectorAll("input[id^=" + hiddenId + "]");
    var labels = document.querySelectorAll("label[for^=" + hiddenId + "]");
    var buttons = document.querySelectorAll("button[id^=" + buttonId + "]");
    for (var i = 0; i < inputs.length; i++) {
        var j = i + 1;
        inputs[i].id = inputId + j.toString();
        inputs[i].name = inputId + j.toString();
        hiddens[i].id = hiddenId + j.toString();
        hiddens[i].name = hiddenId + j.toString();
        labels[i].for = hiddenId + j.toString();
        labels[i].innerText = screenLabel + " " + j.toString();
        buttons[i].id = buttonId + j.toString();
        var buttonClone = buttons[i].cloneNode(true);
        buttonClone.addEventListener("click", removeScreenFromBusinessPhase.bind(null, j));
        buttons[i].parentNode.replaceChild(buttonClone, buttons[i]);
    }
}

function createOption(value, text) {
    var option = document.createElement("option");
    option.setAttribute("value", value);
    option.innerText = text;
    option.addEventListener("dblclick", addScreenToBusinessPhase);
    return option;
}

function createLabelElement(id, labelText) {
    var label = document.createElement("label");
    label.setAttribute("for", id);
    label.innerText = labelText;
    return label;
}

function createHiddenIdField(id) {
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", id);
    input.setAttribute("name", id);
    input.setAttribute("required", "required");
    return input;
}
