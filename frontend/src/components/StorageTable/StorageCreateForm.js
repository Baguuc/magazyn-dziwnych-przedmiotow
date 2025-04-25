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

    return <form className="storage-table-modal-form" onSubmit={submitForm}>
        <h1 className="storage-table-modal-form-header">Stwórz magazyn</h1>
        <div className="storage-table-modal-form-row">
            <label htmlFor="storage-name">Nazwa</label>
            <input type="text" name="storage-name" id="storage-name-input" onInput={(event) => setName(event.target.value)} />
        </div>
        <div className="storage-table-modal-form-row">
            <label htmlFor="storage-capacity">Pojemność</label>
            <input type="text" name="storage-capacity" id="storage-capacity-input" onInput={(event) => setCapacity(event.target.value)} />
        </div>
        <div className="storage-table-modal-form-row">
            <label htmlFor="storage-max-weight">Maksymalna łączna waga</label>
            <input type="text" name="storage-max-weight" id="storage-max-weight-input" onInput={(event) => setMaxTotalWeight(event.target.value)} />
        </div>
        <input type="submit" value="Stwórz" />
    </form>
}

export default StorageCreateForm;
