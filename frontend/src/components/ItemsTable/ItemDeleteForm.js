import { useState } from "react";

function ItemDeleteForm({ storageName, refreshItems }) {
    const [name, setName] = useState("");

    function submitForm(event) {
        event.preventDefault(true);
        
        fetch(`http://localhost:8080/storages/${storageName}/${name}`, { method: "DELETE" })
            .then(refreshItems);
    }

    return <form className="table-modal-form" style={{ width: "200px" }} onSubmit={submitForm}>
        <h1 className="table-modal-form-header">Usuń przedmiot z przedmiot z {storageName}</h1>
        <div className="table-modal-form-row">
            <label htmlFor="name">Nazwa</label>
            <input type="text" name="name" id="name-input" onInput={(event) => setName(event.target.value)} />
        </div>
        <input type="submit" value="Usuń" />
    </form>
}

export default ItemDeleteForm;
