import { useState } from "react";

function ItemCreateForm({ storageName, refreshItems }) {
    const [name, setName] = useState("");
    const [weight, setWeight] = useState(0);
    const [weirdnessLevel, setWeirdnessLevel] = useState(0);
    const [isSensitive, setIsSensitive] = useState(false);

    function submitForm(event) {
        event.preventDefault(true);
    
        const data = { 
            name, 
            weightKg: weight, 
            weirdnessLevel,
            isSensitive
        };

        fetch(`http://localhost:8080/storages/${storageName}`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(data) })
            .then((response) => {
              if(response.status !== 200) {
                response.text().then((text) => {
                  if(text === "EXCEPTION_CASE_UNFULFILLED") {
                    alert("Nie spełniono specjalnego warunku!");
                  }

                  if(text === "INVALID_WEIRDNESS_LEVEL") {
                    alert("Zły poziom dziwności. Musi być w zakresie 1-10.");
                  }

                  if(text === "MAX_CAPACITY_REACHED") {
                    alert("Nie można dodać przedmiotu: magazyn jest pełny!");
                  }
                  
                  if(text === "MAX_WEIGHT_REACHED") {
                    alert("Nie można dodać przedmiotu: magazyn nie utrzyma więcej wagi!");
                  }

                  if(text === "STORAGE_NOT_FOUND") {
                    alert("Nie znaleziono magazynu!");
                  }
                });
              } else {
                alert("Pomyślnie dodano przedmiot do magazynu.");
                refreshItems();
              }
            });
    }

    return <form className="table-modal-form" style={{ width: "200px" }} onSubmit={submitForm}>
        <h1 className="table-modal-form-header">Dodaj przedmiot do {storageName}</h1>
        <div className="table-modal-form-row">
            <label htmlFor="name">Nazwa</label>
            <input type="text" name="name" id="name-input" onInput={(event) => setName(event.target.value)} />
        </div>
        <div className="table-modal-form-row">
            <label htmlFor="weight">Waga</label>
            <input type="text" name="weight" id="weight-input" onInput={(event) => setWeight(event.target.value)} />
        </div>
        <div className="table-modal-form-row">
            <label htmlFor="weirdness-level">Poziom dziwności</label>
            <select type="text" name="weirdness-level" id="weirdness-level-input" onInput={(event) => setWeirdnessLevel(event.target.value)}>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
              <option value="9">9</option>
              <option value="10">10</option>
            </select>
        </div>
        <div className="table-modal-form-row">
            <label htmlFor="is-sensitive">Delikatne</label>
            <input type="checkbox" name="is-sensitive" id="is-sensitive-input" onChange={(event) => setIsSensitive(event.target.checked)} />
        </div>
        <input type="submit" value="Stwórz" />
    </form>
}

export default ItemCreateForm;
