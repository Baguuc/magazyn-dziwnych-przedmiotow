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
            weight, 
            weirdnessLevel,
            isSensitive
        };
        console.log(data);
        fetch(`http://localhost:8080/storages/${storageName}`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(data) })
            .then(refreshItems);
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
            <input type="text" name="weirdness-level" id="weirdness-level-input" onInput={(event) => setWeirdnessLevel(event.target.value)} />
        </div>
        <div className="table-modal-form-row">
            <label htmlFor="is-sensitive">Delikatne</label>
            <input type="text" name="is-sensitive" id="is-sensitive-input" onInput={(event) => setIsSensitive(event.target.value)} />
        </div>
        <input type="submit" value="Stwórz" />
    </form>
}

export default ItemCreateForm;
