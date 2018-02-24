var inputId = "linkedComponentText";
var hiddenId = "linkedHiddenComponent";
var buttonId = "linkedComponentButton";
var componentLabel = "Component";

function addComponent() {
    var hiddens = document.querySelectorAll("input[id^=" + hiddenId + "]");
    var componentSelect = document.getElementById("componentSelect");
    var selectedComponetntToAdd = componentSelect.options[componentSelect.selectedIndex];
    if (selectedComponetntToAdd === null || selectedComponetntToAdd === undefined) {
        //no component was choosen;
        return;
    }
    var toBeAddedId = selectedComponetntToAdd.value;
    var toBeAddedText = selectedComponetntToAdd.text;
    for (var i = 0; i < hiddens.length; i++) {
        if (hiddens[i].value === toBeAddedId) {
            //component already added
            alert("Component is already placed on this screen");
            return;
        }
    }
    var linkedComponentsCount = document.getElementById("linkedComponentsCount");
    var actualCount = parseInt(linkedComponentsCount.getAttribute("value"));
    var linkedComponentsWrapper = document.getElementById("linkedComponents");

    var div = document.createElement("form-group");
    var id = hiddenId + (actualCount + 1);
    var idText = inputId + (actualCount + 1);

    var inputGroup = document.createElement("div");
    inputGroup.setAttribute("class", "input-group");

    var componentIdInput = createHiddenIdField(id);
    componentIdInput.setAttribute("value", toBeAddedId);
    var componentFieldLabel = createLabelElement(id, componentLabel + " " + (actualCount + 1).toString());
    var componentFieldInput = createInputTextElement(idText);
    componentFieldInput.setAttribute("disabled", "disabled");
    componentFieldInput.setAttribute("value", toBeAddedText);

    var span = document.createElement("span");
    span.setAttribute("class", "input-group-btn");
    var button = document.createElement("button");
    button.setAttribute("class", "btn btn-danger");
    button.setAttribute("type", "button");
    button.setAttribute("id", buttonId + (actualCount + 1).toString());
    button.innerText = "Remove";
    button.addEventListener("click", removeComponent.bind(null, (actualCount + 1)));
    span.appendChild(button);

    div.appendChild(componentFieldLabel);
    div.appendChild(componentIdInput);
    inputGroup.appendChild(componentFieldInput);
    inputGroup.appendChild(span);
    div.appendChild(inputGroup);

    linkedComponentsWrapper.appendChild(div);
    linkedComponentsCount.setAttribute("value", "" + (actualCount + 1));
    componentSelect.removeChild(componentSelect.options[componentSelect.selectedIndex]);
}

function removeComponent(linkedComponentId) {
    //get needed DOM elements //TODO maybe split to multiple functions
    var componentSelect = document.getElementById("componentSelect");
    var linkedComponentEl = document.getElementById(hiddenId + linkedComponentId.toString());
    var linkedComponentInputEl = document.getElementById(inputId + linkedComponentId.toString());
    var linkedComponentsWrapper = document.getElementById("linkedComponents");

    //put back as a option
    var option = createOption(linkedComponentEl.getAttribute("value"), linkedComponentInputEl.getAttribute("value"));
    componentSelect.appendChild(option);
    componentSelect.options

    //remove it from html
    linkedComponentsWrapper.removeChild(linkedComponentEl.parentNode);
    var linkedComponentsCount = document.getElementById("linkedComponentsCount");
    var actualCount = parseInt(linkedComponentsCount.getAttribute("value"));
    linkedComponentsCount.setAttribute("value", "" + (actualCount - 1));

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
        labels[i].innerText = componentLabel + " " + j.toString();
        buttons[i].id = buttonId + j.toString();
        var buttonClone = buttons[i].cloneNode(true);
        buttonClone.addEventListener("click", removeComponent.bind(null, j));
        buttons[i].parentNode.replaceChild(buttonClone, buttons[i]);
    }
}

function createOption(value, text) {
    var option = document.createElement("option");
    option.setAttribute("value", value);
    option.innerText = text;
    option.addEventListener("dblclick", addComponent);
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
