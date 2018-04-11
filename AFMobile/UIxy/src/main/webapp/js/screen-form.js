var componentInputId = "linkedComponentText";
var componentHiddenId = "linkedHiddenComponent";
var componentButtonId = "linkedComponentButton";
var componentLabel = "Component";

function addComponent() {
    var hiddens = document.querySelectorAll("input[id^=" + componentHiddenId + "]");
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
    var id = componentHiddenId + (actualCount + 1);
    var idText = componentInputId + (actualCount + 1);

    var componentInputGroup = document.createElement("div");
    componentInputGroup.setAttribute("class", "input-group");

    var componentIdInput = createHiddenIdField(id);
    componentIdInput.setAttribute("value", toBeAddedId);
    var componentFieldLabel = createLabelElement(id, componentLabel + " " + (actualCount + 1).toString());
    var componentFieldInput = createInputTextElement(idText);
    componentFieldInput.setAttribute("disabled", "disabled");
    componentFieldInput.setAttribute("value", toBeAddedText);

    var componentSpan = document.createElement("span");
    componentSpan.setAttribute("class", "input-group-btn");
    var componentButton = document.createElement("button");
    componentButton.setAttribute("class", "btn btn-danger");
    componentButton.setAttribute("type", "button");
    componentButton.setAttribute("id", componentButtonId + (actualCount + 1).toString());
    componentButton.innerText = "Remove";
    componentButton.addEventListener("click", removeComponent.bind(null, (actualCount + 1)));
    componentSpan.appendChild(componentButton);

    div.appendChild(componentFieldLabel);
    div.appendChild(componentIdInput);
    componentInputGroup.appendChild(componentFieldInput);
    componentInputGroup.appendChild(componentSpan);
    div.appendChild(componentInputGroup);

    linkedComponentsWrapper.appendChild(div);
    linkedComponentsCount.setAttribute("value", "" + (actualCount + 1));
    componentSelect.removeChild(componentSelect.options[componentSelect.selectedIndex]);
}

function removeComponent(linkedComponentId) {
    var componentSelect = document.getElementById("componentSelect");
    var linkedComponentEl = document.getElementById(componentHiddenId + linkedComponentId.toString());
    var linkedComponentInputEl = document.getElementById(componentInputId + linkedComponentId.toString());
    var linkedComponentsWrapper = document.getElementById("linkedComponents");

    //put back as a option
    var option = createOption(linkedComponentEl.getAttribute("value"), linkedComponentInputEl.getAttribute("value"));
    componentSelect.appendChild(option);

    //remove it from html
    linkedComponentsWrapper.removeChild(linkedComponentEl.parentNode);
    var linkedComponentsCount = document.getElementById("linkedComponentsCount");
    var actualCount = parseInt(linkedComponentsCount.getAttribute("value"));
    linkedComponentsCount.setAttribute("value", "" + (actualCount - 1));

    //recalculate next indexes
    var inputs = document.querySelectorAll("input[id^=" + componentInputId + "]");
    var hiddens = document.querySelectorAll("input[id^=" + componentHiddenId + "]");
    var labels = document.querySelectorAll("label[for^=" + componentHiddenId + "]");
    var buttons = document.querySelectorAll("button[id^=" + componentButtonId + "]");
    for (var i = 0; i < inputs.length; i++) {
        var j = i + 1;
        inputs[i].id = componentInputId + j.toString();
        inputs[i].name = componentInputId + j.toString();
        hiddens[i].id = componentHiddenId + j.toString();
        hiddens[i].name = componentHiddenId + j.toString();
        labels[i].for = componentHiddenId + j.toString();
        labels[i].innerText = componentLabel + " " + j.toString();
        buttons[i].id = componentButtonId + j.toString();
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
