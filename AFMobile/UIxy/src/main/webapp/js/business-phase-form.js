var screenInputId = "linkedScreenText";
var screenHiddenId = "linkedHiddenScreen";
var screenButtonId = "linkedScreenButton";
var screenLabel = "Screen";

function addScreenToBusinessPhase() {
    var hiddens = document.querySelectorAll("input[id^=" + screenHiddenId + "]");
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
    var id = screenHiddenId + (actualCount + 1);
    var idText = screenInputId + (actualCount + 1);

    var screenInputGroup = document.createElement("div");
    screenInputGroup.setAttribute("class", "input-group");

    var screenIdInput = createHiddenIdField(id);
    screenIdInput.setAttribute("value", toBeAddedId);
    var screenFieldLabel = createLabelElement(id, screenLabel + " " + (actualCount + 1).toString());
    var screenFieldInput = createInputTextElement(idText);
    screenFieldInput.setAttribute("disabled", "disabled");
    screenFieldInput.setAttribute("value", toBeAddedText);

    var screenSpan = document.createElement("span");
    screenSpan.setAttribute("class", "input-group-btn");
    var screenButton = document.createElement("button");
    screenButton.setAttribute("class", "btn btn-danger");
    screenButton.setAttribute("type", "button");
    screenButton.setAttribute("id", screenButtonId + (actualCount + 1).toString());
    screenButton.innerText = "Remove";
    screenButton.addEventListener("click", removeScreenFromBusinessPhase.bind(null, (actualCount + 1)));
    screenSpan.appendChild(screenButton);

    div.appendChild(screenFieldLabel);
    div.appendChild(screenIdInput);
    screenInputGroup.appendChild(screenFieldInput);
    screenInputGroup.appendChild(screenSpan);
    div.appendChild(screenInputGroup);

    linkedScreensWrapper.appendChild(div);
    linkedScreensCount.setAttribute("value", "" + (actualCount + 1));
    screenSelect.removeChild(screenSelect.options[screenSelect.selectedIndex]);
}

function removeScreenFromBusinessPhase(linkedScreenId) {
    var screenSelect = document.getElementById("screenSelect");
    var linkedScreenEl = document.getElementById(screenHiddenId + linkedScreenId.toString());
    var linkedScreenInputEl = document.getElementById(screenInputId + linkedScreenId.toString());
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
    var inputs = document.querySelectorAll("input[id^=" + screenInputId + "]");
    var hiddens = document.querySelectorAll("input[id^=" + screenHiddenId + "]");
    var labels = document.querySelectorAll("label[for^=" + screenHiddenId + "]");
    var buttons = document.querySelectorAll("button[id^=" + screenButtonId + "]");
    for (var i = 0; i < inputs.length; i++) {
        var j = i + 1;
        inputs[i].id = screenInputId + j.toString();
        inputs[i].name = screenInputId + j.toString();
        hiddens[i].id = screenHiddenId + j.toString();
        hiddens[i].name = screenHiddenId + j.toString();
        labels[i].for = screenHiddenId + j.toString();
        labels[i].innerText = screenLabel + " " + j.toString();
        buttons[i].id = screenButtonId + j.toString();
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
