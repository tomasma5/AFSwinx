function addParam(connectionType, type) {
    var modelHeaderParamsCount = document.getElementById(connectionType + type + "ParamsCount");
    var actualCount = parseInt(modelHeaderParamsCount.getAttribute("value"));
    var modelHeaderParamsWrapper = document.getElementById(connectionType + type + "Params");
    var div = document.createElement("form-group");

    var idKey = connectionType + type + "ParamKey" + (actualCount + 1);
    var idVal = connectionType + type + "ParamValue" + (actualCount + 1);
    var labelKey = createLabelElement(idKey, "Key");
    var inputKey = createInputTextElement(idKey);
    var labelValue = createLabelElement(idVal, "Value");
    var inputValue = createInputTextElement(idVal);

    div.appendChild(labelKey);
    div.appendChild(inputKey);
    div.appendChild(labelValue);
    div.appendChild(inputValue);

    modelHeaderParamsWrapper.appendChild(div);
    modelHeaderParamsCount.setAttribute("value", "" + (actualCount + 1));
}

function removeParam(connectionType, type) {
    var modelHeaderParamsCount = document.getElementById(connectionType + type + "ParamsCount");
    var actualCount = parseInt(modelHeaderParamsCount.getAttribute("value"));
    var modelHeaderParamsWrapper = document.getElementById(connectionType + type + "Params");
    var idKey = document.getElementById(connectionType + type + "ParamKey" + (actualCount));
    if (idKey !== null && idKey !== undefined) {
        modelHeaderParamsWrapper.removeChild(idKey.parentNode);
        modelHeaderParamsCount.setAttribute("value", "" + (actualCount - 1));
    }

}

function createLabelElement(id, labelText) {
    var label = document.createElement("label");
    label.setAttribute("for", id);
    label.innerText = labelText;
    return label;
}

function createInputTextElement(id) {
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("class", "form-control");
    input.setAttribute("id", id);
    input.setAttribute("name", id);
    input.setAttribute("required", "required");
    return input;
}

function toggleConnection(connectionType) {
    var checkbox = document.getElementById(connectionType + "ConnectionActive");
    var modelConnectionForm = document.getElementById(connectionType + "Connection");
    if (!checkbox.checked) {
        disableInputs(modelConnectionForm, true);
        modelConnectionForm.classList.add("notVisible");
    } else {
        disableInputs(modelConnectionForm, false);
        modelConnectionForm.classList.remove("notVisible");
    }

}

function disableInputs(el, disabled) {
    var all = el.getElementsByTagName('input'),
        i;
    for (i = 0; i < all.length; i++) {
        all[i].disabled = disabled;
    }
}