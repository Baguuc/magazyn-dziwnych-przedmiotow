import { useState } from "react";

function StorageCreateForm({ refreshStorages }) {
    const [name, setName] = useState("");
    const [capacity, setCapacity] = useState(0);
    const [maxTotalWeight, setMaxTotalWeight] = useState(0);
    
    function submitForm(event) {
        event.preventDefault(true);
    
        const data = { 
            name, 
            capacity, 
            maxTotalWeight
        };
        console.log(data);
        fetch("http://localhost:8080/storages", { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(data) })
            .then(() => {
              alert("Pomyślnie stworzono magazyn.");
              refreshStorages();
            });
    }

    return <form className="table-modal-form" onSubmit={submitForm}>
        <h1 className="table-modal-form-header">Stwórz magazyn</h1>
        <div className="table-modal-form-row">
            <label htmlFor="name">Nazwa</label>
            <input type="text" name="name" id="name-input" onInput={(event) => setName(event.target.value)} />
        </div>
        <div className="table-modal-form-row">
            <label htmlFor="capacity">Pojemność</label>
            <input type="text" name="capacity" id="capacity-input" onInput={(event) => setCapacity(event.target.value)} />
        </div>
        <div className="table-modal-form-row">
            <label htmlFor="max-weight">Maksymalna łączna waga</label>
            <input type="text" name="max-weight" id="max-weight-input" onInput={(event) => setMaxTotalWeight(event.target.value)} />
        </div>
        <input type="submit" value="Stwórz" />
    </form>
}

export default StorageCreateForm;
