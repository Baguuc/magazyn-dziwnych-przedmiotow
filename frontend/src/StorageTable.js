import { useEffect, useId, useState } from 'react';
import Modal from './Modal';

function StorageRow({ data }) {
    return <tr>
        <td>{data.name}</td>
        <td>{data.currentTotalWeight}kg / {data.maxTotalWeight}kg</td>
        <td>{data.currentItemCount} / {data.capacity}</td>
    </tr>
}
  
function StorageTable() {
    const [storages, setStorages] = useState([]);
    const [err, setErr] = useState(undefined);
    
    useEffect(() => {
       refreshStorages();
    }, []);

    function refreshStorages() {
        fetch("http://localhost:8080/storages")
            .then(response => {
                if(response.status !== 200) {
                setErr(response.body);
                return [];
                }
        
                return response.json();
            })
            .then(setStorages);
    }

    function createStorage(data) {
        console.log(data);
        fetch("http://localhost:8080/storages", { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(data) })
            .then(refreshStorages);
    }

    function submitForm(event) {
        event.preventDefault(true);

        const name = document.getElementById("storage-name-input").value;
        const capacity = parseInt(document.getElementById("storage-capacity-input").value);
        const maxTotalWeight = parseFloat(document.getElementById("storage-max-weight-input").value);

        createStorage({ name, capacity, maxTotalWeight });
    }

    const createButtonId = useId();
    
    if(storages && !err) {
        // render storages
        return (<>
            <Modal activatorId={createButtonId}>
                <form className="storage-table-modal-form" onSubmit={submitForm}>
                    <h1 className="storage-table-modal-form-header">Stwórz magazyn</h1>
                    <div className="storage-table-modal-form-row">
                        <label htmlFor="storage-name">Nazwa</label>
                        <input type="text" name="storage-name" id="storage-name-input" />
                    </div>
                    <div className="storage-table-modal-form-row">
                        <label htmlFor="storage-capacity">Pojemność</label>
                        <input type="text" name="storage-capacity" id="storage-capacity-input" />
                    </div>
                    <div className="storage-table-modal-form-row">
                        <label htmlFor="storage-max-weight">Maksymalna łączna waga</label>
                        <input type="text" name="storage-max-weight" id="storage-max-weight-input" />
                    </div>
                    <input type="submit" value="Stwórz" />
                </form>
            </Modal>
            <div className="storage-table-root">
                <table className="storage-table">
                    <thead>
                        <tr>
                            <th>Nazwa</th>
                            <th>Łączna waga</th>
                            <th>Ilość przedmiotów</th>
                        </tr>
                    </thead>
                    <tbody>
                        {storages.map((row, idx) => <StorageRow key={idx} data={row} />)}
                    </tbody>
                </table>
                <button className="storage-table-button" id={createButtonId}>Dodaj magazyn</button>
            </div>
        </>);
    } else {
        // render error
        return <p>{err}</p>
    }
}
  
export default StorageTable;